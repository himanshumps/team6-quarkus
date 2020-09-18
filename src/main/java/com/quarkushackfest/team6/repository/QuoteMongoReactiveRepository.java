package com.quarkushackfest.team6.repository;

import com.quarkushackfest.team6.domain.Quote;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheQuery;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoteMongoReactiveRepository implements ReactivePanacheMongoRepository<Quote> {

    public ReactivePanacheQuery<Quote> findAllByIdNotNullOrderByIdAsc() {
        return find("id is not null", Sort.ascending("id"), true);
    }
}
