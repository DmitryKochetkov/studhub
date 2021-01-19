package com.studhub.dto;

import com.studhub.entity.Specification;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SpecificationDto extends BaseDto {
    private String title;
    private Long refCourseId;
    private List<ProblemCodeDto> problemCodes;

    public SpecificationDto(Specification specification) {
        super(specification);
        title = specification.getTitle();
        refCourseId = specification.getSubject().getId();
        problemCodes = specification.getProblemCodes().stream()
                .map(ProblemCodeDto::new)
                .collect(Collectors.toList());
    }
}
