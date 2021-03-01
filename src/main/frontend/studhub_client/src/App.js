import './App.css';
import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Mainpage from './Mainpage';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './Login';
import ErrorPage from './ErrorPage';
import AdminUsers from './AdminUsers';
import UserProfile from './UserProfile';
import AboutCourses from './AboutCourses';
import AdminLessons from './AdminLessons';
import Course from './Course';
import AdminCreateLesson from './AdminCreateLesson';
import VerdictsInfo from './VerdictsInfo';
import CourseHomework from './CourseHomework';
import Homework from './Homework';
import AboutStatistics from './AboutStatistics';
import PageWrapper from "./PageWrapper";

class App extends Component {
    constructor(props) {
        super(props);
        document.title = 'StudHub';
    }

    static rolesUI = {ROLE_USER: 'Пользователь', ROLE_STUDENT: 'Ученик', ROLE_ADMIN: 'Администратор'};
    static courseStatusUI = {
        ACTIVE: <span>Активен</span>,
        COMPLETED: <span>Пройден</span>,
        CANCELED: <span>Отменен</span>
    };
    static lessonStatusUI = {
        SCHEDULED: <span>Назначен</span>,
        FINISHED: <span>Завершен</span>,
        CANCELLED: <span>Отменен</span>,
        UNKNOWN: <span>Неизвестен</span>,
    }

    render() {
        return (
            <BrowserRouter>
                <Switch>
                    <Route exact path='/' render={props => <PageWrapper{...props} child={Mainpage}/>}/>

                    <Route exact path='/login'>
                        <Login/>
                    </Route>

                    <Route exact path={'/user/:userId'} render={props => <PageWrapper{...props} child={UserProfile}/>}/>

                    <Route exact path='/admin/users' render={props => <PageWrapper{...props} child={AdminUsers}/>}/>

                    <Route exact path='/admin/lessons' render={props => <PageWrapper{...props} child={AdminLessons}/>}/>

                    <Route exact path='/verdicts' render={props => <PageWrapper{...props} child={VerdictsInfo}/>}/>

                    <Route exact path='/course/:courseId' render={props => <PageWrapper{...props} child={Course}/>}/>
                    <Route exact path='/admin/lessons/new' render={props => <PageWrapper{...props} child={AdminCreateLesson}/>}/>
                    <Route exact path='/admin/signup' render={props => <PageWrapper{...props} child={AdminLessons}/>}/>
                    <Route exact path='/about-courses' render={props => <PageWrapper{...props} child={AboutCourses}/>}/>

                    <Route exact path='/course/:courseId/homework/' render={props => <PageWrapper{...props} child={CourseHomework}/>}/>

                    <Route exact path='/course/:courseId/homework/:homeworkId'
                           render = {props => <PageWrapper{...props} tab={'description'} child={Homework}/> }/>

                    <Route exact path='/course/:courseId/homework/:homeworkId/problems/:problemNumber'
                           render = {props => <PageWrapper{...props} tab={'problems'} child={Homework}/> }/>

                    <Route exact path='/course/:courseId/homework/:homeworkId/submissions'
                           render = {props => <PageWrapper{...props} tab={'submissions'} child={Homework}/> }/>

                    <Route exact path='/about-statistics' render={props => <PageWrapper{...props} child={AboutStatistics}/>}/>

                    <Route>
                        <ErrorPage code={404} description={'Страница не найдена.'}/>
                    </Route>
                </Switch>
            </BrowserRouter>
        );
    }
}

export default App;
