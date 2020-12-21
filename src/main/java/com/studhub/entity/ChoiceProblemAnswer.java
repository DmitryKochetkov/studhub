package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "choice_problem_answers")
@Data
public class ChoiceProblemAnswer extends BaseEntity {
    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "choice_problem_id", referencedColumnName = "id", nullable = false)
    private ChoiceProblem choiceProblem;

    @Column
    private boolean correct = false;
}
