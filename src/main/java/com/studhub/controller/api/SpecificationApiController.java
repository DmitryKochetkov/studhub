package com.studhub.controller.api;

import com.studhub.dto.ExamSpecificationDto;
import com.studhub.entity.ExamSpecification;
import com.studhub.exception.NotFoundException;
import com.studhub.service.ExamSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = "Specification", description = "Get specification - description of problems in a specific exam or contest.")
public class SpecificationApiController {
    @Autowired
    private ExamSpecificationService examSpecificationService;

    @GetMapping(value = "/exam-specification/{id}")
    @ApiOperation(value = "Get specification by exam id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<ExamSpecificationDto> getExamSpecification(@PathVariable Long id) {
        ExamSpecification examSpecification = examSpecificationService.getById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(new ExamSpecificationDto(examSpecification));
    }
}
