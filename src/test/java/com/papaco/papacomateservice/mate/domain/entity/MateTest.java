package com.papaco.papacomateservice.mate.domain.entity;

import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MateTest {
    private UUID mateId = UUID.fromString("59e5e09a-0fe6-48e5-b377-9ffe1b1499aa");
    private UUID projectId = UUID.fromString("6fec2b25-9ad4-4dba-9f54-bc3c3305a6a5");
    private Reviewer reviewer = new Reviewer(1L, true);

    @DisplayName("메이트는 식별자와 프로젝트 식별자, 리뷰어, 메이트 상태를 가진다")
    @Test
    void create() {
        assertThatCode(() -> Mate.mateInWaiting(mateId, projectId, reviewer))
                .doesNotThrowAnyException();
    }

    @DisplayName("메이트를 대기에서 제안으로 변경한다")
    @Test
    void propose() {
        Mate mate = Mate.mateInWaiting(mateId, projectId, reviewer);

        mate.propose();

        assertThat(mate.getStatus()).isEqualTo(MateStatus.PROPOSED);
    }

    @DisplayName("리뷰어 등록이 되어있지 않으면 메이트를 제안할 수 없다")
    @Test
    void unregistered_propose() {
        Reviewer unregistered = new Reviewer(1L, false);
        Mate mate = Mate.mateInWaiting(mateId, projectId, unregistered);

        assertThatThrownBy(mate::propose)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메이트를 제안에서 연결로 변경한다")
    @Test
    void join() {
        Mate mate = Mate.mateInWaiting(mateId, projectId, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.PROPOSED);

        mate.join();

        assertThat(mate.getStatus()).isEqualTo(MateStatus.JOINED);
    }

    @DisplayName("리뷰어 등록이 되어있지 않으면 메이트를 연결할 수 없다")
    @Test
    void unregistered_join() {
        Reviewer unregistered = new Reviewer(1L, false);
        Mate mate = Mate.mateInWaiting(mateId, projectId, unregistered);

        assertThatThrownBy(mate::join)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메이트를 연결에서 종료로 변경한다")
    @Test
    void finish() {
        Mate mate = Mate.mateInWaiting(mateId, projectId, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.JOINED);

        mate.finish();

        assertThat(mate.getStatus()).isEqualTo(MateStatus.FINISHED);
    }
}
