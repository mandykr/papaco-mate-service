package com.papaco.papacomateservice.mate.application.port.input;

import com.papaco.papacomateservice.mate.application.port.output.MateRepository;
import com.papaco.papacomateservice.mate.application.port.output.ReviewerRepository;
import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.service.MateValidationService;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.INCLUDE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MateServiceTest {
    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private MateRepository mateRepository;
    private MateService mateService;
    private static UUID MATE_ID = UUID.fromString("d8f64416-08f7-4705-8fdb-4d04e1153a0f");
    private static UUID PROJECT_ID = UUID.fromString("6fec2b25-9ad4-4dba-9f54-bc3c3305a6a5");
    private Reviewer reviewer;

    @BeforeEach
    void setUp() {
        mateService = new MateService(mateRepository, reviewerRepository, new MateValidationService(), new FakeMateEventPublisher());
        reviewer = new Reviewer(1L, true);
        reviewerRepository.save(reviewer);
    }

    @DisplayName("메이트를 종료한다")
    @Test
    void finish() {
        Mate mate = Mate.mateInWaiting(MATE_ID, PROJECT_ID, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.JOINED);
        Mate saveMate = mateRepository.save(mate);

        mateService.finishMate(MATE_ID);

        assertThat(saveMate.getStatus()).isEqualTo(MateStatus.FINISHED);
    }

    @DisplayName("제안 상태의 메이트를 삭제한다")
    @Test
    void delete() {
        Mate mate = Mate.mateInWaiting(MATE_ID, PROJECT_ID, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.PROPOSED);
        mateRepository.save(mate);

        mateService.deleteMate(MATE_ID);

        assertThat(mateRepository.findById(MATE_ID)).isEmpty();
    }

    @DisplayName("연결, 거절, 종료 상태의 메이트는 삭제할 수 없다")
    @ParameterizedTest
    @EnumSource(mode = INCLUDE, names = {"JOINED", "REJECTED", "FINISHED"})
    void deleteFailed(MateStatus status) {
        Mate mate = Mate.mateInWaiting(MATE_ID, PROJECT_ID, reviewer);
        ReflectionTestUtils.setField(mate, "status", status);
        mateRepository.save(mate);

        assertThatThrownBy(() -> mateService.deleteMate(MATE_ID))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("제안 상태의 메이트를 거절한다")
    @Test
    void reject() {
        Mate mate = Mate.mateInWaiting(MATE_ID, PROJECT_ID, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.PROPOSED);
        Mate saveMate = mateRepository.save(mate);

        mateService.rejectMate(MATE_ID);

        assertThat(saveMate.getStatus()).isEqualTo(REJECTED);
    }
}
