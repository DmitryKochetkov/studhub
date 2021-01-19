package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность, описывающая спецификацию экзамена (формат заданий).
 *  */
@Entity
@Table
@Data
public class Specification extends BaseEntity {
    @Column
    private String title; // название - по умолчанию содержит название экзамена и год.

    @ManyToOne
    @JoinColumn(name = "ref_course_id", referencedColumnName = "id")
    private RefCourse refCourse;

    @OneToMany(mappedBy = "specification")
    private List<ProblemCode> problemCodes;
}
