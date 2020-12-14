import React, {Component} from "react";

class AdminLessons extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lessonsPage: null
        };
    }

    componentDidMount() {
        fetch('/api/admin/lessons?page=1')
            .then(res => res.json())
            .then(
                (result) => {this.setState({lessonsPage: result})}
                );
    }

    render() {
        document.title = "StudHub: Уроки";
        const lessonsPage = this.state.lessonsPage;
        // if (lessonsPage === null)
        //     return (<div>error</div>);

        try {
            const lessonsTable = lessonsPage.content.map((lesson) =>
                <tr key={lesson.id}>
                    <td>{lesson.id}</td>
                    <td>{lesson.startDateTime}</td>
                    <td>{lesson.firstName + ' ' + lesson.lastName}</td>
                    <td>{lesson.topic}</td>
                    <td>{lesson.status}</td>
                    <td><a href={'/lesson/' + 1}>Перейти</a></td>
                </tr>
            );

            const pagination = [];
            const x = Math.max(parseInt(lessonsPage["number"]) - 5, 1);
            for (let i = x; i < x + Math.min(10, lessonsPage.totalPages); i++)
                pagination.push(<li className="page-item"><a className="page-link" href={"/admin/users?page=" + i}>{i}</a></li>);


            return (
                <div className="container">
                    <h1 className="font-weight-bold pb-3">Уроки</h1>
                    <div>
                        <table className="table">
                            <thead className="thead-light">
                            <tr>
                                <th>ID</th>
                                <th>Дата</th>
                                <th>Ученик</th>
                                <th>Тема</th>
                                <th>Статус</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {lessonsTable}
                            </tbody>
                        </table>

                        <ul className="pagination">
                            {pagination}
                        </ul>
                    </div>

                    <form action="/admin/lessons/new" method="get">
                        <button type="submit" className="btn btn-primary">Создать пользователя</button>
                    </form>
                </div>
            );
        }
        catch {
            return <div>error</div>;
        }
    }
}

export default AdminLessons;