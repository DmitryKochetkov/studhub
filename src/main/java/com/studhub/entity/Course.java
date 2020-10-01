package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    User student;

    @ManyToOne
    @JoinColumn(name = "course_type", referencedColumnName = "id", nullable = false, updatable = false)
    RefCourse refCourse;

    @Enumerated(EnumType.STRING)
    CourseStatus courseStatus;

    @OneToMany(mappedBy = "course")
    List<Lesson> lessons;

    //CodificatorProgress codificatorProgress = new Codificator(refCourse.getCodificatorTemplate())
}
