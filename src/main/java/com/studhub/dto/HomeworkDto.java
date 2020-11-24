package com.studhub.dto;

import com.studhub.entity.Homework;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomeworkDto extends BaseDto {
    private LocalDateTime deadline;
    private Long courseId;
    private String description;

    public HomeworkDto(Homework homework) {
        super(homework);
        deadline = homework.getDeadline();
        courseId = homework.getCourse().getId();
        description = homework.getDescription();
    }
}
