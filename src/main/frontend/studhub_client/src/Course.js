import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import App from "./App";
import {faQuestionCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Area, AreaChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis} from 'recharts';

class Course extends Component {
    constructor(props) {
        super(props);
        this.state = {
            course: null
        }
    }

    componentDidMount() {
        fetch('/api/student/2/course/1')
            .then(res => res.json())
            .then(
                (result) => {this.setState({course: result})}
            );
    }

    courseStatusUI = App.courseStatusUI;

    render() {
        const {course} = this.state;

        try {
            document.title = 'StudHub: Курс ' + course.id;
            const comingLessons = course.comingLessons.map((lesson) => (<tr>
                <td>{lesson.startDateTime}</td>
                <td>{lesson.topic}</td>
            </tr>));

            const progressTable = <table></table>;

            const data = [
                {
                    "name": "01.10.2020",
                    "uv": 4000,
                    "amt": 2400
                },
                {
                    "name": "11.10.2020",
                    "uv": 3000,
                    "amt": 2210
                },
                {
                    "name": "12.10.2020",
                    "uv": 2000,
                    "amt": 2290
                },
                {
                    "name": "13.10.2020",
                    "uv": 2780,
                    "amt": 2000
                },
                {
                    "name": "14.10.2020",
                    "uv": 1890,
                    "amt": 2181
                },
                {
                    "name": "15.10.2020",
                    "uv": 2390,
                    "amt": 2500
                },
                {
                    "name": "16.10.2020",
                    "uv": 3490,
                    "amt": 2100
                }
            ];

            const chart_avg_homework = <ResponsiveContainer width='100%' aspect={4.5/2.0}>
                <AreaChart data={data} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                    <defs>
                        <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
                            <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8}/>
                            <stop offset="95%" stopColor="#8884d8" stopOpacity={0}/>
                        </linearGradient>
                    </defs>
                    <XAxis dataKey="name" interval={0} tick={{fontSize: 13}}/>
                    <YAxis tick={{fontSize: 13}}/>
                    <CartesianGrid strokeDasharray="3 3" />
                    <Tooltip />
                    <Area type="monotone" dataKey="uv" stroke="#8884d8" fillOpacity={1} fill="url(#colorUv)" />
                </AreaChart>
            </ResponsiveContainer>;

            return (
                <div className="container">
                    <h2 className="font-weight-bold pb-3">Курс #{course.id}: {course.title}</h2>
                    <div>Ученик: <a href={'/user/' + 2}>login</a></div>
                    <div class="font-weight-bold">Статус: {this.courseStatusUI[course.status]}</div>

                    <div className="row pt-3">
                        <div className="col">
                            <div className="font-weight-bold pb-3">Дедлайны / домашние задания</div>
                            <table className="table small-font">
                                <thead className="thead-light">
                                    <tr>
                                        <th>Дата</th>
                                        <th>Разделы</th>
                                        <th>Заданий</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>

                                    </tr>
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

                        <div className="col">
                            <div className="font-weight-bold pb-3">
                                <span>Прогресс по заданиям </span>
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top" title="Для задания с соотвествующим номером при наведении выводится процент правильно решенных заданий в домашних работах."/>
                            </div>

                            {progressTable}
                        </div>
                    </div>

                    <div className="row">
                        <div className={"text-center col"}>
                            <a className="small-font" href={"/student/" + this.props.match.params.studentId + "/course/" + this.props.match.params.courseId + "/homework"}>Все домашние задания</a>
                        </div>
                        <div className={"text-center col"}>
                            <a className="small-font" href={"/student/" + this.props.match.params.studentId + "/course/" + this.props.match.params.courseId + "/lessons"}>Все занятия</a>
                        </div>
                        <div className={"text-center col"}>
                            <a className="small-font" href={"/student/" + this.props.match.params.studentId + "/course/" + this.props.match.params.courseId + "/lessons"}>Подробнее</a>
                        </div>
                    </div>

                    <div className="row pt-4">
                        <div className="col">
                            <div className="pb-3">Средний процент выполнения домашних работ:</div>
                            <div>
                                {chart_avg_homework}
                            </div>
                            <div className="form-group">
                                <label htmlFor="avg_homework">Промежуток:</label>
                                <select name="avg_homework" id="avg_homework" className="form-control">
                                    <option value="month">Месяц</option>
                                    <option value="month3">3 месяца</option>
                                    <option value="month6">Полгода</option>
                                    <option value="year">Год</option>
                                </select>
                            </div>
                        </div>
                        <div className="col">
                            <span>Средний балл ЕГЭ: </span>
                            <span>нет данных </span>
                            <FontAwesomeIcon icon={faQuestionCircle} data-toggle="tooltip" data-placement="top" title="Тестирования или домашние работы по полному варианту ЕГЭ еще не проводились."/>
                        </div>
                    </div>
                </div>
            );
        }
        catch {
            return <div>Error</div>;
        }
    }
}

export default Course;