package com.papaco.papacomateservice.mate.domain.service;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.FINISHED;
import static com.papaco.papacomateservice.mate.domain.vo.MateStatus.JOINED;

@Transactional
@Service
public class MateValidationService {
    public void validateJoined(List<Mate> joinedMates) {
        if (!joinedMates.isEmpty()) {
            throw new IllegalStateException("이미 연결되어 있는 메이트가 존재합니다.");
        }
    }

    public void validateDelete(Mate mate) {
        if (mate.getStatus() == JOINED) {
            throw new IllegalStateException("연결 상태의 메이트는 삭제할 수 없습니다.");
        }

        if (mate.getStatus() == FINISHED) {
            throw new IllegalStateException("종료 상태의 메이트는 삭제할 수 없습니다.");
        }
    }
}
