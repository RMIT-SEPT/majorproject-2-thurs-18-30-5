import React, { Component } from 'react'
import Footer from '../Layout/Footer'
import Header from '../Layout/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';
import CustomerHeader from '../Layout/CustomerHeader';
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
      const res = await axios.post("http://Backend-Dev-dev.us-east-1.elasticbeanstalk.com/api/booking", booking);
    } catch (err) {
      console.log(err);
    }
    this.props.history.push('/customer-dashboard', {user: this.props.location.state.user});
  }
  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <CustomerHeader user={this.props.location.state} />

            <form onSubmit={this.onSubmit}>
                <h3>Booking details</h3>
                <hr/>
                <br/>
                <br/>
                <h6>Service:</h6>
                <h5 className="hCol">{this.state.service.name}</h5>
                <br/>
                <h6>Start date & time:</h6>
                <h5 className="hCol">{this.state.startDate.toString()}</h5>
                <br/>
                <h6>End date & time:</h6>
                <h5 className="hCol">{this.state.endDate.toString()}</h5>
                <br/>
                <h6>Worker:</h6>
                <h5 className="hCol">{this.state.worker.user.firstName}</h5>
                <br/>
                <br/>

                <div className="btn2-div">
                    <button type="submit" className="mybtn btn-primary">Confirm booking</button> 
                </div>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
