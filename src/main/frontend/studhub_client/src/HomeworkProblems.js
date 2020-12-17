import React, {Component} from "react";
import "./App.css";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faQuestionCircle} from "@fortawesome/free-solid-svg-icons";

class HomeworkProblems extends Component {
    constructor(props) {
        super(props);
        this.submitProblem = this.submitProblem.bind(this);
        this.state = {
            homework: props.homework,
            problemInfo: null,
            params: props.params
        };
    };

    componentDidMount() {
        const params = this.state.params;
        fetch("/api/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + params.homeworkId + "/problems/" + params.problemNumber)
            .then((res) => res.json())
            .then((result) => {
                this.setState({
                    homework: this.state.homework,
                    problemInfo: result,
                    params: this.state.params
                });
            })
    }

    submitProblem() {
        console.log(this);
        const params = this.props.params;
        if (this.state.problemInfo.usedAttempts < this.state.problemInfo.maxAttempts) {
            const requestOptions = {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    answer: document.getElementById("answer").value
                })
            };
            fetch("/api/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + params.homeworkId + "/problems/" + params.problemNumber + "/submit", requestOptions)
                .then((response) => {
                    // if (response.status === 200) {
                    //     this.setState({success: true});
                    // } else {
                    //     this.setState({success: false});
                    // }
                    console.log(response);
                })
                .then(response => response.json());
        }
    }

    render() {
        const params = this.props.params;
        const {homework, problemInfo} = this.state;
        if (homework === null || problemInfo === null)
            return (<div>error</div>);

        const problemButtons = [];
        for (let i = 1; i <= homework.totalProblemsCount; i++)
            problemButtons.push(
                <li className="nav-item">
                    <a className="nav-link btn btn-outline-primary"
                       href={"/student/" + params.studentId + "/course/" + params.courseId + "/homework/" + params.homeworkId + "/problems/" + i}>Задача {i}</a>
                </li>);

        let problemInput;
        if (problemInfo.problem.type === "short_answer_problem") {
            problemInput = <div>
                <span className="font-weight-bold">Задание с кратким ответом </span>
                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top"
                                 title="Ответом является строка или конечная десятичная дробь (через запятую)."/>

                <form autoComplete="off" className="p-2" onSubmit={this.submitProblem}>
                    <div>
                        <label htmlFor="answer" className="pr-2">Ответ</label>
                        <input type="text" id="answer" autoComplete="off" disabled={problemInfo.usedAttempts >= problemInfo.maxAttempts}/>
                        <button type="submit" className="btn btn-primary ml-2"
                                disabled={problemInfo.usedAttempts >= problemInfo.maxAttempts}>Отправить</button>
                    </div>
                </form>
            </div>;
        }
        else if (problemInfo.problem.type === "choice_problem") {
            problemInput = <div>
                <span className="font-weight-bold">Задание с вариантами ответа </span>
                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top"
                                 title="Ответом является один ответ из списка."/>
                <form autoComplete="off" className="p-2" onSubmit={this.submitProblem}>
                    {problemInfo.problem.answers.map((answer, index) => (<div>
                        <input type="radio" id={"answer" + index} name={"selectedAnswer"} className="mr-2"
                               disabled={problemInfo.usedAttempts >= problemInfo.maxAttempts}/>
                        <label htmlFor={"answer" + index}>{answer}</label>
                    </div>))}
                    <button type="submit" className="btn btn-primary ml-2"
                            disabled={problemInfo.usedAttempts >= problemInfo.maxAttempts}>Отправить</button>
                </form>
            </div>;
        }

        return <div className="row">
            <div className="col-3">
                <ul className="nav flex-column">
                    {problemButtons}
                </ul>
            </div>
            <div className="col">
                <h4 className="font-weight-bold pb-2">Задача {problemInfo.number}</h4>
                <p>{problemInfo.problem.formulation}</p>

                {problemInput}

                <div className="small-font font-weight-bold">Всего попыток: {problemInfo.maxAttempts}</div>
                <div className="small-font font-weight-bold">Осталось попыток: {problemInfo.maxAttempts - problemInfo.usedAttempts}</div>
            </div>
        </div>
    }
};

export default HomeworkProblems;