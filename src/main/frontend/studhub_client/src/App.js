import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Mainpage from "./Mainpage";
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from "./Login";
import ErrorPage from "./ErrorPage";
import Header from "./Header";

class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <BrowserRouter>
                <Switch>
                    <Route exact path="/">
                        <Header/>
                        <Mainpage/>
                    </Route>
                    <Route exact path="/login">
                        <Login/>
                    </Route>
                    <Route>
                        <ErrorPage code={404} description={"Страница не найдена."}/>
                    </Route>
                </Switch>
            </BrowserRouter>
        );
    }
}

export default App;
