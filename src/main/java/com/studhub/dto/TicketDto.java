package com.studhub.dto;

import com.studhub.entity.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDto extends BaseDto {
    private String title;
    private String body;

    public TicketDto(Ticket ticket) {
        super(ticket);
        this.title = ticket.getTitle();
        this.body = ticket.getBody();
    }
}
