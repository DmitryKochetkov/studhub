package com.studhub.dto;

import com.studhub.entity.AbstractProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProblemDto extends BaseDto {
    private String type;
    private String formulation;
    private List<String> answers;

    public ProblemDto(AbstractProblem problem) {
        super(problem);
        formulation = problem.getFormulation();
        type = problem.getClass().getAnnotation(DiscriminatorValue.class).value();
        answers = new ArrayList<>();
        answers.add("Da");
        answers.add("Nyet");
        answers.add("Ne znau, comrade");
    }
}
