package com.papaco.papacomateservice.mate.application.port.input;

import com.papaco.papacomateservice.mate.application.dto.MateResponse;
import com.papaco.papacomateservice.mate.application.port.output.MateRepository;
import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.application.port.usecase.MateUseCase;
import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.service.MateValidationService;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class MateService implements MateUseCase {
    private final MateRepository mateRepository;
    private final ReviewerRepository reviewerRepository;
    private final MateValidationService validationService;

    @Override
    public MateResponse proposeMate(UUID projectId, Long reviewerId) {
        List<Mate> joinedList = mateRepository.findAllByProjectIdAndStatus(projectId, MateStatus.JOINED);
        Reviewer reviewer = findReviewer(reviewerId);
        Mate mate = Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer);
        validationService.validatePropose(projectId, joinedList, mate, reviewer);

        mate.propose();
        return MateResponse.of(mate);
    }

    private Reviewer findReviewer(Long reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
