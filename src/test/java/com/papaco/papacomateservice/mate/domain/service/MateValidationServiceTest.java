package com.papaco.papacomateservice.mate.domain.service;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.JOINED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.INCLUDE;

class MateValidationServiceTest {
    private UUID projectId = UUID.fromString("6fec2b25-9ad4-4dba-9f54-bc3c3305a6a5");
    private Reviewer reviewer = new Reviewer(1L, true);
    private MateValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new MateValidationService();
    }

    @DisplayName("프로젝트에 연결된 메이트가 존재하면 예외가 발생한다")
    @Test
    void propose_joined() {
        Mate mate = Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer);
        ReflectionTestUtils.setField(mate, "status", JOINED);

        List<Mate> mates = new ArrayList<>();
        mates.add(mate);

        assertThatThrownBy(() -> validationService.validateJoined(mates))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("연결, 거절, 종료 상태의 메이트를 삭제하면 예외가 발생한다")
    @ParameterizedTest
    @EnumSource(mode = INCLUDE, names = {"JOINED", "REJECTED", "FINISHED"})
    void delete_joined_finished(MateStatus status) {
        Mate mate = Mate.mateInWaiting(UUID.randomUUID(), projectId, reviewer);
        ReflectionTestUtils.setField(mate, "status", status);

        assertThatThrownBy(() -> validationService.validateDelete(mate))
                .isInstanceOf(IllegalStateException.class);
    }
}
