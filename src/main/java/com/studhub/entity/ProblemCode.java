package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Сущность, описывающая тип задачи из спецификации
 */
@Entity
@Table(name = "problem_codes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"specification_id", "number_in_specification"}),
})
@Data
public class ProblemCode extends BaseEntity {
    @Column
    private String description;

    @Column(name = "number_in_specification")
    private Integer numberInSpecification;

    @ManyToOne
    @JoinColumn(name = "specification_id", referencedColumnName = "id", nullable = false)
    private ExamSpecification examSpecification;
}
