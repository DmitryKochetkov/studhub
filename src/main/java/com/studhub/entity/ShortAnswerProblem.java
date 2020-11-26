package com.studhub.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "short_answer_problem")
@Data
public class ShortAnswerProblem extends AbstractProblem {
    @Column
    String answer;
}
