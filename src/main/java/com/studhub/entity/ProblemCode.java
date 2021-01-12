package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Сущность, описывающая тип задачи из спецификации
 */
@Entity
@Table(name = "problem_codes")
@Data
public class ProblemCode extends BaseEntity {
    @Column
    private String description;

    @Column
    private Integer numberInSpecification;

    @ManyToOne
    @JoinColumn(name = "specification_id", referencedColumnName = "id", nullable = false)
    private ExamSpecification examSpecification;
}
