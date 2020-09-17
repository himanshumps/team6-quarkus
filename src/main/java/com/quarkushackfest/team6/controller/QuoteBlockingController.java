package com.quarkushackfest.team6.controller;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoBlockingRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteBlockingController {

  private static final int DELAY_PER_ITEM_MS = 100;

  @Inject 
  QuoteMongoBlockingRepository quoteMongoBlockingRepository;

  @GetMapping("/quotes-blocking")
  public Iterable<Quote> getQuotesBlocking() throws Exception {
    return quoteMongoBlockingRepository.findAll().list();
  }

  @GetMapping("/quotes-blocking-paged")
  public PanacheQuery<Quote> getQuotesBlocking(
      final @RequestParam(name = "page") int page, final @RequestParam(name = "size") int size)
      throws Exception {
    return quoteMongoBlockingRepository.findAllByIdNotNullOrderByIdAsc(Page.of(page, size));
  }
}
