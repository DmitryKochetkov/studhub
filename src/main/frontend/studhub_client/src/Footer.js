import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Moment from "react-moment";
import './Footer.css'

class Footer extends Component {
    render() {
        return (
            <footer className="site-footer">
                <div className="container">
                    <div className="row">
                        <div className="col-sm-12 col-md-9">
                            <h6>О сервисе</h6>
                            <p className="text-justify">StudHub - сервис для отслеживания прогресса обучения при подготовке к экзаменам, </p>

                            <p>Время на сервере: {<Moment>{new Date()}</Moment>}</p>
                        </div>

                        <div className="col-xs-6 col-md-3">
                            <h6>Ссылки</h6>
                            <ul className="footer-links">
                                <li><a href="/about-courses">О курсах</a></li>
                                <li><a href="/api-docs">API Docs</a></li>
                                <li><a href="https://github.com/DmitryKochetkov/studhub">Contribute (Github)</a></li>
                            </ul>
                        </div>
                    </div>
                    <hr/>
                </div>
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 col-sm-6 col-xs-12">
                            <p className="copyright-text">Copyright &copy; 2020-2021 All Rights Reserved by
                                <a href="https://github.com/DmitryKochetkov">DmitryKochetkov</a>.
                            </p>
                        </div>
                    </div>
                </div>
            </footer>
        );
    }
}

export default Footer;