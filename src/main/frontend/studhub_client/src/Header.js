import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";

class Header extends Component {
    render() {
        return (
            <div>
                <div className="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
                    <h5 className="my-0 mr-md-auto font-weight-bold"><a className="p-2 text-dark" href="/">StudHub</a></h5>
                    <nav className="my-2 my-md-0 mr-md-3">
                        <a className="p-2 text-dark" href="/user/1">Личный кабинет</a>
                        <a className="p-2 text-dark" href="/admin/users">Пользователи</a>
                        <a className="p-2 text-dark" href="/admin/lessons">Уроки</a>
                        <a className="p-2 text-dark" href="/about-courses">О курсах</a>
                    </nav>
                    <a className="btn btn-outline-primary" href="/#">Выход</a>
                </div>
            </div>
        );
    }
}

export default Header;