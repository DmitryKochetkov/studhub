package com.studhub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T> {
    int number;
    boolean hasNext;
    boolean hasPrevious;
    int totalPages;
    List<T> content;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
    }
}
