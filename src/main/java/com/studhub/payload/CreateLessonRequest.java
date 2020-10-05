package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreateLessonRequest {
    String topic;

    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    Date startDate;

    String studentUsername;
    String courseTitle;
}
