package com.studhub.dto;

import com.studhub.entity.ChoiceProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChoiceProblemDto extends ProblemDto {
    private List<String> answers;

    public ChoiceProblemDto(ChoiceProblem problem) {
        super(problem);
        answers = problem.getAnswers();
    }
}
