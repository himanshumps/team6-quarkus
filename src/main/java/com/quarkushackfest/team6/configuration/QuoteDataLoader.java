package com.quarkushackfest.team6.configuration;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoReactiveRepository;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuoteDataLoader {

    private static final Logger log = LoggerFactory.getLogger(QuoteDataLoader.class);

    @Inject
    QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    void run(@Observes StartupEvent ev) {
        log.info("Starting the application ...");
        log.info("Quote count: {}", quoteMongoReactiveRepository.count().await().indefinitely().intValue());
        // If there is no data in the database
        if (quoteMongoReactiveRepository.count().await().indefinitely().intValue() == 0) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg1000.json")));
            var stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            quoteMongoReactiveRepository.persist(
                    new JsonArray(
                            stringBuilder.toString())
                            .stream()
                            .map(jsonObject -> {
                                log.info("Got object: {}", ((JsonObject)jsonObject).encode());
                                return new Quote(idSupplier.get(), ((JsonObject)jsonObject).getString("Title"), ((JsonObject)jsonObject).getString("Plot"))
                            })
                            .collect(Collectors.toList()
                            )
            ).await().indefinitely();

            log.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().await().indefinitely().intValue());
        } else {
            log.info("Data is already loaded in the database.");
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l = 0L;

            @Override
            public String get() {
                // adds padding zeroes
                return String.format("%05d", l++);
            }
        };
    }
}
