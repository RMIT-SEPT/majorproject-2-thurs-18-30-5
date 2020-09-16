import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/General/Login';
import SignUp from './components/General/Signup';
import ForgotPassword from './components/General/ForgotPassword';
import CustomerDashboard from './components/Customer/CustomerDashboard';
import BookingPage1 from './components/Customer/BookingPage1';
import BookingPage2 from './components/Customer/BookingPage2';
import BookingPage3 from './components/Customer/BookingPage3';
import ConfirmBooking from './components/Customer/ConfirmBooking';
import CustomerProfile from './components/Customer/CustomerProfile';

ReactDOM.render(
  <BrowserRouter>
       <Switch>
        <Route exact path="/" component={App} />
        <Route path="/customer-dashboard" component={CustomerDashboard} />
        <Route path="/sign-up" component={SignUp} />
        <Route path="/sign-in" component={Login} />
        <Route path="/forgotPassword" component={ForgotPassword} />
        <Route path="/bookingPage1" component={BookingPage1} />
        <Route path="/bookingPage2" component={BookingPage2} />
        <Route path="/bookingPage3" component={BookingPage3} />
        <Route path="/confirmBooking" component={ConfirmBooking} />
        <Route path="/customer-profile" component={CustomerProfile} />
      </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
