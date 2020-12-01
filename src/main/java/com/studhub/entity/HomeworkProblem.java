package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "homework_problems",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"homework_id", "number_in_homework"}),
            @UniqueConstraint(columnNames = {"homework_id", "problem_id"})
        })
@Data
public class HomeworkProblem extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "homework_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Homework homework;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "id", nullable = false, updatable = false)
    private AbstractProblem problem;

    @Column
    private boolean required;

    @Column(updatable = false)
    private Integer maxAttempts;

    @Column
    private Integer usedAttempts;

    @Column(name = "number_in_homework")
    private Integer numberInHomework; // номер задачи в домашней работе
}
