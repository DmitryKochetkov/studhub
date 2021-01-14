package com.studhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseStatisticsByExamSpecificationDto {
    private Long specificationId;
    private Long courseId;
    private List<ProblemCodeStatisticsDto> statistics;
}
