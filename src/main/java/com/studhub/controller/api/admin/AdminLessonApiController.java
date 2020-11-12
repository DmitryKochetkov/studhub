package com.studhub.controller.api.admin;

import com.studhub.dto.LessonDto;
import com.studhub.dto.PageDto;
import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.ResourceNotFoundException;
import com.studhub.payload.CreateLessonRequest;
import com.studhub.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "Manage lessons", description = "Available only for administrator.")
public class AdminLessonApiController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/lessons")
    @ApiOperation("Get all lessons")
    public ResponseEntity<PageDto<LessonDto>> get(@RequestParam(defaultValue = "1") Integer page) {
        if (page - 1 < 0)
            throw new BadRequestException();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<LessonDto> result = lessonService.getAll(pageable).map(LessonDto::new);
        if (result.getNumber() + 1 > result.getTotalPages())
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }

    @PostMapping(value = "/lessons/new",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Create new lesson")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LessonDto> create(@RequestBody CreateLessonRequest request) {
        LessonDto dto = new LessonDto();

        //TODO: use java.time everywhere to store datetime
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getStartDate());
        calendar.add(Calendar.HOUR, request.getStartTime().getHours());
        calendar.add(Calendar.MINUTE, request.getStartTime().getMinutes());

        dto.setStartDate(calendar.getTime());
        dto.setTopic(request.getTopic());
        dto.setStatus(LessonStatus.SCHEDULED);

        Lesson created = lessonService.createLesson(dto);

        if (created == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(new LessonDto(created), HttpStatus.ACCEPTED);
    }
}
