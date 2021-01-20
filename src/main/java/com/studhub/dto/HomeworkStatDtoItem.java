package com.studhub.dto;

import com.studhub.entity.Homework;
import com.studhub.entity.HomeworkProblem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomeworkStatDtoItem {
    private LocalDateTime date;
    private Double percentage;

    public HomeworkStatDtoItem(Homework homework) {
        date = homework.getDeadline();

        int total = homework.getProblems().size();
        if (total == 0) {
            percentage = 0.0;
            return;
        }

        int correct = 0;
        for (HomeworkProblem homeworkProblem: homework.getProblems()) {
            if (homeworkProblem.getSubmissions().stream().anyMatch(submission -> submission.getVerdict().getCode().equals("OK")))
                correct += 1;
        }
        percentage = (double) correct / total;
    }
}
