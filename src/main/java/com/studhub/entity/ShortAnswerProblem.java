package com.studhub.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "short_answer_problem")
@Data
@DiscriminatorValue("short_answer_problem")
public class ShortAnswerProblem extends AbstractProblem {
    @Column
    private String correctAnswer;
}
