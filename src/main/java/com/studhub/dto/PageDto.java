package com.studhub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T> {
    int page;
    boolean hasNext;
    boolean hasPrevious;
    List<T> content;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
    }
}
