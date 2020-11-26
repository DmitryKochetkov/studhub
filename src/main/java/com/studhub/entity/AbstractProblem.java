package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class AbstractProblem extends BaseEntity {
    @Column
    private String formulation;

    @Column
    private boolean required; // Определяет попадет ли задание в общую статистику по заданием данного типа
}
