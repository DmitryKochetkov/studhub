import React, {Component} from "react";
import "./App.css";
import Header from "./Header";
import Moment from "react-moment";

class CourseHomework extends Component {
    constructor(props) {
        super(props);
        this.state = {
            courseHomeworkPage: null
        };
    }

    componentDidMount() {
        const params = this.props.match.params;
        fetch("/api/student/" + params.studentId + "/course/" + params.courseId + "/homework/")
            .then((res) => res.json())
            .then((result) => {
                this.setState({
                    courseHomeworkPage: result
                });
            })
    }

    render() {
        const params = this.props.match.params;
        const {courseHomeworkPage} = this.state;
        if (courseHomeworkPage === null)
            return (<div>error</div>);

        document.title = "StudHub: Домашние работы по курсу #" + params.courseId;
        const homeworkList = courseHomeworkPage.content.map((homework) => <tr key={homework.id}>
            <td>{homework.description}</td>
            <td>{homework.lessonId}</td>
            <td><Moment format="DD.MM.YYYY HH:mm">{homework.deadline}</Moment></td>
            <td>{homework.solvedProblemsCount}/{homework.totalProblemsCount}</td>
            <td><a href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + homework.id}>Подробнее</a></td>
        </tr>);

        const pagination = [];
        const x = Math.max(parseInt(courseHomeworkPage["number"]) - 5, 1);
        for (let i = x; i < x + Math.min(10, courseHomeworkPage.totalPages); i++)
            pagination.push(<li className="page-item"><a className="page-link" href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework?page=" + i.valueOf()}>{i}</a></li>);

        return (
            <div>
                <Header/>
                <div className="container">
                    <div>
                        <h2 className="font-weight-bold">Домашние работы</h2>
                        <span>
                            <span className="font-weight-bold">по курсу </span>
                            <a className="btn-link" href={'/student/' + params.studentId + '/course/' + params.courseId}>#{params.courseId}</a>
                        </span>
                    </div>

                    <div className="pt-3">
                        <table className="table small-font">
                            <thead className="thead-light">
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

                        <ul className="pagination">
                            {pagination}
                        </ul>
                    </div>
                </div>
            </div>

        );
    }
};

export default CourseHomework;