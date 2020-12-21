package com.studhub.dto;

import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CourseDto extends BaseDto {
    private Long studentId;
    private String title;
    private CourseStatus status;
    private LocalDateTime startDate;
    private List<LessonDto> comingLessons;
    private List<HomeworkDto> comingHomework;

    public CourseDto(Course course) {
        super(course);
        this.id = course.getId();
        this.studentId = course.getStudent().getId();
        this.title = course.getRefCourse().getTitle();
        this.status = course.getCourseStatus();
        this.startDate = course.getCreated();
        this.comingLessons = course.getLessons().stream().limit(3).map(LessonDto::new).collect(Collectors.toList());
        this.comingHomework = course.
                getHomework().stream().limit(3).map(HomeworkDto::new).collect(Collectors.toList());
    }

    public List<LessonDto> getComingLessons() {
        return comingLessons;
    }
}
