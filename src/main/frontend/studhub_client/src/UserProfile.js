import React, {Component} from "react";
import profilePic from "./profilePic.jpg";
import "./App.css";
import App from "./App";
import Moment from "react-moment";

class UserProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            followers: null,
            followings: null,
            courses: null
        };
    }

    componentDidMount() {
        Promise.all([
            fetch("/api/user/2"),
            fetch("/api/user/2/followers"),
            fetch("/api/user/2/following")
        ]).then((result) => Promise.all(result.map(v => v.json())))
            .then((result) => {
                this.setState({
                    user: result[0],
                    followers: result[1],
                    followings: result[2]
                });
            });
    }

    rolesUI = App.rolesUI;
    courseStatusUI = App.courseStatusUI;

    render() {
        const {user, followers, followings} = this.state;
        if (user === null)
            return (<div>error</div>);

        document.title = "StudHub: Пользователь " + user.username;

        const followingsSpan = followings["content"].map((following) =>
            <span><a href={"/api/user/" + following.id}>{following.username + " [" + following.firstName + " " + following.lastName + "]"}</a>, </span>
        );

        const followersSpan = followers["content"].map((follower) =>
            <span><a href={"/api/user/" + follower.id}>{follower.username + " [" + follower.firstName + " " + follower.lastName + "]"}</a>, </span>
        );

        const coursesTableBody = user.courses.map((course) =>
            <tr key={course.id}>
                <td>{course.id}</td>
                <td>{course.subject.title}</td>
                <td>{this.courseStatusUI[course.status]}</td>
                <td><Moment format="DD.MM.YYYY">{course.created}</Moment></td>
                <td><a href={"/student/" + 2 + "/course/" + course.id}>Перейти</a></td>
            </tr>
        );

        return (
            <div className="container">
                <h1 className="font-weight-bold pb-3">Личный кабинет</h1>
                <div className="row align-items-center">
                    <div className="col-md-2">
                        <img src={profilePic} className="img img-rounded img-fluid"/>
                    </div>
                    <div className="col-md-10">
                        <div className="font-weight-bold profile-username">{user.username}</div>
                        <div className="profile-name">{user.firstName + " " + user.lastName}</div>
                        <div>
                            <span className="font-weight-bold">Роль: </span>
                            <span>{user.roles.map((role) => this.rolesUI[role.name]).join(", ")}</span>
                        </div>
                    </div>
                </div>

                <div>
                    <a href={"/user/" + user.id + "/settings"} className="btn btn-link">Изменить учетные данные</a>
                    <a href={"/user/" + user.id + "/settings"} className="btn btn-link">Зачислить на курс</a>
                </div>

                <div>
                    <span className="font-weight-bold">Подписан(а) на: </span>
                    {followingsSpan}
                </div>

                <div>
                    <span className="font-weight-bold">Подписчики: </span>
                    {followersSpan}
                </div>

                <h2 className="font-weight-bold">Курсы</h2>
                <table className="table">
                    <thead className="thead-light">
                        <tr>
                            <th>ID</th>
                            <th>Предмет</th>
                            <th>Статус</th>
                            <th>Дата старта</th>
                            <th/>
                        </tr>
                    </thead>
                    <tbody>{coursesTableBody}</tbody>
                </table>
            </div>
        );
    }
};

export default UserProfile;