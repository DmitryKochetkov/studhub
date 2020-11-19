package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Тело запроса на создание урока
 */
@Data
@NoArgsConstructor
public class CreateLessonRequest {
    private String topic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "HH:mm")
    private Date startTime;

    private String studentUsername;
    private String courseTitle;
}
