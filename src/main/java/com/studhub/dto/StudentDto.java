package com.studhub.dto;

import com.studhub.entity.Course;
import com.studhub.entity.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentDto extends UserDto {
    private List<CourseDto> courses;

    public StudentDto(Student student) {
        super(student);
        courses = new ArrayList<>();
        if (student.getCourses() != null)
            for (Course course: student.getCourses())
                courses.add(new CourseDto(course));
    }
}
