import React, {Component} from "react";
import "./App.css";

class VerdictsInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            verdicts: null
        };
    }

    componentDidMount() {
        fetch("/api/verdicts")
            .then((res) => res.json())
            .then((result) => {
                this.setState({
                    verdicts: result
                });
            })
    }

    render() {
        const {verdicts} = this.state;
        if (verdicts === null)
            return (<div>error</div>);

        document.title = "StudHub: Вердикты решений";

        const verdictsTableBody = verdicts.map((verdict) =>
            <tr>
                <td style={verdict.code === "OK" ? {color: "green"} : {color: "red"}}>{verdict.code}</td>
                <td>{verdict.transcription}</td>
                <td>{verdict.description}</td>
            </tr>
        );

        return (
            <div className="container">
                <h2 className="font-weight-bold pb-2">Вердикты</h2>

                <div className="verdicts_table">
                    <table className="table">
                        <thead className="thead-light">
                        <tr>
                            <th>Сокращение</th>
                            <th>Транскрипция</th>
                            <th>Значение</th>
                        </tr>
                        </thead>
                        <tbody>
                        {verdictsTableBody}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
};

export default VerdictsInfo;