import React, {Component} from 'react';

class TicketTableRow extends Component {
    constructor(props) {
        super(props);
        this.state = {
            ticket: props.ticket
        };
    }

    render() {
        const ticket = this.state.ticket;
        return <li className='box-row'>
            <div className='font-weight-bold'><a href='#'>{ticket.title}</a></div>
            <div className='ticket-caption'>#{ticket.index}, открыт {ticket.created}, автор <a href={'/user/' + ticket.author.id}>{ticket.author.username}</a></div>
        </li>;
    }
}

export default TicketTableRow;