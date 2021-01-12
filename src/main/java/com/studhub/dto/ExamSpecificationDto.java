package com.studhub.dto;

import com.studhub.entity.ExamSpecification;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExamSpecificationDto extends BaseDto {
    private String title;
    private Integer startYear;
    private Integer endYear;
    private Long refCourseId;
    private List<ProblemCodeDto> problemCodes;

    public ExamSpecificationDto(ExamSpecification specification) {
        super(specification);
        title = specification.getTitle();
        startYear = specification.getStartYear();
        endYear = specification.getEndYear();
        refCourseId = specification.getRefCourse().getId();
        problemCodes = specification.getProblemCodes().stream()
                .map(ProblemCodeDto::new)
                .collect(Collectors.toList());
    }
}
