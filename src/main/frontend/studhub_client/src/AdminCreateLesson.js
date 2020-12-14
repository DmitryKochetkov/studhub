import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./Header";

class AdminCreateLesson extends Component {
    constructor(props) {
        super(props);
        this.state = {
            success: false,
            error: false
        }
    }

    async onChange(e) {
        let username = document.getElementById('studentUsername').value;
        console.log(username);
        let courses = document.getElementById('course');
        let response = await fetch("/api/student?username=" + username);
        if (response.ok) {
            e.target.setCustomValidity('');
            courses.innerHTML = '';
            let student = await response.json();
            for (let course of student["courses"]) {
                let opt = document.createElement("option");
                opt.value = course["id"];
                opt.innerHTML = course["title"];
                courses.appendChild(opt);
            }
            document.getElementById("course").disabled = false;
        } else {
            e.target.setCustomValidity("Ученик не найден.");
            courses.innerHTML = '';
            courses.disabled = true;
        }
        e.target.reportValidity();
    }

    async postAction() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                topic: document.getElementById('topic').value,
                startDate: document.getElementById('startDate').value,
                startTime: document.getElementById('startTime').value,
                studentUsername: document.getElementById('studentUsername').value,
                courseId: document.getElementById('course').value
            })
        };
        fetch('/api/admin/lessons/new', requestOptions)
            .then((response) => {
                if (response.status === 201) {
                    this.setState({success: true});
                }
                else this.setState({success: false});
            })
            .then(response => response.json());
    }

    render() {
        document.title = 'StudHub: Новый урок';
        return (
            <div>
                <Header/>
                <div className="container">
                    <h1 className="font-weight-bold">Новый урок</h1>

                    <form onSubmit={this.postAction} autoComplete="off" className="was-validated">
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="startDate">Дата</label>
                                    <input type="date" name="startDate" id="startDate" placeholder="Дата" className="form-control" required/>
                                </div>
                            </div>
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="startTime">Время</label>
                                    <input type="time" name="startTime" id="startTime" placeholder="Время" className="form-control" required/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="studentUsername">Ученик</label>
                            <input type="text" id="studentUsername" placeholder="Ученик" className="form-control" required onChange={this.onChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="course">Курс:</label>
                            <select name="text" id="course" className="form-control" disabled required>
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="topic">Тема</label>
                            <input type="text" id="topic" placeholder="Тема" className="form-control" required/>
                        </div>

                        {this.state.success ? <div className="alert alert-success">Success</div>: <div/>}
                        {this.state.error ? <div className="alert alert-danger">Error</div>: <div/>}

                        <button type="submit" className="btn btn-success">Создать урок</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default AdminCreateLesson;