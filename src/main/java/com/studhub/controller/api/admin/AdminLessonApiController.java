package com.studhub.controller.api.admin;

import com.studhub.dto.LessonDto;
import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import com.studhub.payload.CreateLessonRequest;
import com.studhub.service.LessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "Manage lessons", description = "Available only for administrator.")
public class AdminLessonApiController {
    @Autowired
    LessonService lessonService;

    @GetMapping("/lessons")
    @ApiOperation("Get all lessons")
    public ResponseEntity<List<LessonDto>> get() {
        List<LessonDto> lessons = new ArrayList<>();
        for (Lesson lesson: lessonService.getAll())
            lessons.add(new LessonDto(lesson));
        return ResponseEntity.ok(lessons);
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
