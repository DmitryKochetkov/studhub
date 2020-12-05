package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "problem_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class AbstractProblem extends BaseEntity {
    @Column
    private String formulation;
}
