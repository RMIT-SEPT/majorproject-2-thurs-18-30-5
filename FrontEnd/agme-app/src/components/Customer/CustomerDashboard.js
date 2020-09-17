import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import CustomerHeader from '../Layout/CustomerHeader';
import Footer from '../Layout/Footer';
import './CustomerDashboard.css';
import axios from "axios";

export default class CustomerDashboard extends Component {
  state = {
    upcomingBookings: [],
    pastBookings: []
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://localhost:8080/api/booking/all/user/" + this.props.location.state.user.id, { params: {bookingStatus: "PENDING"} })
        .then(res => {
          const bookings = res.data;
          this.setState({upcomingBookings: bookings});
          this.state.upcomingBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/booking/all/user/" + this.props.location.state.user.id, { params: {bookingStatus: "COMPLETED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({pastBookings: bookings});
          this.state.pastBookings = bookings;
        })
    } catch (err) {

    }
  }

  handleClick (booking) {
    
    // Set the booking status to cancelled
    booking.status = "CANCELLED";
    try {
      axios.put("http://localhost:8080/api/booking/" + booking.id, booking);
    } catch (err) {

    }
    this.props.history.push('/customer-dashboard', {user: this.props.location.state.user});
  }

  render() {
    return (
      <div>
        <CustomerHeader user={this.props.location.state} />
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">G'day, {this.props.location.state.user.firstName}!</div>
              <Link className="btn book-btn" to={{
                pathname: '/bookingPage1',
                state: this.props.location.state
              }}>Book a service</Link>
            </div>
            <div className="container upcoming-booking">
              <div className="upcoming-title">Upcoming bookings</div>
              <div className="table-wrapper-scroll-y my-custom-scrollbar">
                <table className="table table-editable text-nowrap table-borderless table-hover">
                  <thead>
                    <tr>
                      <th scope="col" width="20%" className="date">Start time</th>
                      <th scope="col" width="20%" className="time">End time</th>
                      <th scope="col" width="30%" className="service">Service</th>
                      <th scope="col" width="25%" className="worker">Worker</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      this.state.upcomingBookings.map(booking =>
                        <tr>
                          <th scope="row">{booking.start}</th>
                          <td>{booking.end}</td>
                          <td>{booking.worker.business.name}</td>
                          <td>{booking.worker.user.firstName}</td>
                          <td className="table-remove text-center cancel-booking">
                            <button type="button" className="close" onClick={() => this.handleClick(booking)}>&times;</button>
                          </td>
                        </tr>
                      )
                    }
                  </tbody>
                </table>
              </div>
            </div>

            <div className="container past-booking">
            <div className="past-title">Past bookings</div>
              <table className="table table-editable text-nowrap table-borderless table-hover">
                <thead>
                  <tr>
                    <th scope="col" width="20%">Start time</th>
                    <th scope="col" width="20%">End time</th>
                    <th scope="col" width="30%">Service</th>
                    <th scope="col" width="25%">Worker</th>
                  </tr>
                </thead>
                <tbody>
                  {
                    this.state.pastBookings.map(booking =>
                      <tr>
                        <th scope="row">{booking.start}</th>
                        <td>{booking.end}</td>
                        <td>{booking.worker.business.name}</td>
                        <td>{booking.worker.user.firstName}</td>
                      </tr>
                    )
                  }
                </tbody>
              </table>
            </div>
            
          </div>
        <Footer/>
      </div>
    )
  }
}
