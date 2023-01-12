package com.papaco.papacomateservice.mate.domain.service;

import com.papaco.papacomateservice.mate.application.port.output.MateRepository;
import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.JOINED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MateValidationServiceTest {
    private UUID projectId = UUID.fromString("6fec2b25-9ad4-4dba-9f54-bc3c3305a6a5");
    private Reviewer reviewer = new Reviewer(1L, true);
    private MateValidationService validationService;
    private MateRepository mateRepository;

    @BeforeEach
    void setUp() {
        validationService = new MateValidationService();
    }

    @DisplayName("프로젝트의 메이트가 연결 상태이면 새로운 메이트를 제안할 수 없다")
    @Test
    void joined() {
        Mate mate = Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer);
        ReflectionTestUtils.setField(mate, "status", JOINED);

        Reviewer newReviewer = new Reviewer(2L, true);
        Mate newMate = Mate.mateInWaiting(UUID.randomUUID(), projectId, newReviewer);

        List<Mate> mates = new ArrayList<>();
        mates.add(mate);

        assertThatThrownBy(() -> validationService.validatePropose(projectId, mates, newMate, newReviewer))
                .isInstanceOf(IllegalStateException.class);
    }
}
