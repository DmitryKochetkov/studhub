import './App.css';
import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './form-signin.css'

class Login extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="text-center">
                <form action="/login" method="post" className="form-signin">
                    <h1 className="h3 mb-3 font-weight-normal">Sign in</h1>
                    <label htmlFor="inputLogin" className="sr-only">Login</label>
                    <input type="text" id="inputLogin" className="form-control" placeholder="Login" required=""
                           autoFocus=""/>
                    <label htmlFor="inputPassword" className="sr-only">Password</label>
                    <input type="password" id="inputPassword" className="form-control" placeholder="Password"
                           required=""/>
                    <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </form>
            </div>

        );
    }
}

export default Login;
