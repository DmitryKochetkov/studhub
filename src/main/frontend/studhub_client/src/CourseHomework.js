import React, {Component} from 'react';
import './App.css';
import Moment from 'react-moment';
import ErrorPage from "./ErrorPage";
import PaginationPanel from "./PaginationPanel";

class CourseHomework extends Component {
    constructor(props) {
        super(props);
        this.state = {
            courseHomeworkPage: null,
            error: null
        };
    }

    async componentDidMount() {
        const params = this.props.match.params;
        const response = await fetch('/api/course/' + params.courseId + '/homework' + this.props.location.search);
        if (response.ok) {
            const json = await response.json();
            this.setState({courseHomeworkPage: json});
        }
        else {
            this.setState({error: response});
        }
    }

    render() {
        const params = this.props.match.params;
        const {courseHomeworkPage, error} = this.state;

        if (error) {
            console.log(error);
            return <ErrorPage code={error.status} description={error.statusText}/>
        }
        if (courseHomeworkPage === null)
            return (<div>error</div>);

        document.title = 'StudHub: Домашние работы по курсу #' + params.courseId;
        const homeworkList = courseHomeworkPage.content.map((homework) => <tr key={homework.id}>
            <td>{homework.description}</td>
            <td>{homework.lessonId}</td>
            <td><Moment format='DD.MM.YYYY HH:mm'>{homework.deadline}</Moment></td>
            <td>{homework.solvedProblemsCount}/{homework.totalProblemsCount}</td>
            <td><a href={'/course/' + params.courseId + '/homework/' + homework.id}>Подробнее</a></td>
        </tr>);

        return (
            <div className="page-container">
                <div className='container'>
                    <div>
                        <h2 className='font-weight-bold'>Домашние работы</h2>
                        <span>
                            <span className='font-weight-bold'>по курсу </span>
                            <a className='btn-link' href={'/course/' + params.courseId}>#{params.courseId}</a>
                        </span>
                    </div>

                    <div className='pt-3'>
                        <table className='table small-font'>
                            <thead className='thead-light'>
                                <tr>
                                    <th>Тема</th>
                                    <th>Занятие</th>
                                    <th>Срок сдачи</th>
                                    <th>Прогресс</th>
                                    <th></th>
                                </tr>
                            </thead>
                                <tbody>
                                {homeworkList}
                                </tbody>
                        </table>

                        <PaginationPanel currentPage={courseHomeworkPage.number} totalPages={courseHomeworkPage.totalPages} path={this.props.location.pathname}/>
                    </div>
                </div>
            </div>

        );
    }
};

export default CourseHomework;