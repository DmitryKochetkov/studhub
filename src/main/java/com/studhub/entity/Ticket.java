package com.studhub.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Сущность, описывающая тикет - обсуждение вопроса ученика или преподавателя в ходе курса
 */
@Entity
@Table(name = "tickets",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_id", "index"})
})
@Data
public class Ticket extends BaseEntity {
    private Integer index; // номер в курсе

    @Column
    private String title;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
}
