package com.studhub.dto;

import com.studhub.entity.Submission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmissionDto extends BaseDto {
    private String answer;
    private String verdict;
    private Long homeworkId;
    private Long problemId;

    public SubmissionDto(Submission submission) {
        super(submission);
        this.homeworkId = submission.getHomeworkProblem().getHomework().getId();
        this.problemId = submission.getHomeworkProblem().getProblem().getId();
        this.answer = submission.getAnswer();
        if (submission.getVerdict() != null)
            this.verdict = submission.getVerdict().getCode();
    }
}
