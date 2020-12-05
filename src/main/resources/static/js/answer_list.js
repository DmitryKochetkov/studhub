'use strict';

/**
 * Загружает список ответов для вопросов с выбором ответа.
 */

class AnswerList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            answers: []
        }
    }

    componentDidMount() {
        fetch('/api/student/' + 2 + "/course/" + 1 + "/homework/" + 1 + "/problems/" + 4)
            .then(res => res.json())
            .then(
                (result) => {
                    if (result.problem.answers) {
                        this.setState({
                            isLoaded: true,
                            answers: result.problem.answers
                        });
                    }
                    else {
                        this.setState({error: true});
                    }
                },

                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    render() {
        const { error, isLoaded, answers } = this.state;

        if (error) {
            return <div class="alert alert-danger">Произошла ошибка при загрузке задания.</div>
        }
        else if (!isLoaded) {
            return <div class="alert alert-info">Загрузка...</div>
        }
        else {
            return (
                answers.map((item, index) => (
                        <div>
                            <input type="radio" name="selectedAnswer" id={index}/>
                            <label className="pl-2" htmlFor={"answer" + index}>{item.name}{item}</label>
                        </div>
                    ))
            );
        }
    }
}

ReactDOM.render(
    React.createElement(AnswerList),
    document.getElementById('answer_list')
);