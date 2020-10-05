package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studhub.entity.Lesson;
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

    public LessonDto(Lesson lesson) {
        super(lesson);
        this.topic = lesson.getTopic();
        this.startDate = lesson.getStartDate();
        this.courseId = lesson.getCourse().getId();
    }
}
