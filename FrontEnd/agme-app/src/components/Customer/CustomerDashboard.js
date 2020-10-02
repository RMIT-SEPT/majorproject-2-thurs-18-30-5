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
      axios.get("http://sept-backend.us-east-1.elasticbeanstalk.com/api/booking/all/user/" + this.props.location.state.user.id, { params: {bookingStatus: "PENDING"} })
        .then(res => {
          const bookings = res.data;
          this.setState({upcomingBookings: bookings});
          this.state.upcomingBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://sept-backend.us-east-1.elasticbeanstalk.com/api/booking/all/user/" + this.props.location.state.user.id, { params: {bookingStatus: "COMPLETED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({pastBookings: bookings});
          this.state.pastBookings = bookings;
        })
    } catch (err) {

    }
  }

  handleClick = async booking => {
    if (window.confirm("Are you sure you want to cancel the booking?")) {
      // Set the booking status to cancelled
      var changedBooking = {
        id: booking.id,
        user: {
          id: booking.user.id
        },
        worker: {
          id: booking.worker.id
        },
        start: booking.start,
        end: booking.end,
        status: "CANCELLED"
      };

      try {
        await axios.put("http://sept-backend.us-east-1.elasticbeanstalk.com/api/booking/" + booking.id, changedBooking);
      } catch (err) {

      }
      this.props.history.push('/customer-dashboard', {user: this.props.location.state.user});
      window.location.reload(false);
    }
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
            
              <div className="booking-title upcoming-title">Upcoming bookings</div>
              {
                this.state.upcomingBookings.length > 0 &&
                <div className="cus-up-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="20%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="20%" className="book-header mid-title">End time</th>
                        <th scope="col" width="25%" className="book-header mid-title">Service</th>
                        <th scope="col" width="25%" className="book-header mid-title">Worker</th>
                        <th scope="col" width="5%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.upcomingBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td className="table-remove text-center">
                              <button type="button" className="close" onClick={() => this.handleClick(booking)}>&times;</button>
                            </td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                this.state.upcomingBookings.length == 0 &&
                <div className="card no-booking-card upcoming-card">
                  <div className="no-booking-msg">No upcoming bookings at the moment. Book a service now!</div>
                </div>
              }
              
              <div className="booking-title past-title">Past bookings</div>
              {
                this.state.pastBookings.length > 0 &&
                <div className="cus-pa-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                  <thead className="book-summary-title">
                      <tr>
                      <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="20%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="20%" className="book-header mid-title">End time</th>
                        <th scope="col" width="25%" className="book-header mid-title">Service</th>
                        <th scope="col" width="25%" className="book-header mid-title">Worker</th>
                        <th scope="col" width="5%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.pastBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td></td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                this.state.pastBookings.length == 0 &&
                <div className="card no-booking-card past-card">
                  <div className="no-booking-msg">No past bookings recorded.</div>
                </div>
              }
              
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
