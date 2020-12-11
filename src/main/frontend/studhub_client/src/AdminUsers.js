import React, {Component} from "react";

class AdminUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users_page: null
        };
    }

    componentDidMount() {
        document.title = "StudHub: Пользователи";
        fetch('/api/admin/users?page=1')
            .then(res => res.json())
            .then(
                (result) => {this.setState({users_page: result})}
                );
    }

    rolesUI = {ROLE_USER: "Пользователь", ROLE_STUDENT: "Ученик", ROLE_ADMIN: "Администратор"}

    render() {
        const users_page = this.state.users_page;
        if (users_page === null)
            return (<div>error</div>);

        const userTable = users_page.content.map((user) =>
            <tr key={user.id}>
                <td>{user.id}</td>
                <td><a href={"/user/" + user.username}>{user.username}</a></td>
                <td>{user.firstName + ' ' + user.lastName}</td>
                <td>{user.roles.map((role) => this.rolesUI[role.name]).join(', ')}</td>
                <td>{user.status}</td>
            </tr>
        );

        const pagination = [];
        const x = Math.max(parseInt(users_page["number"]) - 5, 1);
        for (let i = x; i < x+10; i++)
            pagination.push(<li className="page-item"><a className="page-link" href={"/admin/users?page=" + i}>{i}</a></li>);

        return (
            <div className="container">
                <h1 className="font-weight-bold pb-3">Пользователи</h1>
                {/*<div className="table_empty_info" th:if="${!page.isHasPrevious() && page.getContent().isEmpty()}">*/}
                {/*    Нет зарегистрированных пользователей.*/}
                {/*</div>*/}
                <div className="users_table">
                    <table className="table">
                        <thead className="thead-light">
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

                    <ul className="pagination">
                        {pagination}
                    </ul>
                </div>

                <form action="/admin/signup" method="get">
                    <button type="submit" className="btn btn-primary">Создать пользователя</button>
                </form>
            </div>
        );
    }
}

export default AdminUsers;