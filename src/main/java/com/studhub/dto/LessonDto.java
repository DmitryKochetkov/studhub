package com.studhub.dto;

import com.studhub.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    String topic;
    Date startDate;

    public LessonDto(Lesson lesson) {
        this.topic = lesson.getTopic();
        this.startDate = lesson.getStartDate();
    }
}
