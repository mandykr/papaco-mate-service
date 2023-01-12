package com.papaco.papacomateservice.mate.application.port.usecase;

import com.papaco.papacomateservice.mate.application.dto.MateResponse;

import java.util.UUID;

public interface MateUseCase {
    MateResponse proposeMate(UUID projectId, Long reviewerId);
}
