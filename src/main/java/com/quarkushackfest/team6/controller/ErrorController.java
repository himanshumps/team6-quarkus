package com.quarkushackfest.team6.controller;

import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/error")
@RequestScoped
public class ErrorController {
    private static final Logger log = LoggerFactory.getLogger(QuoteReactiveController.class);

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getQuoteMulti() {
        log.error("There is some problem in the code here", new Exception("This controller should never be called."));
        return Uni.createFrom().item("error");
    }

}
