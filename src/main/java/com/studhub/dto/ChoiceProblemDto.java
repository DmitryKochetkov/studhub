package com.studhub.dto;

import com.studhub.entity.ChoiceProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ChoiceProblemDto extends ProblemDto {
    private List<String> answers;

    public ChoiceProblemDto(ChoiceProblem problem) {
        super(problem);
        answers = problem.getAnswers().stream().map(answer -> answer.getText()).collect(Collectors.toList());
    }
}
