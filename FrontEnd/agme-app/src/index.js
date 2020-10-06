import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

import SignUp from './components/General/Signup';
import Login from './components/General/Login';
import ForgotPassword from './components/General/ForgotPassword';

import CustomerDashboard from './components/Customer/CustomerDashboard';
import CustomerProfile from './components/Customer/CustomerProfile';
import BookingPage1 from './components/Customer/BookingPage1';
import BookingPage2 from './components/Customer/BookingPage2';
import BookingPage3 from './components/Customer/BookingPage3';
import ConfirmBooking from './components/Customer/ConfirmBooking';

import AdminDashboard from './components/Admin/AdminDashboard';
import AddWorker from './components/Admin/AddWorker';
import Business from './components/Admin/Business';
import UpcomingBooking from './components/Admin/UpcomingBooking';
import PastBooking from './components/Admin/PastBooking';
import WorkerPage from './components/Admin/WorkerPage';

import WorkerDashboard from './components/Worker/WorkerDashboard';
import WorkerProfile from './components/Worker/WorkerProfile';
import WorkerJobs from './components/Worker/WorkerJobs';

ReactDOM.render(
  <BrowserRouter>
       <Switch>
        <Route exact path="/" component={App} />
        <Route path="/sign-up" component={SignUp} />
        <Route path="/sign-in" component={Login} />
        <Route path="/forgotPassword" component={ForgotPassword} />
        
        <Route path="/customer-dashboard" component={CustomerDashboard} />
        <Route path="/customer-profile" component={CustomerProfile} />
        <Route path="/bookingPage1" component={BookingPage1} />
        <Route path="/bookingPage2" component={BookingPage2} />
        <Route path="/bookingPage3" component={BookingPage3} />
        <Route path="/confirmBooking" component={ConfirmBooking} />

        <Route path="/admin-dashboard" component={AdminDashboard} />
        <Route path="/add-worker" component={AddWorker} />
        <Route path="/business" component={Business} />
        <Route path="/upcoming-booking" component={UpcomingBooking} />
        <Route path="/past-booking" component={PastBooking} />
        <Route path="/worker-page" component={WorkerPage} />

        <Route path="/worker-dashboard" component={WorkerDashboard} />
        <Route path="/worker-profile" component={WorkerProfile} />
        <Route path="/worker-jobs" component={WorkerJobs} />
        
      </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
