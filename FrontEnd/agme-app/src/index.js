import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

import SignUp from './components/General/js/Signup';
import Login from './components/General/js/Login';
import ForgotPassword from './components/General/js/ForgotPassword';

import CustomerDashboard from './components/Customer/js/CustomerDashboard';
import CustomerProfile from './components/Customer/js/CustomerProfile';
import ChooseService from './components/Customer/js/ChooseService';
import ChooseTime from './components/Customer/js/ChooseTime';
import ChooseWorker from './components/Customer/js/ChooseWorker';
import ConfirmBooking from './components/Customer/js/ConfirmBooking';

import AdminDashboard from './components/Admin/js/AdminDashboard';
import AddWorker from './components/Admin/js/AddWorker';
import Business from './components/Admin/js/Business';
import UpcomingBooking from './components/Admin/js/UpcomingBooking';
import PastBooking from './components/Admin/js/PastBooking';
import WorkerPage from './components/Admin/js/WorkerPage';

import WorkerDashboard from './components/Worker/js/WorkerDashboard';
import WorkerProfile from './components/Worker/js/WorkerProfile';
import WorkerJobs from './components/Worker/js/WorkerJobs';

ReactDOM.render(
  <BrowserRouter>
       <Switch>
        <Route exact path="/" component={App} />
        <Route path="/sign-up" component={SignUp} />
        <Route path="/sign-in" component={Login} />
        <Route path="/forgotPassword" component={ForgotPassword} />
        
        <Route path="/customer-dashboard" component={CustomerDashboard} />
        <Route path="/customer-profile" component={CustomerProfile} />
        <Route path="/choose-service" component={ChooseService} />
        <Route path="/choose-time" component={ChooseTime} />
        <Route path="/choose-worker" component={ChooseWorker} />
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
