package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto extends BaseDto {
    String topic;

    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    Date startDate;

    Long courseId;
    Long studentId;

    LessonStatus status;

    public LessonDto(Lesson lesson) {
        super(lesson);
        this.topic = lesson.getTopic();
        this.startDate = lesson.getStartDate();
        this.courseId = lesson.getCourse().getId();
        this.studentId = lesson.getCourse().getStudent().getId();
        this.status = lesson.getStatus();
    }
}
