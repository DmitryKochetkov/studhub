import React, {Component} from 'react';
import App from "./App";
import Header from "./Header";
import ErrorPage from "./ErrorPage";
import PaginationPanel from "./PaginationPanel";

class AdminUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            usersPage: null,
            error: null
        };
    }

    async componentDidMount() {
        const response = await fetch('/api/admin/users' + this.props.location.search);
        if (response.ok) {
            const json = await response.json();
            this.setState({usersPage: json});
        }
        else {
            this.setState({error: response})
        }
    }

    rolesUI = App.rolesUI;

    render() {
        document.title = 'StudHub: Пользователи';
        const {usersPage, error} = this.state;
        if (error) {
            console.log(error);
            return <ErrorPage code={error.status} description={error.statusText}/>
        }

        if (usersPage === null)
            return (<div>error</div>);

        let userTable = <div>Нет зарегистрированных пользователей.</div>
        if (usersPage.content.length !== 0) {
            userTable = usersPage.content.map((user) =>
                <tr key={user.id}>
                    <td>{user.id}</td>
                    <td><a href={'/user/' + user.id}>{user.username}</a></td>
                    <td>{user.firstName + ' ' + user.lastName}</td>
                    <td>{user.roles.map((role) => this.rolesUI[role.name]).join(', ')}</td>
                    <td>{user.status}</td>
                </tr>
            );
        }

        return (
            <div>
                <Header/>
                <div className='container'>
                    <h1 className='font-weight-bold pb-3'>Пользователи</h1>

                    <div className='users_table'>
                        <table className='table'>
                            <thead className='thead-light'>
                            <tr>
                                <th>ID</th>
                                <th>Логин</th>
                                <th>Имя</th>
                                <th>Тип аккаунта</th>
                                <th>Статус</th>
                            </tr>
                            </thead>
                            <tbody>
                            {userTable}
                            </tbody>
                        </table>

                        <PaginationPanel currentPage={usersPage.number} totalPages={usersPage.totalPages} path={this.props.location.pathname}/>
                    </div>

                    <form action='/admin/signup' method='get'>
                        <button type='submit' className='btn btn-primary'>Создать пользователя</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default AdminUsers;