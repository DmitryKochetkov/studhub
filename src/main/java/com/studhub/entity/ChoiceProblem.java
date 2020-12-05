package com.studhub.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@Table(name = "choice_problem")
@Data
@DiscriminatorValue("choice_problem")
public class ChoiceProblem extends AbstractProblem {
    private Boolean multiple = false;
    //answersOptions;
}
