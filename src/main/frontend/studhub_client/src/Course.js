import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import {faQuestionCircle} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {Bar, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis} from 'recharts';
import Header from './Header';
import Moment from 'react-moment';
import moment from 'moment';
import BarChart from 'recharts/lib/chart/BarChart';
import SpecificationStatisticsTable from './SpecificationStatisticsTable';
import ErrorPage from './ErrorPage';

class Course extends Component {
    constructor(props) {
        super(props);
        this.state = {
            course: null,
            homeworkStatistics: null,
            specification: null,
            specificationStatistics: null,
            hasError: false
        };
        this.onPeriodChange = this.onPeriodChange.bind(this);
    }

    componentDidMount() {
        const {courseId} = this.props.match.params;
        const base = '/api/course/' + courseId;

        fetch(base)
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: result});}
            ).then(() =>

        fetch(base + '/homework-statistics?businessPeriod=MONTH')
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: this.state.course, homeworkStatistics: result});}
            )).then(() =>

        fetch('/api/specification/' + this.state.course.activeSpecificationId)
            .then((res) => res.json())
            .then(
                (result) => {
                    this.setState({
                        course: this.state.course,
                        homeworkStatistics: this.state.homeworkStatistics,
                        specification: result
                    });
                }
            ).then(() =>

        fetch(base + '/specification-statistics/')
            .then((res) => res.json())
            .then(
                (result) => {
                    this.setState({
                        course: this.state.course,
                        homeworkStatistics: this.state.homeworkStatistics,
                        specification: this.state.specification,
                        specificationStatistics: result,
                        specificationStatisticsLoaded: true
                    })
                },
                (error) => {
                    this.setState({
                        course: this.state.course,
                        homeworkStatistics: this.state.homeworkStatistics,
                        specification: this.state.specification,
                        specificationStatistics: null,
                        hasError: true
                    })
                }
            )
            )).then(() => {

        fetch('/api/user/' + this.state.course.studentId)
           .then((res) => res.json())
           .then(
               (result) => {this.setState({user: result})}
           )
        });
    }

    courseStatusUI = App.courseStatusUI;

    async onPeriodChange(e) {
        const {courseId} = this.props.match.params;
        fetch('/api/course/' + courseId + '/homework-statistics?businessPeriod=' + document.getElementById('homework_stat_period').value)
            .then((res) => res.json())
            .then(
                (result) => {this.setState({course: this.state.course, homeworkStatistics: result})}
            );
    }

    render() {
        if (this.state.hasError) {
            return <ErrorPage code={'500'} description={'Internal Server Error'}/>;
        }

        const {course, homeworkStatistics, specification, specificationStatistics} = this.state;

        try {
            document.title = 'StudHub: Курс #' + course.id;
            const comingLessons = course.comingLessons.map((lesson) => (<tr key={lesson.id}>
                <td><Moment format='DD.MM.YYYY HH:mm'>{lesson.startDateTime}</Moment></td>
                <td>{lesson.topic}</td>
            </tr>));

            const comingHomework = course.comingHomework.map((homework) => (<tr key={homework.id}>
                <td><Moment format='DD.MM.YYYY HH:mm'>{homework.deadline}</Moment></td>
                <td>{homework.description}</td>
                <td>{homework.solvedProblemsCount}/{homework.totalProblemsCount}</td>
            </tr>));

            const toPercent = (decimal, fixed = 0) => `${(decimal * 100).toFixed(fixed)}%`;
            const chart_avg_homework = <ResponsiveContainer width='100%' aspect={4.5/2.0}>
                <BarChart data={homeworkStatistics} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
                    <defs>
                        <linearGradient id='colorUv' x1='0' y1='0' x2='0' y2='1'>
                            <stop offset='5%' stopColor='#8884d8' stopOpacity={0.8}/>
                            <stop offset='95%' stopColor='#8884d8' stopOpacity={0}/>
                        </linearGradient>
                    </defs>
                    <XAxis dataKey='date' interval={0} tick={{fontSize: 13}} tickFormatter={(periodDate) => moment(periodDate).format('DD.MM.YYYY')}/>
                    <YAxis tick={{fontSize: 13}} tickFormatter={toPercent}/>
                    <CartesianGrid strokeDasharray='3 3'/>
                    <Tooltip separator=': '/>
                    <Bar type='monotone' dataKey='percentage' stroke='#8884d8' fillOpacity={1} fill='url(#colorUv)'/>
                </BarChart>
            </ResponsiveContainer>;

            let progressTable = 'Загрузка...';
            if (this.state.specificationStatisticsLoaded) {
                progressTable = <SpecificationStatisticsTable specification={specification} specificationStatistics={specificationStatistics}/>
            }

            return (
                <div>
                    <Header/>
                    <div className='container'>
                        <h2 className='font-weight-bold pb-3'>Курс #{course.id}: {course.subject.title}</h2>
                        <div>Ученик: <a href={'/user/' + this.state.user.id}>{this.state.user.username}</a></div>
                        <div className='font-weight-bold'>Статус: {this.courseStatusUI[course.status]}</div>

                        <div className='row pt-3'>
                            <div className='col'>
                                <div className='font-weight-bold pb-3'>Ближайшие дедлайны</div>
                                <table className='table small-font'>
                                    <thead className='thead-light'>
                                    <tr>
                                        <th>Срок сдачи</th>
                                        <th>Разделы</th>
                                        <th>Заданий</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {comingHomework}
                                    </tbody>
                                </table>
                            </div>

                            <div className='col'>
                                <div className='font-weight-bold pb-3'>Ближайшие занятия</div>
                                <table className='table small-font'>
                                    <thead className='thead-light'>
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

                        <div className='row'>
                            <div className={'text-center col'}>
                                <a className='small-font'
                                   href={'/course/' + this.props.match.params.courseId + '/homework'}>Все
                                    домашние задания</a>
                            </div>
                            <div className={'text-center col'}>
                                <a className='small-font'
                                   href={'/course/' + this.props.match.params.courseId + '/lessons'}>Все
                                    занятия</a>
                            </div>
                        </div>

                        <div className='row pt-4'>
                            <div className='col'>
                                <div className='pb-3'>Динамика выполнения домашних работ:</div>
                                <div>
                                    {chart_avg_homework}
                                </div>
                                <div className='form-group'>
                                    <label htmlFor='homework_stat_period'>Промежуток:</label>
                                    <select name='homework_stat_period' id='homework_stat_period' className='form-control' onChange={this.onPeriodChange}>
                                        <option value='MONTH'>Месяц</option>
                                        <option value='THREE_MONTH'>3 месяца</option>
                                        <option value='SIX_MONTH'>Полгода</option>
                                        <option value='YEAR'>Год</option>
                                    </select>
                                </div>
                            </div>
                            <div className='col'>
                                <span>Средний балл ЕГЭ: </span>
                                <span>нет данных </span>
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle='tooltip' data-placement='top'
                                                 title='Тестирования или домашние работы по полному варианту ЕГЭ еще не проводились.'/>
                            </div>
                        </div>

                        <div>
                            <div className='font-weight-bold pb-3'>
                                <span>Прогресс по заданиям экзамена </span>
                                <FontAwesomeIcon icon={faQuestionCircle} data-toggle='tooltip' data-placement='top'
                                                 title='Для задания с соотвествующим номером при наведении выводится процент правильно решенных заданий в домашних работах.'/>
                            </div>

                            {progressTable}
                        </div>

                        <div>
                            <a className='small-font'
                               href={'/about-statistics'}>Подробнее об оценивании и статистике</a>
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