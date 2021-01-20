package com.studhub.dto;

import com.studhub.entity.ProblemCode;
import lombok.Data;

@Data
public class ProblemCodeDto extends BaseDto {
    private String description;
    private Integer indexInSpecification;

    public ProblemCodeDto(ProblemCode problemCode) {
        super(problemCode);
        description = problemCode.getDescription();
        indexInSpecification = problemCode.getIndexInSpecification();
    }
}
