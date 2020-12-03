package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "courses")
@Data
public class Course extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_type", referencedColumnName = "id", nullable = false, updatable = false)
    private RefCourse refCourse;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + this.getId() +
                ", student='" + student.getUsername() +
                "'}";
    }
}
