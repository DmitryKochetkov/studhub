package com.studhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "students")
public class Student extends User {
    @OneToMany(mappedBy = "student")
    private List<Course> courses;

    public Student() {
        courses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", username='" + this.getUsername() + '\'' +
                '}';
    }
}
