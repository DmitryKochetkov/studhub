import React, {Component} from "react";
import "./App.css";
import Header from "./Header";
import HomeworkProblems from "./HomeworkProblems";
import HomeworkSubmissions from "./HomeworkSubmissions";

class Homework extends Component {
    constructor(props) {
        super(props);
        this.state = {
            homework: null,
            problemNumber: 1
        };
    }

    componentDidMount() {
        const params = this.props.match.params;
        fetch("/api/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + params.homeworkId)
            .then((res) => res.json())
            .then((result) => {
                this.setState({
                    homework: result,
                    problemNumber: this.state.problemNumber
                });
            })
    }

    render() {
        const params = this.props.match.params;
        const {homework} = this.state;
        if (homework === null)
            return (<div>error</div>);

        document.title = "StudHub: Домашняя работа #" + homework.id;

        const tabName = this.props.tab;
        let tab;

        if (tabName === "description")
            tab = <div>
                <h3 className="font-weight-bold">Описание</h3>
                <p>{homework.description ? homework.description : "Преподаватель не оставил описания для этой работы."}</p>
            </div>;
        else if (tabName === "problems")
            tab = <HomeworkProblems homework={homework} params={this.props.match.params}/>;
        else if (tabName === "submissions")
            tab = <HomeworkSubmissions homework={homework} params={this.props.match.params}/>;

        return (
            <div>
                <Header/>
                <div className="container">
                    <div>
                        <h2 className="font-weight-bold">Домашняя работа</h2>
                        <span>
                            <span className="font-weight-bold">
                                <span>по курсу </span>
                                <a className="btn-link" href={'/student/' + params.studentId + '/course/' + params.courseId}>#{params.courseId}</a>
                            </span>

                            <span className="font-weight-bold"> к уроку </span>
                            <a className="btn-link" href={'/student/' + params.studentId + '/course/' + params.courseId + '/lesson/' + homework.lessonId}>#{homework.lessonId}</a>
                        </span>
                    </div>

                    <div className="pt-3">
                        <span>Срок сдачи: {homework.deadline}, до дедлайна осталось {homework.remainingSeconds} секунд</span>
                    </div>

                    <div className="pt-3 pb-2">
                        <ul className="nav nav-tabs" id="homework" role="tablist">
                            <li className="nav-item">
                                <a className="nav-link" href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + homework.id}>Описание</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + homework.id + "/problems/" + this.state.problemNumber}>Задачи</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + homework.id + "/submissions"}>Посылки</a>
                            </li>
                        </ul>
                    </div>

                    {tab}
                </div>
            </div>

        );
    }
};

export default Homework;