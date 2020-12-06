package com.studhub.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ref_verdict")
@Data
public class Verdict extends BaseEntity {
    private String code;
    private String transcription;
    private String description;

    @Override
    public String toString() {
        return "Verdict{" +
                "id='" + getId() + '\'' +
                "code='" + code + '\'' +
                '}';
    }
}
