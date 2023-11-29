package com.papaco.papacomateservice.mate.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BaseEvent {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
