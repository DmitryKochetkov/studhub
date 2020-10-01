package com.studhub.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ref_courses")
@Data
public class RefCourse extends BaseEntity {
    @Column
    String title;
}
