import React, {Component} from "react";
import "./App.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faQuestionCircle} from "@fortawesome/free-solid-svg-icons";

class HomeworkSubmissions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            homework: props.homework,
            submissionsPage: null
        };
    }

    componentDidMount() {
        const params = this.props.params;
        fetch("/api/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + params.homeworkId + "/submissions")
            .then((res) => res.json())
            .then((result) => {
                this.setState({
                    homework: this.state.homework,
                    submissionsPage: result
                });
            })
    }

    render() {
        const params = this.props.params;
        const {homework, submissionsPage} = this.state;

        if (submissionsPage === null)
            return <div>error</div>;

        const submissions = submissionsPage.content.map((submission) => <tr>
            <td>{submission.id}</td>
            <td>{submission.created}</td>
            <td>{submission.problemId}</td>
            <td>{submission.answer}</td>
            <td style={submission.verdict === 'OK' ? {color: "green"} : {color: "red"}}>{submission.verdict}</td>
        </tr>);

        return <div>
            <h3 className="font-weight-bold pb-2">Посылки</h3>
            <p>Если указано, что ваше решение тестируется, для обновления результатов необходимо обновить страницу.</p>

            <table className="table">
                <thead className="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Время посылки</th>
                        <th>Задача</th>
                        <th>Ответ</th>
                        <th>
                            <span>Вердикт </span>
                            <a href="/verdicts">
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top"/>
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                {submissions}
                </tbody>
            </table>
        </div>
    }
};

export default HomeworkSubmissions;