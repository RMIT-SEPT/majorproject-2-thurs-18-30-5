import React, { Component } from 'react'
import Footer from '../../Layout/js/Footer'
import Header from '../../Layout/js/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import '../css/BookingPage.css';
import CustomerHeader from '../../Layout/js/CustomerHeader';
import axios from "axios";

export default class ConfirmBooking extends Component {
    state = {
      startDate: this.props.location.state.startDate,
      endDate: this.props.location.state.endDate,
      service: this.props.location.state.service,
      worker: this.props.location.state.worker
  };
  constructor(props) {
    super(props);
  }
  onSubmit = async e =>{
    e.preventDefault();

    const booking = {
      worker: {id: this.state.worker.id},
      user: {id: this.props.location.state.user.id},
      start: this.state.startDate,
      end: this.state.endDate,
      status: 0
    };

    try {
      await axios.post("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/booking", booking, {headers: {Authorization: this.props.location.state.auth}});
    } catch (err) {
      window.alert("This booking has conflict with your other bookings; please try again.");
    }
    this.props.history.push('/customer-dashboard', {user: this.props.location.state.user, auth: this.props.location.state.auth});
  }
  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <CustomerHeader state={this.props.location.state} />

            <form onSubmit={this.onSubmit}>
                <h3 className="book-details-title">Booking details</h3>
                <hr/>
                <br/>

                <div className="booking-label book-label-service">Service</div>
                <div className="booking-detail service-detail">{this.state.service.name}</div>
                <br/>

                <div className="booking-label book-label-start">Start date & time</div>
                <div className="booking-detail start-detail">{this.state.startDate.toString()}</div>
                <br/>

                <div className="booking-label book-label-end">End date & time</div>
                <div className="booking-detail end-detail">{this.state.endDate.toString()}</div>
                <br/>

                <div className="booking-label book-label-worker">Worker</div>
                <div className="booking-detail worker-detail">{this.state.worker.user.firstName}</div>
                <br/><br/>

                <div>
                    <button type="submit" className="booking-btn proceed-btn">Confirm booking</button> 
                </div>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
