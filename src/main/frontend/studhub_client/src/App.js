import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Mainpage from "./Mainpage";
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from "./Login";
import ErrorPage from "./ErrorPage";
import Header from "./Header";
import AdminUsers from "./AdminUsers"
import UserProfile from "./UserProfile";
import AboutCourses from "./AboutCourses";
import AdminLessons from "./AdminLessons";
import Course from "./Course";
import AdminCreateLesson from "./AdminCreateLesson";

class App extends Component {
    constructor(props) {
        super(props);
        document.title = "StudHub";
    }

    static rolesUI = {ROLE_USER: "Пользователь", ROLE_STUDENT: "Ученик", ROLE_ADMIN: "Администратор"};
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
                    <Route exact path="/">
                        <Header/>
                        <Mainpage/>
                    </Route>
                    <Route exact path="/login">
                        <Login/>
                    </Route>
                    <Route exact path={"/user/:userId"}>
                        <Header/>
                        <UserProfile/>
                    </Route>
                    <Route exact path="/admin/users">
                        <Header/>
                        <AdminUsers/>
                    </Route>
                    <Route exact path="/admin/lessons">
                        <Header/>
                        <AdminLessons/>
                    </Route>
                    <Route exact path="/student/:studentId/course/:courseId" component={Course}/>
                    <Route exact path="/admin/lessons/new" component={AdminCreateLesson}/>
                    <Route exact path="/about-courses">
                        <Header/>
                        <AboutCourses/>
                    </Route>
                    <Route>
                        <ErrorPage code={404} description={"Страница не найдена."}/>
                    </Route>
                </Switch>
            </BrowserRouter>
        );
    }
}

export default App;
