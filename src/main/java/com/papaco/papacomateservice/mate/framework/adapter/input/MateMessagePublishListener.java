package com.papaco.papacomateservice.mate.framework.adapter.input;

import com.papaco.papacomateservice.mate.domain.event.MateEvent;
import com.papaco.papacomateservice.mate.framework.adapter.output.MateKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class MateMessagePublishListener {
    private final MateKafkaProducer mateKafkaProducer;

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessage(MateEvent event) {
        log.info("listen spring event: {}", event.toString());
        mateKafkaProducer.sendMessage(event);
    }
}
