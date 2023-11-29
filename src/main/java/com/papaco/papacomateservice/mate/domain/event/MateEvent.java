package com.papaco.papacomateservice.mate.domain.event;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import com.papaco.papacomateservice.mate.domain.entity.Reviewer;
import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
public class MateEvent extends BaseEvent {
    private UUID id;
    private UUID projectId;
    private Reviewer reviewer;
    private MateStatus status;

    private MateEvent(UUID id, UUID projectId, Reviewer reviewer, MateStatus status, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.id = id;
        this.projectId = projectId;
        this.reviewer = reviewer;
        this.status = status;
    }

    public static MateEvent of(Mate mate) {
        return new MateEvent(
                mate.getId(),
                mate.getProjectId(),
                mate.getReviewer(),
                mate.getStatus(),
                mate.getCreatedDate(),
                mate.getModifiedDate()
        );
    }
}
