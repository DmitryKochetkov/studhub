package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class CreateLessonRequest {
    String topic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

    @DateTimeFormat(pattern = "HH:mm")
    Date startTime;

    String studentUsername;
    String courseTitle;
}
