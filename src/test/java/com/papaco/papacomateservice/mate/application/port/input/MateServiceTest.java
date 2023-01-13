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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
        mateService = new MateService(mateRepository, reviewerRepository, new MateValidationService());
        reviewer = new Reviewer(1L, true);
        reviewerRepository.save(reviewer);
    }

    @DisplayName("메이트를 종료한다")
    @Test
    void finishMate() {
        Mate mate = Mate.mateInWaiting(MATE_ID, PROJECT_ID, reviewer);
        ReflectionTestUtils.setField(mate, "status", MateStatus.JOINED);
        Mate saveMate = mateRepository.save(mate);

        mateService.finishMate(MATE_ID);

        assertThat(saveMate.getStatus()).isEqualTo(MateStatus.FINISHED);
    }
}
