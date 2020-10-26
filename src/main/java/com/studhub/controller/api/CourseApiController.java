package com.studhub.controller.api;

import com.studhub.dto.CourseDto;
import com.studhub.entity.Course;
import com.studhub.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.Media;

@Controller
@RequestMapping("/api/course/{course_id}")
@Api(tags = "Courses", description = "Access course progress.")
public class CourseApiController {
    @Autowired
    CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get course by id")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long course_id) {
        Course course = courseService.getById(course_id);
        if (course == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new CourseDto(course));
    }


}
