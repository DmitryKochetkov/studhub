package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "submissions")
@Data
public class Submission extends BaseEntity {
    @Column
    private String answer;

    @Column
    private String verdict;

    @ManyToOne
    @JoinColumn(name = "homework_problem_id", referencedColumnName = "id")
    private HomeworkProblem homeworkProblem;
}
