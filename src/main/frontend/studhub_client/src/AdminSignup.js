import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "./Header";

class AdminSignup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            success: false,
            error: false
        }
    }

    async onChange(e) {
        var response = await fetch("http://localhost:8081/api/user?username=" + document.getElementById("username").value);
        if (response.ok) {
            e.target.setCustomValidity("Такой пользователь уже зарегистрирован");
        }
        else {
            e.target.setCustomValidity("");
        }
        e.target.reportValidity();
    }

    async postAction() {
        const requestOptions = {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                firstName: document.getElementById("firstName").value,
                lastName: document.getElementById("lastName").value,
                username: document.getElementById("username").value,
                password: document.getElementById("password").value,
                role: document.getElementById("role").value
            })
        };
        fetch("/api/admin/signup", requestOptions)
            .then((response) => {
                if (response.status === 201) {
                    this.setState({success: true});
                }
                else {
                    this.setState({success: false});
                }
            })
            .then(response => response.json());
    }

    render() {
        document.title = "StudHub: Создать пользователя";
        return (
            <div>
                <Header/>
                <div className="container">
                    <h1 className="font-weight-bold">Новый пользователь</h1>

                    <form onSubmit={this.postAction} autoComplete="off" className="was-validated">
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="firstName">Имя</label>
                                    <input type="text" id="firstName" placeholder="Имя"
                                           className="form-control" required pattern="[A-ZА-ЯЁ][A-Za-zа-яё-]+"/>
                                </div>
                            </div>
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="lastName">Фамилия</label>
                                    <input type="text" id="lastName" placeholder="Фамилия"
                                           className="form-control" required pattern="[A-ZА-ЯЁ][A-Za-zа-яё-]+"/>
                                </div>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="username">Логин</label>
                            <input type="text" id="username" placeholder="Логин" className="form-control" required pattern="[A-Za-z][A-Za-z0-9_]+" minLength="3" maxLength="30"/>
                                <span className="small-font">Допустима длина логина от 3 до 30 символов. Разрешается использование латиницы, цифр и нижнего подчеркивания.</span>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Пароль</label>
                            <input type="password" id="password" placeholder="Пароль" className="form-control" required minLength="6" maxLength="30"/>
                                <span className="small-font">Допустима длина пароля от 6 до 30 символов.</span>
                        </div>

                        <div className="form-group">
                            <label htmlFor="role">Роль:</label>
                            <select name="role" id="role" className="form-control">
                                <option value="USER">Пользователь</option>
                                <option value="STUDENT">Ученик</option>
                            </select>
                        </div>

                        <button type="submit" className="btn btn-success">Зарегистрировать</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default AdminSignup;