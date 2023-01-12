package com.papaco.papacomateservice.mate.domain.entity;

import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mate {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;
    private UUID projectId;

    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    @Enumerated(EnumType.STRING)
    private MateStatus status;

    public static Mate mateInWaiting(UUID id, UUID projectId, Reviewer reviewer) {
        return new Mate(id, projectId, reviewer, MateStatus.WAITING);
    }

    public void propose() {
        if (!reviewer.isRegistered()) {
            throw new IllegalStateException("리뷰어 등록이 되어있지 않으면 메이트를 제안할 수 없습니다");
        }
        this.status = MateStatus.PROPOSED;
    }

    public void join() {
        this.status = MateStatus.JOINED;
    }

    public void finish() {
        this.status = MateStatus.FINISHED;
    }
}
