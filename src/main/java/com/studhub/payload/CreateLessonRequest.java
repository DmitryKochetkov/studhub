package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreateLessonRequest {
    String topic;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date startDate;

    @DateTimeFormat(pattern = "hh:mm")
    Date startTime;

    String studentUsername;
    String courseTitle;
}
