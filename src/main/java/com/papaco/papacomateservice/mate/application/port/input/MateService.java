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
        Reviewer reviewer = findReviewerById(reviewerId);
        Mate mate = findOrCreateMateInWaiting(projectId, reviewer);

        mate.propose();
        Mate saveMate = mateRepository.save(mate);
        return MateResponse.of(saveMate);
    }

    private Mate findOrCreateMateInWaiting(UUID projectId, Reviewer reviewer) {
        return mateRepository.findByProjectIdAndReviewerAndStatus(projectId, reviewer, MateStatus.WAITING)
                .orElse(Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer));
    }

    private Reviewer findReviewerById(Long reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void joinMate(UUID mateId) {
        Mate mate = findMateById(mateId);
        validateJoinedProject(mate.getProjectId());
        mate.join();
    }

    @Override
    public void finishMate(UUID mateId) {
        Mate mate = findMateById(mateId);
        mate.finish();
    }

    @Override
    public void rejectMate(UUID mateId) {
        Mate mate = findMateById(mateId);
        mate.reject();
    }

    @Override
    public void deleteMate(UUID mateId) {
        Mate mate = findMateById(mateId);
        validationService.validateDelete(mate);
        mateRepository.delete(mate);
    }

    private void validateJoinedProject(UUID projectId) {
        List<Mate> joinedList = mateRepository.findAllByProjectIdAndStatus(projectId, JOINED);
        validationService.validateJoined(joinedList);
    }

    private Mate findMateById(UUID mateId) {
        return mateRepository.findById(mateId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
