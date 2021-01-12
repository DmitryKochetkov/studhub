package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Предмет, по которому читается курс
 */
@Entity
@Table(name = "ref_courses")
@Data
public class RefCourse extends BaseEntity {
    @Column
    private String title;

    @OneToMany(mappedBy = "refCourse")
    private List<ExamSpecification> examSpecifications; // возможные спецификации экзамена (если курс готовит к нему)

    @OneToOne
    private ExamSpecification currentExamSpecification; // актуальная спецификация, по которой ведется сбор статистики
}
