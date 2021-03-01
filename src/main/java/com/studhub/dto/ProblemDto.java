package com.studhub.dto;

import com.studhub.entity.AbstractProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;

@Data
@NoArgsConstructor
public class ProblemDto extends BaseDto {
    private String type;
    private String formulation;

    public ProblemDto(AbstractProblem problem) {
        super(problem);
        formulation = problem.getFormulation();
        type = problem.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
