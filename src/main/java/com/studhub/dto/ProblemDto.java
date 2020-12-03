package com.studhub.dto;

import com.studhub.entity.AbstractProblem;
import com.studhub.entity.ShortAnswerProblem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemDto extends BaseDto {
    private String type = "unknown";
    private String formulation;

    public ProblemDto(AbstractProblem problem) {
        super(problem);
        formulation = problem.getFormulation();
        if (problem instanceof ShortAnswerProblem)
            type = "shortAnswer"; //TODO: изменить стратегию наследования, хранить тип задачи как атрибут сущности в базе
    }
}
