package com.quarkushackfest.team6.controller;

import com.quarkushackfest.team6.domain.Quote;
import com.quarkushackfest.team6.repository.QuoteMongoBlockingRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteBlockingController {

    @Inject
    QuoteMongoBlockingRepository quoteMongoBlockingRepository;

    /**
     * Get all the documents using blocking api call
     *
     * @return
     */
    @GET
    @Path("quotes-blocking")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Quote> getQuotesBlocking() throws Exception {
        return quoteMongoBlockingRepository.findAll().list();
    }

    /**
     * Get all the documents in pages using blocking api call
     *
     * @return
     */
    @GET
    @Path("quotes-blocking-paged")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public PanacheQuery<Quote> getQuotesBlocking(
            final @QueryParam("page") int page,
            final @QueryParam("size") int size
    ) throws Exception {
        return quoteMongoBlockingRepository.findAllByIdNotNullOrderByIdAsc(Page.of(page, size));
    }
}
