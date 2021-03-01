import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

class PaginationPanel extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let {currentPage, totalPages, path} = this.props;

        const pagination = [];
        const x = Math.max(currentPage - 5, 1);
        for (let i = x; i < x + Math.min(10, totalPages); i++)
            pagination.push(<li className='page-item'><a className='page-link' href={path + '?page=' + i}>{i}</a></li>);

        return (
            <ul className='pagination'>
                {pagination}
            </ul>
        );
    }
}

export default PaginationPanel;