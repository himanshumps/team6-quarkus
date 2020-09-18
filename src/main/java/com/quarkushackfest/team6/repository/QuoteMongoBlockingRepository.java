package com.quarkushackfest.team6.repository;

import com.quarkushackfest.team6.domain.Quote;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoteMongoBlockingRepository implements PanacheMongoRepository<Quote> {

    public PanacheQuery<Quote> findAllByIdNotNullOrderByIdAsc(Page page) {
        return find("{ id : { $exists: ?1 } }", Sort.ascending("id"), true);
    }
}
