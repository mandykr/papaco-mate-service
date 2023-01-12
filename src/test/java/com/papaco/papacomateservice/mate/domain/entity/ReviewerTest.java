package com.papaco.papacomateservice.mate.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class ReviewerTest {

    @DisplayName("리뷰어는 식별자와 리뷰어 등록 여부를 가진다")
    @Test
    void create() {
        assertThatCode(() -> new Reviewer(1L, true))
                .doesNotThrowAnyException();
    }
}
