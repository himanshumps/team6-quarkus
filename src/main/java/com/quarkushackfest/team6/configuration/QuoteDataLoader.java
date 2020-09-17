package com.quarkushackfest.team6.configuration;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoReactiveRepository;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.function.Supplier;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuoteDataLoader {

  private static final Logger log = LoggerFactory.getLogger(QuoteDataLoader.class);

  @Inject QuoteMongoReactiveRepository quoteMongoReactiveRepository;

  void run(@Observes StartupEvent ev) {
    log.info("Starting the application ...");
    // If there is no data in the database

    if (quoteMongoReactiveRepository.count().await().asOptional().indefinitely().get() == 0L) {
      var idSupplier = getIdSequenceSupplier();
      var bufferedReader =
          new BufferedReader(
              new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg2000.txt")));
      Multi.createFrom()
          .items(
              bufferedReader
                  .lines()
                  .filter(l -> !l.trim().isEmpty())
                  .map(
                      l ->
                          quoteMongoReactiveRepository.persist(
                              new Quote(idSupplier.get(), "El Quijote - " + UUID.randomUUID().toString(), l)))).subscribe();
      // .subscribe(m -> log.info("New quote loaded: {}", m.block()));
      log.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().await());
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
