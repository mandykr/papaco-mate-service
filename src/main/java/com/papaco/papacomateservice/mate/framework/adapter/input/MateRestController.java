package com.papaco.papacomateservice.mate.framework.adapter.input;

import com.papaco.papacomateservice.mate.application.dto.MateResponse;
import com.papaco.papacomateservice.mate.application.port.usecase.MateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/mates")
@RestController
public class MateRestController {
    private final MateUseCase mateUseCase;

    @PostMapping("/{projectId}/{reviewerId}/propose")
    public ResponseEntity<MateResponse> proposeMate(@PathVariable UUID projectId, @PathVariable Long reviewerId) {
        MateResponse mate = mateUseCase.proposeMate(projectId, reviewerId);
        return ResponseEntity.created(URI.create("/mates/" + mate.getId())).body(mate);
    }
}
