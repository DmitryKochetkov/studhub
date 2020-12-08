package com.studhub.controller.api.admin;

import com.studhub.dto.LessonDto;
import com.studhub.dto.PageDto;
import com.studhub.entity.Lesson;
import com.studhub.exception.BadRequestException;
import com.studhub.exception.InternalServerErrorException;
import com.studhub.exception.NotFoundException;
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

/**
 * Контроллер администрирования уроков.
 */
@RestController
@RequestMapping("/api/admin")
@Api(tags = "Manage lessons", description = "Available only for administrator.")
public class AdminLessonApiController {
    @Autowired
    private LessonService lessonService;

    @GetMapping(value = "/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get all lessons")
    public ResponseEntity<PageDto<LessonDto>> get(@RequestParam(defaultValue = "1") Integer page) {
        if (page - 1 < 0)
            throw new BadRequestException();
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<LessonDto> result = lessonService.getAll(pageable).map(LessonDto::new);
        if (result.getNumber() + 1 > result.getTotalPages() && result.getTotalPages() > 0)
            throw new NotFoundException();
        return ResponseEntity.ok(new PageDto<>(result));
    }

    @PostMapping(value = "/lessons/new",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Create new lesson")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LessonDto> create(@RequestBody CreateLessonRequest request) {
        try {
            Lesson created = lessonService.createLesson(request);
            return new ResponseEntity<>(new LessonDto(created), HttpStatus.ACCEPTED);
        }
        catch (IllegalArgumentException e) {
            throw new InternalServerErrorException();
        }
    }
}
