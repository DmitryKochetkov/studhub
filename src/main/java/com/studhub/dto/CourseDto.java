package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import com.studhub.entity.RefCourse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseDto extends BaseDto {
    Long id;
    Long studentId;
    String title;
    CourseStatus status;
    Date startDate;
    String courseTitle;

    List<LessonDto> lessons;

    public CourseDto(Course course) {
        super(course);
        this.id = course.getId();
        this.studentId = course.getStudent().getId();
        this.title = course.getRefCourse().getTitle();
        this.status = course.getCourseStatus();
        this.startDate = course.getCreated();
        this.courseTitle = course.getRefCourse().getTitle();
    }

    public List<LessonDto> getComingLessons() {
        List<LessonDto> result = new ArrayList<>();

        return result;
    }
}
