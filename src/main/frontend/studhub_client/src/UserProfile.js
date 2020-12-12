import React, {Component} from "react";
import profile_pic from './profile_pic.jpg';
import './App.css';

class UserProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            followers: null,
            followings: null
        };
    }

    componentDidMount() {
        // fetch('/api/user/4')
        //     .then(res => res.json())
        //     .then(
        //         (result) => {
        //             this.state.user = result;
        //         }
        //     );
        //
        // fetch('/api/user/4/following')
        //     .then(res => res.json())
        //     .then(
        //         (result) => {
        //             this.state.followings = result;
        //         }
        //     );
        //
        fetch('/api/user/4/followers')
            .then(res => res.json())
            .then(
                (result) => {
                    this.state.followers = result;
                }
            );

        Promise.all([
            fetch("/api/user/1"),
            fetch("/api/user/2/followers"),
            fetch("/api/user/2/following")
        ]).then(result => Promise.all(result.map(v => v.json())))
            .then(result => {
                this.setState({
                    user: result[0],
                    followers: result[1],
                    followings: result[2]
                });
            });
    }

    rolesUI = {ROLE_USER: "Пользователь", ROLE_STUDENT: "Ученик", ROLE_ADMIN: "Администратор"};

    render() {
        const {user, followers, followings } = this.state;
        if (user === null)
            return (<div>error</div>);

        document.title = "StudHub: Пользователь " + user.username;

        const followingsSpan = followings['content'].map((following) =>
            <span><a href={'/api/user/' + following.id}>{following.username + ' [' + following.firstName + ' ' + following.lastName + ']'}</a>, </span>
        );

        const followersSpan = followers['content'].map((follower) =>
            <span><a href={'/api/user/' + follower.id}>{follower.username + ' [' + follower.firstName + ' ' + follower.lastName + ']'}</a>, </span>
        );


        // const coursesTable = user.course.map((user) =>
        //     <tr key={user.id}>
        //         <td>{user.id}</td>
        //         <td><a href={"/user/" + user.username}>{user.username}</a></td>
        //         <td>{user.firstName + ' ' + user.lastName}</td>
        //         <td>{user.roles.map((role) => this.rolesUI[role.name]).join(', ')}</td>
        //         <td>{user.status}</td>
        //     </tr>
        // );

        //
        // const pagination = [];
        // const x = Math.max(parseInt(users_page["number"]) - 5, 1);
        // for (let i = x; i < x+10; i++)
        //     pagination.push(<li className="page-item"><a className="page-link" href={"/admin/users?page=" + i}>{i}</a></li>);

        return (
            <div className="container">
                <h1 className="font-weight-bold pb-3">Личный кабинет</h1>
                <div className="row align-items-center">
                    <div className="col-md-2">
                        <img src={profile_pic} className="img img-rounded img-fluid"/>
                    </div>
                    <div className="col-md-10">
                        <div className="font-weight-bold profile-username">{user.username}</div>
                        <div className="profile-name">{user.firstName + ' ' + user.lastName}</div>
                        <div>
                            <span className="font-weight-bold">Роль: </span>
                            <span>{user.roles.map((role) => this.rolesUI[role.name]).join(', ')}</span>
                        </div>
                    </div>
                </div>

                <div>
                    <a href={'/user/' + user.id + '/settings'} className="btn btn-link">Изменить учетные данные</a>
                    <a href={'/user/' + user.id + '/settings'} className="btn btn-link">Зачислить на курс</a>
                </div>

                <div>
                    <span className="font-weight-bold">Подписан(а) на: </span>
                    {followingsSpan}
                </div>

                <div>
                    <span className="font-weight-bold">Подписчики: </span>
                    {followersSpan}
                </div>
            </div>
        );
    }
};

export default UserProfile;