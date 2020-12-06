package com.studhub.dto;

import com.studhub.entity.Verdict;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerdictDto extends BaseDto {
    private String code;
    private String transcription;
    private String description;

    public VerdictDto(Verdict verdict) {
        super(verdict);
        code = verdict.getCode();
        transcription = verdict.getTranscription();
        description = verdict.getDescription();
    }
}
