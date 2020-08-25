import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import SignUp from './components/Signup';
import BookingPage1 from './components/BookingPage1';
import BookingPage2 from './components/BookingPage2';
import BookingPage3 from './components/BookingPage3';
import ConfirmBooking from './components/ConfirmBooking';

ReactDOM.render(
  <BrowserRouter>
       <Switch>
        <Route exact path="/" component={App} />
        <Route path="/sign-up" component={SignUp} />
        <Route path="/sign-in" component={Login} />
        <Route path="/bookingPage1" component={BookingPage1} />
        <Route path="/bookingPage2" component={BookingPage2} />
        <Route path="/bookingPage3" component={BookingPage3} />
        <Route path="/confirmBooking" component={ConfirmBooking} />
      </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
