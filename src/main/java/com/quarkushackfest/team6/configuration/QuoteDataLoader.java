package com.quarkushackfest.team6.configuration;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.function.Supplier;

@ApplicationScoped
public class QuoteDataLoader {

/*
    private static final Logger log = LoggerFactory.getLogger(QuoteDataLoader.class);
*/

    /*@Inject
    QuoteMongoReactiveRepository quoteMongoReactiveRepository;*/

    void run(@Observes StartupEvent ev) {
        System.out.println("Starting the application ...");
        // If there is no data in the database

        /*if (System.getenv("NATIVE") == null && quoteMongoReactiveRepository.count().await().indefinitely().intValue() == 0) {
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
                                //log.info("Got object: {}", ((JsonObject)jsonObject).encode());
                                return new Quote(idSupplier.get(), ((JsonObject)jsonObject).getValue("Title").toString(), ((JsonObject)jsonObject).getValue("Plot").toString());
                            })
                            .collect(Collectors.toList()
                            )
            ).await().indefinitely();
            log.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().await().indefinitely().intValue());
        } else {
            log.info("Data is already loaded in the database.");
        }*/
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
