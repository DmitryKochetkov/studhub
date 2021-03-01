import React, {Component} from 'react';
import './App.css';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faQuestionCircle} from '@fortawesome/free-solid-svg-icons';

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
        fetch('/api/course/' + params.courseId + '/homework/' + params.homeworkId + '/problems/' + params.problemNumber)
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

        const problem = this.state.problemInfo.problem;
        let answer;
        if (problem.type === 'choice_problem') {
            for (let i = 0; i < problem.answers.length; i++) {
                if (document.getElementById('radioButtonAnswer' + i).checked)
                    answer = document.getElementById('answer' + i).textContent;
            }
        }
        else { answer = document.getElementById('answer').value };

        if (this.state.problemInfo.usedAttempts < this.state.problemInfo.maxAttempts) {
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    answer: answer
                })
            };
            fetch('/api/course/' + params.courseId + '/homework/' + params.homeworkId + '/problems/' + params.problemNumber + '/submit', requestOptions)
                .then((response) => {
                    if (response.ok) {
                        this.setState({success: true});
                    } else {
                        this.setState({success: false});
                    }
                    console.log(response);
                })
                .then(response => response.json());
        }
    }

    render() {
        const params = this.props.params;
        const {homework, problemInfo} = this.state;
        if (!this.state.problemInfo)
            return (<div className="alert alert-danger">Ошибка загрузки.</div>);

        const problemSubmitDisabled = (problemInfo.usedAttempts >= problemInfo.maxAttempts) || (new Date(homework.deadline) <= new Date());

        const problemButtons = [];
        for (let i = 1; i <= homework.totalProblemsCount; i++)
            problemButtons.push(
                <li className='nav-item'>
                    <a className='nav-link btn btn-outline-primary'
                       href={'/course/' + params.courseId + '/homework/' + params.homeworkId + '/problems/' + i}>Задача {i}</a>
                </li>);

        let problemInput;
        if (problemInfo.problem.type === 'short_answer_problem') {
            problemInput = <div>
                <span className='font-weight-bold'>Задание с кратким ответом </span>
                <FontAwesomeIcon icon={faQuestionCircle} data-toggle='tooltip' data-placement='top'
                                 title='Ответом является строка или конечная десятичная дробь (через запятую).'/>

                <form autoComplete='off' className='p-2' onSubmit={this.submitProblem}>
                    <div>
                        <label htmlFor='answer' className='pr-2'>Ответ</label>
                        <input type='text' id='answer' autoComplete='off' disabled={problemSubmitDisabled}/>
                        <button type='submit' className='btn btn-primary ml-2'
                                disabled={problemSubmitDisabled}>Отправить</button>
                    </div>
                </form>
            </div>;
        }
        else if (problemInfo.problem.type === 'choice_problem') {
            problemInput = <div>
                <span className='font-weight-bold'>Задание с вариантами ответа </span>
                <FontAwesomeIcon icon={faQuestionCircle} data-toggle='tooltip' data-placement='top'
                                 title='Ответом является один ответ из списка.'/>
                <form autoComplete='off' className='p-2' onSubmit={this.submitProblem}>
                    {problemInfo.problem.answers.map((answer, index) => (<div>
                        <input type='radio' id={'radioButtonAnswer' + index} name={'answer'} className='mr-2'
                               disabled={problemSubmitDisabled}/>
                        <label htmlFor={'radioButtonAnswer' + index} id={'answer' + index}>{answer}</label>
                    </div>))}
                    <button type='submit' className='btn btn-primary ml-2'
                            disabled={problemSubmitDisabled}>Отправить</button>
                </form>
            </div>;
        }

        return <div className='row'>
            <div className='col-3'>
                <ul className='nav flex-column'>
                    {problemButtons}
                </ul>
            </div>
            <div className='col'>
                <h4 className='font-weight-bold pb-2'>Задача {problemInfo.number}</h4>
                <p>{problemInfo.problem.formulation}</p>

                {problemInput}

                <div className='small-font font-weight-bold'>Всего попыток: {problemInfo.maxAttempts}</div>
                <div className='small-font font-weight-bold' style={problemInfo.maxAttempts - problemInfo.usedAttempts > 0 ? {color: "black"} : {color: "red"}}>
                    Осталось попыток: {problemInfo.maxAttempts - problemInfo.usedAttempts}
                </div>
            </div>
        </div>
    }
};

export default HomeworkProblems;