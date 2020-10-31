package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import com.studhub.entity.RefCourse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CourseDto extends BaseDto {
    Long studentId;
    String title;
    CourseStatus status;
    Date startDate;
    List<LessonDto> comingLessons;

    public CourseDto(Course course) {
        super(course);
        this.id = course.getId();
        this.studentId = course.getStudent().getId();
        this.title = course.getRefCourse().getTitle();
        this.status = course.getCourseStatus();
        this.startDate = course.getCreated();
        this.courseTitle = course.getRefCourse().getTitle();
        this.comingLessons = course.getLessons().stream().limit(3).map(LessonDto::new).collect(Collectors.toList());
    }

    public List<LessonDto> getComingLessons() {
        return comingLessons;
    }
}
