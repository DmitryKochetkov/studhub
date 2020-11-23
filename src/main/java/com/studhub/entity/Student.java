package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "students")
@NoArgsConstructor
public class Student extends User {
    @OneToMany(mappedBy = "student")
    private List<Course> courses;
}
