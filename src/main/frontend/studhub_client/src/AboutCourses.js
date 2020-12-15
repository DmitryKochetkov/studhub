import React, {Component} from "react";
import "bootstrap/dist/css/bootstrap.min.css";

class AboutCourses extends Component {
    constructor(props) {
        super(props);
        this.state = {
            refCourses: null
        };
    }

    componentDidMount() {
        // fetch("/api/aoub")
    }

    render() {
        return (
            <div className="container">
                <h1 className="font-weight-bold pb-3">Курсы</h1>
                <p>Я преподаю следующие курсы:</p>
                <ul>
                    <li>Программирование на C++</li>
                    <li>Программирование на Pascal</li>
                    <li>Программирование на Java</li>
                    <li>Олимпиадное программирование</li>
                    <li>Информатика (ЕГЭ)</li>
                    <li>Математика (ЕГЭ)</li>
                    <li>Информатика (ОГЭ)</li>
                    <li>Математика (ОГЭ)</li>
                    <li>Математический анализ</li>
                    <li>Линейная алгебра</li>
                </ul>
            </div>
        );
    }
}

export default AboutCourses;