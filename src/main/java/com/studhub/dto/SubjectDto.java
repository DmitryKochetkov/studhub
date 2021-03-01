package com.studhub.dto;

import com.studhub.entity.Subject;
import lombok.Data;

@Data
public class SubjectDto {
    private Long id;
    private String title;

    public SubjectDto(Subject subject) {
        this.id = subject.getId();
        this.title = subject.getTitle();
    }
}
