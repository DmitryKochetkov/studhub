package com.studhub.controller.api;

import com.studhub.dto.VerdictDto;
import com.studhub.service.VerdictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Verdicts", description = "Get verdicts info.")
public class VerdictsInfoApiController {
    @Autowired
    private VerdictService verdictService;

    @GetMapping(value = "/api/verdicts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all verdicts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    }
    )
    public ResponseEntity<List<VerdictDto>> verdicts() {
        List<VerdictDto> result = verdictService.getAll().stream().map(VerdictDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
