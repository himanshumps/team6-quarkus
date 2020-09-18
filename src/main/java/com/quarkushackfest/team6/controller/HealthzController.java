package com.quarkushackfest.team6.controller;

import io.smallrye.mutiny.Uni;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/health")
@RequestScoped
public class HealthzController {

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getQuoteMulti() {
        return Uni.createFrom().item("success");
    }

}
