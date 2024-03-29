package com.papaco.papacomateservice.mate.application.port.output;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateRepository {
    Mate save(Mate mate);

    Optional<Mate> findById(UUID mateId);

    List<Mate> findAllByProjectIdAndStatus(UUID projectId, MateStatus status);

    Optional<Mate> findByProjectIdAndReviewerAndStatus(UUID projectId, Reviewer reviewer, MateStatus waiting);

    void delete(Mate mate);
}
