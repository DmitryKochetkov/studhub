package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "homework")
@Data
public class Homework extends BaseEntity {
    @Column
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Course course; //TODO: remove from here

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @Column
    private String description;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private List<HomeworkProblem> problems;

    @Override
    public String toString() {
        return "Homework{" +
                "id='" + getId() + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}
