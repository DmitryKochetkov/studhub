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
public class ExamSpecification extends BaseEntity {
    @Column
    private String title; // название - по умолчанию содержит название экзамена и год.

    @Column
    private Integer startYear; // год, в котором введена

    @Column
    private Integer endYear; // год, в котором упразднена

    @ManyToOne
    @JoinColumn(name = "ref_course_id", referencedColumnName = "id")
    private RefCourse refCourse;

    @OneToMany(mappedBy = "examSpecification")
    private List<ProblemCode> problemCodes;
}
