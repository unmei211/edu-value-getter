package com.subby.eduvaluegetter.valuegetter.controller;

import com.subby.eduvaluegetter.valuegetter.controller.model.ValueResponseDto;
import com.subby.eduvaluegetter.valuegetter.service.ValueGetterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ValueGetterController {

    private final ValueGetterService valueGetterService;

    ValueGetterController(
            ValueGetterService valueGetterService
    ) {
        this.valueGetterService = valueGetterService;
    }

    @GetMapping("/value")
    public ResponseEntity<ValueResponseDto> minValueGetterByStep(
            @RequestParam String path,
            @RequestParam Integer step
    ) throws IOException {
        var value = valueGetterService.getMinValueByStep(path, step);

        return ResponseEntity.ok(
                new ValueResponseDto(value)
        );
    }
}

