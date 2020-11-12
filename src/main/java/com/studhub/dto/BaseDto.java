package com.studhub.dto;

import com.studhub.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BaseDto {
    protected Long id;
    protected Date created;
    protected Date lastModified;

    BaseDto(BaseEntity entity) {
        this.id = entity.getId();
        this.created = entity.getCreated();
        this.lastModified = entity.getLastModified();
    }
}
