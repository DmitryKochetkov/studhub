package com.studhub.dto;

import com.studhub.entity.Homework;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HomeworkDto extends BaseDto {
    private LocalDateTime deadline;
    private Long courseId;
    private String description;
    private Long lessonId;
    private Integer totalProblemsCount;
    private Integer solvedProblemsCount;

    public HomeworkDto(Homework homework) {
        super(homework);
        deadline = homework.getDeadline();
        courseId = homework.getCourse().getId();
        if (homework.getLesson() != null)
            lessonId = homework.getLesson().getId();
        description = homework.getDescription();
        totalProblemsCount = homework.getProblems().size();
        //solvedProblemsCount = homework.getSubmissions().stream().filter(submitted).distinct(by problem id).toList();
    }
}
