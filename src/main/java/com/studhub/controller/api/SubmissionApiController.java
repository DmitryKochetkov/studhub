package com.studhub.controller.api;

import com.studhub.dto.PageDto;
import com.studhub.dto.SubmissionDto;
import com.studhub.entity.Course;
import com.studhub.entity.Submission;
import com.studhub.exception.NotAcceptableException;
import com.studhub.exception.NotFoundException;
import com.studhub.payload.SubmissionRequest;
import com.studhub.service.CourseService;
import com.studhub.service.SubmissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(tags = "Submissions", description = "Submit problems or get existing submissions.")
public class SubmissionApiController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private SubmissionService submissionService;

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/submissions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get submissions in homework")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<SubmissionDto>> getSubmissionsFromHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkId,
            @RequestParam(defaultValue = "1") Integer page) {
        Course course = courseService.getById(courseId).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            throw new NotFoundException();

        Page<SubmissionDto> result = submissionService.getByHomeworkId(homeworkId, page).map(SubmissionDto::new);
        return ResponseEntity.ok(new PageDto<>(result));
    }

    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}/problems/{problemNumber}/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Submit problem in homework")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 406, message = "Not Acceptable")
    }
    )
    public ResponseEntity<SubmissionDto> submitProblemInHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkId,
            @PathVariable Integer problemNumber,
            @RequestBody SubmissionRequest submissionRequest) {
        Course course = courseService.getById(courseId).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            throw new NotFoundException();

        submissionRequest.setHomeworkId(homeworkId);
        submissionRequest.setProblemNumber(problemNumber);
        try {
            Submission created = submissionService.createSubmission(submissionRequest);
            return new ResponseEntity<SubmissionDto>(new SubmissionDto(created), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            throw new NotAcceptableException();
        }
    }
}
