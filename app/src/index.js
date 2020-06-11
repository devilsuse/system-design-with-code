import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route} from 'react-router-dom'

import './index.css';
import Shortener from "./components/short-url-generator/Shortener";
import NavBar from "./components/navbar/NavBar";

import SignIn from "./components/login/SignIn";

class App extends React.Component {

    render() {
        return (
            <div>
                <div>
                    <Router>
                        <NavBar/>
                        <Route exact path="/" component={Shortener}/>
                        <Route path="/signin" component={SignIn}/>
                    </Router>
                </div>
            </div>
        );
    }
}

ReactDOM.render(
        <App/>, document.getElementById('root')
);
