package com.papaco.papacomateservice.mate.application.port.output;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateRepository extends JpaRepository<Mate, UUID> {
    List<Mate> findAllByProjectIdAndStatus(UUID projectId, MateStatus joined);

    Optional<Mate> findByProjectIdAndReviewer(UUID projectId, Reviewer reviewer);
}
