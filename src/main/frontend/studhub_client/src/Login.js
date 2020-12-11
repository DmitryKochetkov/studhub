// import './App.css';
import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './form-signin.css'

class Login extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        document.body.classList.add('form-signin-body');
        return (
            <div className="text-center">
                <h1 className="font-weight-bold">StudHub</h1>
                <form action="/login" method="post" className="form-signin">
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
