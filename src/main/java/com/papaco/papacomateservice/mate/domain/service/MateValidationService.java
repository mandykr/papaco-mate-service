package com.papaco.papacomateservice.mate.domain.service;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class MateValidationService {
    public void validatePropose(UUID projectId, List<Mate> joinedMates, Mate newMate, Reviewer newReviewer) {
        if (!joinedMates.isEmpty()) {
            throw new IllegalStateException("프로젝트 연결 상태인 메이트가 있어 새로운 메이트를 제안할 수 없습니다");
        }
    }
}
