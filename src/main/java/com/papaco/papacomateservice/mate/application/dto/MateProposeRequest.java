package com.papaco.papacomateservice.mate.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class MateProposeRequest {
    private UUID projectId;
    private Long reviewerId;
}
