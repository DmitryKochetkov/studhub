package com.studhub.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "choice_problem")
@Data
@DiscriminatorValue("choice_problem")
public class ChoiceProblem extends AbstractProblem {
    private Boolean multiple = false;

    @ElementCollection
    private List<String> answers;
}
