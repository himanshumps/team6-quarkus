package com.quarkushackfest.team6.controller;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoReactiveRepository;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteReactiveController {

  private static final int DELAY_PER_ITEM_MS = 500;

  @Inject QuoteMongoReactiveRepository quoteMongoReactiveRepository;

  @GetMapping("/quotes-reactive")
  public Multi<Quote> getQuoteFlux() {
    return quoteMongoReactiveRepository.findAll().stream()
        .onItem()
        .transformToUni(
            quote ->
                Uni.createFrom()
                    .item(quote)
                    .onItem()
                    .delayIt()
                    .by(Duration.ofMillis(DELAY_PER_ITEM_MS)))
        .merge();
  }

  @GetMapping("/quotes-reactive-paged")
  public Multi<Quote> getQuoteFlux(
      final @RequestParam(name = "page") int page, final @RequestParam(name = "size") int size) {
    return quoteMongoReactiveRepository
        .findAllByIdNotNullOrderByIdAsc()
        .page(Page.of(page, size))
        .stream()
        .onItem()
        .transformToUni(
            quote ->
                Uni.createFrom()
                    .item(quote)
                    .onItem()
                    .delayIt()
                    .by(Duration.ofMillis(DELAY_PER_ITEM_MS)))
        .merge();
  }
}
