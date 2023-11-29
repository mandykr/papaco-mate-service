package com.papaco.papacomateservice.mate.framework.adapter.output;

import com.papaco.papacomateservice.mate.application.port.output.MateEventPublisher;
import com.papaco.papacomateservice.mate.domain.event.MateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class MateSpringEventPublisher implements MateEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(MateEvent mateEvent) {
        log.info("publish spring event: {}", mateEvent.toString());
        eventPublisher.publishEvent(mateEvent);
    }
}
