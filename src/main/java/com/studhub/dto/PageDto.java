package com.studhub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T> {
    private int number;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;
    private List<T> content;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber() + 1;
        this.hasNext = number < totalPages;
        this.hasPrevious = number > 1;
    }
}
