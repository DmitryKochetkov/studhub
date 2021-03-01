package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Сущность, описывающая тип задачи из спецификации
 */
@Entity
@Table(name = "problem_codes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"specification_id", "index_in_specification"}),
})
@Data
public class ProblemCode extends BaseEntity {
    @Column
    private String description;

    @Column(name = "index_in_specification")
    private Integer indexInSpecification;

    @ManyToOne
    @JoinColumn(name = "specification_id", referencedColumnName = "id", nullable = false)
    private Specification specification;
}
