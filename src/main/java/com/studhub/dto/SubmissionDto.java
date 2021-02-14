package com.studhub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.studhub.entity.Submission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmissionDto extends BaseDto {
    private String answer;
    private String verdict;

    @JsonProperty("homeworkProblem")
    private HomeworkProblemDto homeworkProblemDto;

    public SubmissionDto(Submission submission) {
        super(submission);
        this.homeworkProblemDto = new HomeworkProblemDto(submission.getHomeworkProblem());
        this.answer = submission.getAnswer();
        if (submission.getVerdict() != null)
            this.verdict = submission.getVerdict().getCode();
    }
}
