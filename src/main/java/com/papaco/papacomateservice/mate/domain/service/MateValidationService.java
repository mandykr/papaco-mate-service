package com.papaco.papacomateservice.mate.domain.service;

import com.papaco.papacomateservice.mate.domain.entity.Mate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MateValidationService {
    public void validateJoined(List<Mate> joinedMates) {
        if (!joinedMates.isEmpty()) {
            throw new IllegalStateException("이미 연결되어 있는 메이트가 존재합니다.");
        }
    }
}
