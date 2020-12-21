package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "abstract_problem")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "problem_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class AbstractProblem extends BaseEntity {
    @Column
    private String formulation;

    @Override
    public String toString() {
        return "AbstractProblem{" +
                "id=" + getId() +
                ", formulation=" + formulation +
                "}";
    }
}
