package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lessons")
@Data
public class Lesson extends BaseEntity {
    @Column
    private String topic;

    @Column
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;
}
