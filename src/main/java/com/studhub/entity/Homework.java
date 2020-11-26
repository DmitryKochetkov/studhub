package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "homework")
@Data
public class Homework extends BaseEntity {
    @Column
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "homework_problem",
            joinColumns = {@JoinColumn(name = "homework_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "problem_id", referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"homework_id", "problem_id"}))
    private List<AbstractProblem> problems;

    //private List<Submission> submissions;
}
