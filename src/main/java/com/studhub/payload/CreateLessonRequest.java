package com.studhub.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Тело запроса на создание урока
 */
@Data
@NoArgsConstructor
public class CreateLessonRequest {
    private String topic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    private String studentUsername;
    private String courseTitle;
}
