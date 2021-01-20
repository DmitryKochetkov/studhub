package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "problems")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "problem_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class AbstractProblem extends BaseEntity {
    @Column
    private String formulation;

    @ManyToMany
    @JoinTable(name = "problem_code_mapping",
            joinColumns = {@JoinColumn(name = "problem_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "problem_code_id", referencedColumnName = "id")})
    private List<ProblemCode> problemCodes;

    @Override
    public String toString() {
        return "AbstractProblem{" +
                "id=" + getId() +
                ", formulation=" + formulation +
                "}";
    }
}
