import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "./Header";
import Footer from "./Footer";

class PageWrapper extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const Child = this.props.child;
        const props = this.props;

        return (
            <div>
                <Header/>
                <div className="page-wrapper">
                    <Child{...props}/>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default PageWrapper;