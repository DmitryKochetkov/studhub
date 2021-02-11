package com.studhub.dto;

import com.studhub.entity.Course;
import com.studhub.entity.CourseStatus;
import com.studhub.entity.Specification;
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
    private SubjectDto subject;
    private CourseStatus status;
    private LocalDateTime startDate;
    private List<LessonDto> comingLessons;
    private List<HomeworkDto> comingHomework;
    private Long activeSpecificationId;
    private List<TicketDto> lastTickets;

    public CourseDto(Course course) {
        super(course);
        this.id = course.getId();
        this.studentId = course.getStudent().getId();
        this.subject = new SubjectDto(course.getSubject());
        this.status = course.getCourseStatus();
        this.startDate = course.getCreated();

        Specification specification = course.getActiveSpecification();
        if (specification != null)
            this.activeSpecificationId = specification.getId();

        this.comingLessons = course.getLessons().stream().limit(3).map(LessonDto::new).collect(Collectors.toList());
        this.comingHomework = course.
                getHomework().stream().limit(3).map(HomeworkDto::new).collect(Collectors.toList());

        this.lastTickets = course.getTickets().stream().limit(4).map(TicketDto::new).collect(Collectors.toList());
    }

    public List<LessonDto> getComingLessons() {
        return comingLessons;
    }
}
