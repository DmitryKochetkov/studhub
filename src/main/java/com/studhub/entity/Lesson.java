package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lessons")
@Data
public class Lesson extends BaseEntity {
    @Column
    private String topic;

    @Column
    private LocalDateTime startDateTime;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @Override
    public String toString() {
        return super.toString();//"Lesson{id=id}";
    }
}
