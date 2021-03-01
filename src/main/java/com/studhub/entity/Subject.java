package com.studhub.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Предмет, по которому читается курс
 */
@Entity
@Table(name = "subjects")
@Data
public class Subject extends BaseEntity {
    @Column
    private String title;

    @OneToMany(mappedBy = "subject")
    private List<Specification> specifications; // возможные спецификации экзамена (если курс готовит к нему)
}
