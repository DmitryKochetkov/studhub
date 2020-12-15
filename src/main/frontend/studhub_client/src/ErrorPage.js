import "./App.css";
import React, {Component} from "react";

class ErrorPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            code: props.code ? props.code : "999",
            description: props.description ? props.description : "Unknown error"
        }
    }

    render() {
        const {code, description} = this.state;
        return (
            <div className="page-wrap d-flex flex-row align-items-center pt-5">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-12 text-center">
                            <span className="font-weight-bold lead">Ошибка</span>
                            <span className="display-1 d-block" style={{fontWeight: 500}}>{code}</span>
                            <div className="mb-4 lead" style={{fontWeight: 500}}>{description}</div>
                            <a href="/" className="btn btn-primary">На главную</a>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ErrorPage;
