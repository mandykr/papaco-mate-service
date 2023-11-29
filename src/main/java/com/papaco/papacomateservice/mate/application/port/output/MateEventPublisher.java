package com.papaco.papacomateservice.mate.application.port.output;

import com.papaco.papacomateservice.mate.domain.event.MateEvent;

public interface MateEventPublisher {
    void publish(MateEvent mateEvent);
}
