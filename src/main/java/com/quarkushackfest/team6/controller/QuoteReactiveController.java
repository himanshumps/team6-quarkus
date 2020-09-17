package com.quarkushackfest.team6.controller;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoReactiveRepository;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Multi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RequestScoped
public class QuoteReactiveController {
    private static final Logger log = LoggerFactory.getLogger(QuoteReactiveController.class);
    private static final int DELAY_PER_ITEM_MS = 1;

    @Inject
    QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    /**
     * Get all the documents the reactive way
     * @return
     */
    @GET()
    @Path("/quotes-reactive")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Quote> getQuoteMulti() {
        log.info("Serving the request for getQuoteMulti");
        return quoteMongoReactiveRepository
                .findAll()
                .stream();
    }

    /**
     * Get all the documents the reactive way in a paginated fashion
     * @return
     */

    @GET()
    @Path("/quotes-reactive-paged")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Quote> getQuoteMultiPaged(
            final @QueryParam("page") int page,
            final @QueryParam("size") int size) {
        log.info("Serving the request for getQuoteMultiPaged for page: {} and size: {}", page, size);
        return quoteMongoReactiveRepository
                .findAllByIdNotNullOrderByIdAsc()
                .page(Page.of(page, size))
                .stream();
    }
}
