package com.studhub.dto;

import com.studhub.entity.ChoiceProblem;
import com.studhub.entity.HomeworkProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomeworkProblemDto {

    private Long homeworkId;
    private Integer number;
    private boolean required;
    private Integer maxAttempts;
    private Integer usedAttempts;
    private ProblemDto problem;

    public HomeworkProblemDto(HomeworkProblem homeworkProblem) {
        required = homeworkProblem.isRequired();
        maxAttempts = homeworkProblem.getMaxAttempts();
        usedAttempts = homeworkProblem.getSubmissions().size();

        homeworkId = homeworkProblem.getHomework().getId();
        problem = new ProblemDto(homeworkProblem.getProblem());
        if (homeworkProblem.getProblem() instanceof ChoiceProblem)
            problem = new ChoiceProblemDto((ChoiceProblem) homeworkProblem.getProblem());

        number = homeworkProblem.getNumberInHomework();
    }
}
