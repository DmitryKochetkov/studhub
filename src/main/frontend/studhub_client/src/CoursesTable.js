import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Moment from "react-moment";

class CoursesTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            courses: []
        };
    }

    render() {
        const {courses} = this.state;
        const coursesTableBody = courses.map((course) =>
            <tr key={course.id}>
                <td>{course.id}</td>
                <td>{course.subject.title}</td>
                <td>{this.courseStatusUI[course.status]}</td>
                <td><Moment format='DD.MM.YYYY'>{course.created}</Moment></td>
                <td><a href={'/student/' + 2 + '/course/' + course.id}>Перейти</a></td>
            </tr>
        );
        return (
            <div>
                <h2 className='font-weight-bold'>Курсы</h2>
                <table className='table'>
                    <thead className='thead-light'>
                    <tr>
                        <th>ID</th>
                        <th>Предмет</th>
                        <th>Статус</th>
                        <th>Дата старта</th>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>{coursesTableBody}</tbody>
                </table>
            </div>
        );
    }
}

export default CoursesTable;