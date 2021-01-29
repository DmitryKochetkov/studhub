import React, {Component} from 'react';
import profilePic from './profilePic.jpg';
import './App.css';
import App from './App';
import Header from "./Header";
import CoursesTable from "./CoursesTable";

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
        const {userId} = this.props.match.params;
        Promise.all([
            fetch('/api/user/' + userId),
            fetch('/api/user/' + userId + '/followers'),
            fetch('/api/user/' + userId + '/following')
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

        document.title = 'StudHub: Пользователь ' + user.username;

        const followingsSpan = followings['content'].map((following) =>
            <span><a href={'/user/' + following.id}>{following.username + ' [' + following.firstName + ' ' + following.lastName + ']'}</a>, </span>
        );

        const followersSpan = followers['content'].map((follower) =>
            <span><a href={'/user/' + follower.id}>{follower.username + ' [' + follower.firstName + ' ' + follower.lastName + ']'}</a>, </span>
        );

        return (
            <div>
                <Header/>
                <div className='container'>
                    <h1 className='font-weight-bold pb-3'>Личный кабинет</h1>
                    <div className='row align-items-center'>
                        <div className='col-md-2'>
                            <img src={profilePic} className='img img-rounded img-fluid'/>
                        </div>
                        <div className='col-md-10'>
                            <div className='font-weight-bold profile-username'>{user.username}</div>
                            <div className='profile-name'>{user.firstName + ' ' + user.lastName}</div>
                            <div>
                                <span className='font-weight-bold'>Роль: </span>
                                <span>{user.roles.map((role) => this.rolesUI[role.name]).join(', ')}</span>
                            </div>
                        </div>
                    </div>

                    <div>
                        <a href={'/user/' + user.id + '/settings'} className='btn btn-link'>Изменить учетные данные</a>
                        <a href={'/user/' + user.id + '/settings'} className='btn btn-link'>Зачислить на курс</a>
                    </div>

                    <div>
                        <span className='font-weight-bold'>Подписан(а) на: </span>
                        {followingsSpan}
                    </div>

                    <div>
                        <span className='font-weight-bold'>Подписчики: </span>
                        {followersSpan}
                    </div>

                    {user.roles.map(role => {return role.name}).includes('ROLE_STUDENT') ? <CoursesTable studentId={user.id}/> : null}
                </div>
            </div>
        );
    }
};

export default UserProfile;