package com.papaco.papacomateservice.mate.application.port.input;

import com.papaco.papacomateservice.mate.application.dto.MateResponse;
import com.papaco.papacomateservice.mate.application.port.output.MateRepository;
import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.application.port.usecase.MateUseCase;
import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.service.MateValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.JOINED;

@RequiredArgsConstructor
@Transactional
@Service
public class MateService implements MateUseCase {
    private final MateRepository mateRepository;
    private final ReviewerRepository reviewerRepository;
    private final MateValidationService validationService;

    @Override
    public MateResponse proposeMate(UUID projectId, Long reviewerId) {
        validateJoinedProject(projectId);
        Reviewer reviewer = findReviewer(reviewerId);
        Mate mate = Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer);

        mate.propose();
        Mate saveMate = mateRepository.save(mate);
        return MateResponse.of(saveMate);
    }

    private Reviewer findReviewer(Long reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void joinMate(UUID mateId) {
        Mate mate = mateRepository.findById(mateId)
                .orElseThrow(EntityNotFoundException::new);
        validateJoinedProject(mate.getProjectId());
        mate.join();
    }

    private void validateJoinedProject(UUID projectId) {
        List<Mate> joinedList = mateRepository.findAllByProjectIdAndStatus(projectId, JOINED);
        validationService.validateJoined(joinedList);
    }
}
