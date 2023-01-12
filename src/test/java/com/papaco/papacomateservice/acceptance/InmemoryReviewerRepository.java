package com.papaco.papacomateservice.acceptance;

import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;

import java.util.Optional;

public class InmemoryReviewerRepository implements ReviewerRepository {
    @Override
    public Optional<Reviewer> findById(Long reviewerId) {
        return Optional.of(new Reviewer(reviewerId, true));
    }
}
