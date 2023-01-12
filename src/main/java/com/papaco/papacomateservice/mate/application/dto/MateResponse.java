package com.papaco.papacomateservice.mate.application.dto;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class MateResponse {
    private UUID id;
    private UUID projectId;
    private Long reviewerId;
    private MateStatus status;

    public static MateResponse of(Mate mate) {
        return new MateResponse(mate.getId(), mate.getProjectId(), mate.getReviewer().getId(), mate.getStatus());
    }
}
