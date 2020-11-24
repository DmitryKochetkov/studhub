package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homework")
@Data
public class Homework extends BaseEntity {
    @Column
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Course course;

    @Column
    private String description;

//    @OneToMany(mappedBy = "homework")
//    private Problem problem;
}
