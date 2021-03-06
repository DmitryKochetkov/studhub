import './App.css';
import React, {Component} from 'react';

class Mainpage extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="page-container">
                <div className={"content-wrap"}>
                    <div className='container'>
                        <h1 className='font-weight-bold pb-3'>Главная</h1>
                        <p>Привет! Меня зовут Дмитрий, и я занимаюсь преподаванием различных математических и IT-дисциплин. На
                            этом сайте мои ученики и их родители могут отслеживать оценки по домашним работам и прогресс
                            обучения.</p>
                        <div className='alert alert-warning'>
                            Данный сайт находится в стадии разработки.
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Mainpage;
