package com.studhub.dto;

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

//    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime startDateTime;

    public LessonDto(Lesson lesson) {
        super(lesson);
        this.topic = lesson.getTopic();
        this.startDateTime = lesson.getStartDateTime();
        this.courseId = lesson.getCourse().getId();
        this.studentId = lesson.getCourse().getStudent().getId();
        this.status = lesson.getStatus();
    }
}
