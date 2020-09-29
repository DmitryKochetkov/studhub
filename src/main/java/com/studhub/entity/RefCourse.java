package com.studhub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ref_courses")
public class RefCourse extends BaseEntity {
    @Column
    String title;
}
