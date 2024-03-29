package com.papaco.papacomateservice.mate.application.port.usecase;

import com.papaco.papacomateservice.mate.application.dto.MateResponse;

import java.util.UUID;

public interface MateUseCase {
    MateResponse proposeMate(UUID projectId, Long reviewerId);

    void joinMate(UUID mateId);

    void finishMate(UUID mateId);

    void rejectMate(UUID mateId);

    void deleteMate(UUID mateId);
}
