package com.papaco.papacomateservice.mate.application.port.output;

import com.papaco.papacomateservice.mate.domain.entity.Reviewer;

import java.util.Optional;

public interface ReviewerRepository {
    Reviewer save(Reviewer reviewer);

    Optional<Reviewer> findById(Long reviewerId);

    void flush();
}
