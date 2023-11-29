package com.papaco.papacomateservice.mate.framework.adapter.output;

import com.papaco.avro.schema.MateMessage;
import com.papaco.avro.schema.ReviewerMessage;
import com.papaco.papacomateservice.mate.domain.event.MateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Component
public class MateKafkaProducer {
    @Value("${papaco.kafka.mate.avro.topic.name}")
    private String topic;
    private final KafkaTemplate<String, MateMessage> kafkaTemplate;

    public void sendMessage(MateEvent event) {
        MateMessage message = getMateMessage(event);
        String key = message.getId();
        kafkaTemplate.send(topic, key, message).addCallback(new ListenableFutureCallback<SendResult<String, MateMessage>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka send error: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, MateMessage> result) {
                log.info("kafka send result: {}", result.toString());
            }
        });
    }

    private MateMessage getMateMessage(MateEvent event) {
        ReviewerMessage reviewer = new ReviewerMessage(
                event.getReviewer().getId(),
                event.getReviewer().isRegistered()
        );

        return new MateMessage(
                String.valueOf(event.getId()),
                String.valueOf(event.getProjectId()),
                reviewer,
                event.getStatus().toString(),
                event.getCreatedDate(),
                event.getModifiedDate()
        );
    }
}
