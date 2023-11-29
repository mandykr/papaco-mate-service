package com.papaco.papacomateservice.mate.application.port.input;

import com.papaco.papacomateservice.mate.application.port.output.MateEventPublisher;
import com.papaco.papacomateservice.mate.domain.event.MateEvent;

public class FakeMateEventPublisher implements MateEventPublisher {
    @Override
    public void publish(MateEvent mateEvent) {

    }
}
