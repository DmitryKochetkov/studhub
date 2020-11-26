package com.studhub.dto;

import com.studhub.entity.AbstractProblem;

public class ProblemDto extends BaseDto {
    String type;
    String formulation;

    public ProblemDto(AbstractProblem problem) {
        super();
        formulation = problem.getFormulation();
        type = "unknown";
    }
}
