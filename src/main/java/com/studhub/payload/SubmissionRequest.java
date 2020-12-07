package com.studhub.payload;

import lombok.Data;

@Data
public class SubmissionRequest {
    private String answer;
    private Long homeworkId;
    private Integer problemNumber;
    //String programmingLanguage; - polymorphism
}
