package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lessons")
@Data
public class Lesson extends BaseEntity {
    @Column
    String topic;

    @Column
    Date startDate;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    Course course;

    @Enumerated(EnumType.STRING)
    LessonStatus status;
}
