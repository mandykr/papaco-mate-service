package com.papaco.papacomateservice.mate.domain.entity;

import com.papaco.papacomateservice.mate.domain.vo.MateStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mate {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;
    private UUID projectId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    @Enumerated(EnumType.STRING)
    private MateStatus status;

    public static Mate mateInWaiting(UUID id, UUID projectId, Reviewer reviewer) {
        return new Mate(id, projectId, reviewer, WAITING);
    }

    public void propose() {
        checkRegisteredReviewer();
        this.status = PROPOSED;
    }

    private void checkRegisteredReviewer() {
        if (!reviewer.isRegistered()) {
            throw new IllegalStateException("리뷰어 등록이 되어있지 않으면 메이트를 제안할 수 없습니다.");
        }
    }

    public void join() {
        checkRegisteredReviewer();
        this.status = JOINED;
    }

    public void finish() {
        this.status = FINISHED;
    }

    public void reject() {
        if (this.status != PROPOSED) {
            throw new IllegalStateException("제안 상태가 아니면 거절할 수 없습니다.");
        }
        this.status = REJECTED;
    }
}
