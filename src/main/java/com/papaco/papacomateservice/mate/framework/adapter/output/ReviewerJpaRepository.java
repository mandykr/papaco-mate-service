package com.papaco.papacomateservice.mate.framework.adapter.output;

import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerJpaRepository extends ReviewerRepository, JpaRepository<Reviewer, Long> {
}
