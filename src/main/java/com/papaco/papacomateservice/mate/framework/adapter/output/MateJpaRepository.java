package com.papaco.papacomateservice.mate.framework.adapter.output;

import com.papaco.papacomateservice.mate.application.port.output.MateRepository;
import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateJpaRepository extends MateRepository, JpaRepository<Mate, UUID> {
    List<Mate> findAllByProjectIdAndStatus(UUID projectId, MateStatus status);

    Optional<Mate> findByProjectIdAndReviewerAndStatus(UUID projectId, Reviewer reviewer, MateStatus waiting);
}
