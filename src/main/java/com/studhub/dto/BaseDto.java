package com.studhub.dto;

import com.studhub.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseDto {
    protected Long id;
    protected LocalDateTime created;
    protected LocalDateTime lastModified;

    BaseDto(BaseEntity entity) {
        this.id = entity.getId();
        this.created = entity.getCreated();
        this.lastModified = entity.getLastModified();
    }
}
