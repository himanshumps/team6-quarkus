package com.quarkushackfest.team6.configuration;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoReactiveRepository;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuoteDataLoader {

    private static final Logger log = LoggerFactory.getLogger(QuoteDataLoader.class);

    @Inject
    QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    void run(@Observes StartupEvent ev) {
        log.info("Starting the application ...");
        // If there is no data in the database
        log.info("quoteMongoReactiveRepository.count().await().indefinitely().intValue(): {}", quoteMongoReactiveRepository.count().await().indefinitely().intValue());
        if (quoteMongoReactiveRepository.count().await().indefinitely().intValue() == 0) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg2000.txt")));
            bufferedReader
                    .lines()
                    .filter(l -> !l.trim().isEmpty())
                    .map(l -> {
                        log.info("Going to insert: {}", l);
                        try{
                            quoteMongoReactiveRepository.persist(
                                    new Quote(idSupplier.get(), "El Quijote - " + UUID.randomUUID().toString(), l)).await().indefinitely();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        return "";
                    })
                    .collect(Collectors.toList());
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().await().indefinitely().intValue());
        } else {
            log.info("Data was already loaded in the database.");
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
