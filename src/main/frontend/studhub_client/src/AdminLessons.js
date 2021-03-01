import React, {Component} from 'react';
import App from './App';
import Moment from 'react-moment';
import ErrorPage from "./ErrorPage";
import PaginationPanel from "./PaginationPanel";

class AdminLessons extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lessonsPage: null,
            error: null
        };
    }

    async componentDidMount() {
        const response = await fetch('/api/admin/lessons' + this.props.location.search);
        if (response.ok) {
            const json = await response.json();
            this.setState({lessonsPage: json});
        }
        else {
            this.setState({error: response})
        }
    }

    render() {
        document.title = 'StudHub: Уроки';
        const {lessonsPage, error} = this.state;

        if (error) {
            console.log(error);
            return <ErrorPage code={error.status} description={error.statusText}/>
        }

        try {
            const lessonsTable = lessonsPage.content.map((lesson) =>
                <tr key={lesson.id}>
                    <td>{lesson.id}</td>
                    <td><Moment format='DD.MM.YYYY HH:mm'>{lesson.startDateTime}</Moment></td>
                    <td><a href={'/user/' + lesson.studentId}>{lesson.studentId}</a></td>
                    <td>{lesson.topic}</td>
                    <td>{App.lessonStatusUI[lesson.status]}</td>
                    <td><a href={'/student/' + 2 + '/course/' + 1 + '/lesson/' + 1}>Перейти</a></td>
                </tr>
            );

            return (
                <div className="page-container">
                    <div className='container'>
                        <h1 className='font-weight-bold pb-3'>Уроки</h1>
                        <div>
                            <table className='table'>
                                <thead className='thead-light'>
                                <tr>
                                    <th>ID</th>
                                    <th>Дата</th>
                                    <th>Ученик</th>
                                    <th>Тема</th>
                                    <th>Статус</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {lessonsTable}
                                </tbody>
                            </table>

                            <PaginationPanel currentPage={lessonsPage.number} totalPages={lessonsPage.totalPages} path={this.props.location.pathname}/>
                        </div>

                        {/*TODO: redirect to lesson page*/}
                        <form action='/admin/lessons/new' method='get'>
                            <button type='submit' className='btn btn-primary'>Создать урок</button>
                        </form>
                    </div>
                </div>
            );
        }
        catch {
            return <div>error</div>;
        }
    }
}

export default AdminLessons;