import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import App from "./App";
import {faQuestionCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Bar, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis} from "recharts";
import Header from "./Header";
import Moment from 'react-moment';
import moment from 'moment';
import BarChart from "recharts/lib/chart/BarChart";

class Course extends Component {
    constructor(props) {
        super(props);
        this.state = {
            course: null,
            homework_statistics: null,
            specification: null,
            specification_statistics: null
        };
        this.onPeriodChange = this.onPeriodChange.bind(this);
    }

    componentDidMount() {
        fetch("/api/student/2/course/1")
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: result})}
            );

        fetch("/api/student/2/course/1/homework-statistics?businessPeriod=MONTH")
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: this.state.course, homework_statistics: result})}
            );

        fetch("/api/exam-specification/4")
            .then((res) => res.json())
            .then(
                (result) => {
                    this.setState({
                        course: this.state.course,
                        homework_statistics: this.state.homework_statistics,
                        specification: result
                    })
                }
            );

        fetch("/api/student/2/course/1/exam-specification-statistics")
            .then((res) => res.json())
            .then(
                (result) => {
                    this.setState({
                        course: this.state.course,
                        homework_statistics: this.state.homework_statistics,
                        specification: this.state.specification,
                        specification_statistics: result
                    })
                }
            );
    }

    courseStatusUI = App.courseStatusUI;

    async onPeriodChange(e) {
        fetch("/api/student/2/course/1/homework-statistics?businessPeriod=" + document.getElementById("homework_stat_period").value)
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: this.state.course, homework_statistics: result})}
            );
    }

    render() {
        const {course, homework_statistics, specification, specification_statistics} = this.state;

        try {
            document.title = "StudHub: Курс #" + course.id;
            const comingLessons = course.comingLessons.map((lesson) => (<tr key={lesson.id}>
                <td><Moment format="DD.MM.YYYY HH:mm">{lesson.startDateTime}</Moment></td>
            </tr>));

            const comingHomework = course.comingHomework.map((homework) => (<tr key={homework.id}>
                <td><Moment format="DD.MM.YYYY HH:mm">{homework.deadline}</Moment></td>
                <td>{homework.description}</td>
                <td>{homework.solvedProblemsCount}/{homework.totalProblemsCount}</td>
            </tr>));

            //TODO: вынести в отдельный компонент
            let progressTable = <div>Отсутствует спецификация.</div>
            if (this.state.specification) {
                let problemCodesByIndex = new Map();
                specification.problemCodes.map((data, index) => {problemCodesByIndex[data.id] = {
                    specificationIndex: data.numberInSpecification,
                    description: data.description,
                    totalSubmissions: null,
                    correctSubmissions: null
                }});

                specification_statistics.statistics.forEach(element => {
                    problemCodesByIndex[element.problemCodeId].totalSubmissions = element.totalSubmissions;
                    problemCodesByIndex[element.problemCodeId].correctSubmissions = element.correctSubmissions;
                });

                progressTable = <table className={"specification-statistics-table"}>
                    <tbody>
                        <tr>
                            {specification.problemCodes.map((data, index) => {
                                let problemStatistics = problemCodesByIndex[data.id];
                                let statisticsInfo = data.description;

                                const {correctSubmissions, totalSubmissions} = problemStatistics;
                                const percentage = (correctSubmissions / totalSubmissions) * 100;

                                var r, g, b = 0;
                                if (percentage < 50) {
                                    r = 255;
                                    g = Math.round(5.1 * percentage);
                                }
                                else {
                                    g = 255;
                                    r = Math.round(510 - 5.10 * percentage);
                                }
                                var h = r * 0x10000 + g * 0x100 + b * 0x1;

                                if (problemStatistics.totalSubmissions)
                                    statisticsInfo += "\nУспешных попыток: " + problemStatistics.correctSubmissions + "/" + problemStatistics.totalSubmissions;
                                else statisticsInfo += "\nНет посылок задач этого типа."
                                return (<td style={{backgroundColor: '#' + ('000000' + h.toString(16)).slice(-6)}}><span title={statisticsInfo}>{data.numberInSpecification}</span></td>);
                            })}
                        </tr>
                    </tbody>
                </table>;
            }

            const toPercent = (decimal, fixed = 0) => `${(decimal * 100).toFixed(fixed)}%`;
            const chart_avg_homework = <ResponsiveContainer width="100%" aspect={4.5/2.0}>
                <BarChart data={homework_statistics} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                    <defs>
                        <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                            <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8}/>
                            <stop offset="95%" stopColor="#8884d8" stopOpacity={0}/>
                        </linearGradient>
                    </defs>
                    <XAxis dataKey="date" interval={0} tick={{fontSize: 13}} tickFormatter={(periodDate) => moment(periodDate).format("DD.MM.YYYY")}/>
                    <YAxis tick={{fontSize: 13}} tickFormatter={toPercent}/>
                    <CartesianGrid strokeDasharray="3 3"/>
                    <Tooltip separator=": "/>
                    <Bar type="monotone" dataKey="percentage" stroke="#8884d8" fillOpacity={1} fill="url(#colorUv)"/>
                </BarChart>
            </ResponsiveContainer>;

            return (
                <div>
                    <Header/>
                    <div className="container">
                        <h2 className="font-weight-bold pb-3">Курс #{course.id}: {course.title}</h2>
                        <div>Ученик: <a href={"/user/" + 2}>login</a></div>
                        <div className="font-weight-bold">Статус: {this.courseStatusUI[course.status]}</div>

                        <div className="row pt-3">
                            <div className="col">
                                <div className="font-weight-bold pb-3">Ближайшие дедлайны</div>
                                <table className="table small-font">
                                    <thead className="thead-light">
                                    <tr>
                                        <th>Дата</th>
                                        <th>Разделы</th>
                                        <th>Заданий</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {comingHomework}
                                    </tbody>
                                </table>
                            </div>

                            <div className="col">
                                <div className="font-weight-bold pb-3">Ближайшие занятия</div>
                                <table className="table small-font">
                                    <thead className="thead-light">
                                    <tr>
                                        <th>Дата</th>
                                        <th>Тема</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {comingLessons}
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div className="row">
                            <div className={"text-center col"}>
                                <a className="small-font"
                                   href={"/student/" + this.props.match.params.studentId + "/course/" + this.props.match.params.courseId + "/homework"}>Все
                                    домашние задания</a>
                            </div>
                            <div className={"text-center col"}>
                                <a className="small-font"
                                   href={"/student/" + this.props.match.params.studentId + "/course/" + this.props.match.params.courseId + "/lessons"}>Все
                                    занятия</a>
                            </div>
                        </div>

                        <div className="row pt-4">
                            <div className="col">
                                <div className="pb-3">Динамика выполнения домашних работ:</div>
                                <div>
                                    {chart_avg_homework}
                                </div>
                                <div className="form-group">
                                    <label htmlFor="homework_stat_period">Промежуток:</label>
                                    <select name="homework_stat_period" id="homework_stat_period" className="form-control" onChange={this.onPeriodChange}>
                                        <option value="MONTH">Месяц</option>
                                        <option value="THREE_MONTH">3 месяца</option>
                                        <option value="SIX_MONTH">Полгода</option>
                                        <option value="YEAR">Год</option>
                                    </select>
                                </div>
                            </div>
                            <div className="col">
                                <span>Средний балл ЕГЭ: </span>
                                <span>нет данных </span>
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top"
                                                 title="Тестирования или домашние работы по полному варианту ЕГЭ еще не проводились."/>
                            </div>
                        </div>

                        <div>
                            <div className="font-weight-bold pb-3">
                                <span>Прогресс по заданиям экзамена </span>
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top"
                                                 title="Для задания с соотвествующим номером при наведении выводится процент правильно решенных заданий в домашних работах."/>
                            </div>

                            {progressTable}
                        </div>

                        <div>
                            <a className="small-font"
                               href={"/about-statistics"}>Подробнее об оценивании и статистике</a>
                        </div>
                    </div>
                </div>
            );
        }
        catch (e) {
            console.error(e);
            return <div>Error</div>;
        }
    }
}

export default Course;