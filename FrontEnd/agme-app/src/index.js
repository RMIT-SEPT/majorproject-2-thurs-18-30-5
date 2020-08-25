import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import SignUp from './components/Signup';
import ForgotPassword from './components/ForgotPassword';
import CustomerDashboard from './components/CustomerDashboard';

ReactDOM.render(
  <BrowserRouter>
       <Switch>
        <Route exact path="/" component={App} />
        <Route path="/customer-dashboard" component={CustomerDashboard} />
        <Route path="/sign-up" component={SignUp} />
        <Route path="/sign-in" component={Login} />
        <Route path="/forgotPassword" component={ForgotPassword} />
      </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
