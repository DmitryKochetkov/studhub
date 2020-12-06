package com.studhub.controller.api;

import com.studhub.dto.VerdictDto;
import com.studhub.service.VerdictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VerdictsInfoApiController {
    @Autowired
    private VerdictService verdictService;

    @GetMapping("/api/verdicts")
    public ResponseEntity<List<VerdictDto>> verdicts() {
        List<VerdictDto> result = verdictService.getAll().stream().map(VerdictDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
