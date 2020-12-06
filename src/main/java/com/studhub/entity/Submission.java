package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "submissions")
@Data
public class Submission extends BaseEntity {
    @Column
    private String answer;

    @ManyToOne
    @JoinColumn(name = "verdict_id", referencedColumnName = "id")
    private Verdict verdict;

    @ManyToOne
    @JoinColumn(name = "homework_problem_id", referencedColumnName = "id")
    private HomeworkProblem homeworkProblem;

    @Override
    public String toString() {
        return "Submission{" +
                "id='" + getId() + '\'' +
                '}';
    }
}
