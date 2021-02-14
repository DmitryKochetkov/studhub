import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Loader.css'

class Loader extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <div className='loader-wrapper'>
                    <div className="font-weight-bold">Загрузка...</div>
                    <div className={"loader"}/>
                </div>
            </div>
        );
    }
}

export default Loader;