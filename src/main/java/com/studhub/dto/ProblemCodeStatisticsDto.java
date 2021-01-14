package com.studhub.dto;

import lombok.Data;

@Data
public class ProblemCodeStatisticsDto {
    private Integer problemCodeId;
    private Integer totalSubmissions; // все посылки задач данного типа, за вычетом посылок со стасусом SE
    private Integer correctSubmissions; // посылки, оцененные статусом OK
}
