package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studhub.entity.Lesson;
import com.studhub.entity.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto extends BaseDto {
    private String topic;
    private Long courseId;
    private Long studentId;
    private LessonStatus status;

    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime startDate;

    public LessonDto(Lesson lesson) {
        super(lesson);
        this.topic = lesson.getTopic();
        this.startDate = lesson.getStartDateTime();
        this.courseId = lesson.getCourse().getId();
        this.studentId = lesson.getCourse().getStudent().getId();
        this.status = lesson.getStatus();
    }
}
