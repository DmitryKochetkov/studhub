package com.studhub.dto;

import com.studhub.entity.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDto extends BaseDto {
    private String title;
    private String body;
    private UserDto author;
    private Integer index;

    public TicketDto(Ticket ticket) {
        super(ticket);
        this.title = ticket.getTitle();
        this.body = ticket.getBody();
        this.author = new UserDto(ticket.getAuthor());
        this.index = ticket.getIndex();
    }
}
