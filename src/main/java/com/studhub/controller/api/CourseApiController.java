package com.studhub.controller.api;

import com.studhub.dto.*;
import com.studhub.entity.Course;
import com.studhub.entity.Homework;
import com.studhub.entity.Specification;
import com.studhub.entity.Submission;
import com.studhub.enums.BusinessPeriod;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.NotAcceptableException;
import com.studhub.exception.NotFoundException;
import com.studhub.payload.SubmissionRequest;
import com.studhub.service.*;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(tags = "Course", description = "Get course info.")
public class CourseApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get course by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getById(courseId).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //TODO: check authority

        return ResponseEntity.ok(new CourseDto(course));
    }

    @GetMapping(value = "/course/{courseId}/homework", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all homework in course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<PageDto<HomeworkDto>> getAllHomeworkInCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "1") Integer page) {
        if (page - 1 < 0)
            throw new BadRequestException();
        Course course = courseService.getById(courseId).orElseThrow(NotFoundException::new);

        //TODO: check authority

        Page<HomeworkDto> result = homeworkService.getAllHomeworkInCourse(course, page).map(HomeworkDto::new);
        if (result.getTotalPages() < result.getNumber() + 1)
            throw new BadRequestException();
        return ResponseEntity.ok(new PageDto<HomeworkDto>(result));
    }

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get homework by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<HomeworkDto> getHomeworkById(
            @PathVariable Long courseId,
            @PathVariable Long homeworkId,
            @RequestParam(defaultValue = "1") Integer page) {
        Course course = courseService.getById(courseId).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HomeworkDto result = new HomeworkDto(homeworkService.getById(homeworkId).orElseThrow(NotFoundException::new));
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/problems/{problemNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get problem in homework")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<HomeworkProblemDto> getProblemFromHomework(
            @PathVariable Long courseId,
            @PathVariable Long homeworkId,
            @PathVariable Integer problemNumber) {
        Course course = courseService.getById(courseId).orElseThrow(IllegalArgumentException::new);
        if (course == null)
            throw new NotFoundException();

        if (problemNumber < 1)
            throw new BadRequestException();

        HomeworkProblemDto result = new HomeworkProblemDto(homeworkService.getProblemInHomework(homeworkId, problemNumber).orElseThrow(NotFoundException::new));
        return ResponseEntity.ok(result);
    }

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

    @GetMapping(value = "/course/{courseId}/homework-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get percentage of successful submissions in all homeworks by period.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<List<HomeworkStatDtoItem>> getCourseHomeworkStatistics(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "MONTH") BusinessPeriod businessPeriod) {
        Course course = courseService.getById(courseId).orElseThrow(NotFoundException::new);

        if (!course.getId().equals(courseId))
            throw new NotFoundException();

        try {
            List<Homework> homeworkList = homeworkService.getAllHomeworkInCourseByBusinessPeriod(course, businessPeriod);
            return ResponseEntity.ok(homeworkList.stream().map(HomeworkStatDtoItem::new).collect(Collectors.toList()));
        }
        catch (IllegalArgumentException e) {
            throw new NotAcceptableException();
        }
    }

    @GetMapping(value = "/course/{courseId}/specification-statistics")
    @ApiOperation(value = "Get statistics by active exam specification.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    }
    )
    public ResponseEntity<CourseStatisticsBySpecificationDto> getCourseStatisticsByActiveExamSpecification(
            @PathVariable Long courseId
    ) {
        Course course = courseService.getById(courseId).orElseThrow(NotFoundException::new);

        Specification specification = course.getActiveSpecification();
        if (specification == null)
            throw new BadRequestException(); //TODO: add message

        CourseStatisticsBySpecificationDto courseStatisticsBySpecificationDto = statisticsService.getCourseStatisticsBySpecification(course, specification);
        return ResponseEntity.ok(courseStatisticsBySpecificationDto);
    }
}
