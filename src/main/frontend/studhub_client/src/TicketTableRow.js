import React, {Component} from 'react';
import Moment from "react-moment";

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
            <div className='font-weight-bold'><a href='/student/2/course/1/ticket/1'>{ticket.title}</a></div>
            <div className='ticket-caption'>#{ticket.index}, открыт <Moment format='DD.MM.YYYY'>{ticket.created}</Moment>, автор <a href={'/user/' + ticket.author.id}>{ticket.author.username}</a></div>
        </li>;
    }
}

export default TicketTableRow;