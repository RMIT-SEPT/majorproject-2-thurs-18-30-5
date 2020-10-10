import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import CustomerHeader from '../Layout/CustomerHeader';
import Footer from '../Layout/Footer';
import './CustomerDashboard.css';
import axios from "axios";

export default class CustomerDashboard extends Component {
  state = {
    pendingBookings: [],
    confirmedBookings: [],
    completedBookings: []
  };
  constructor(props) {
    super(props);
    console.log(this.props.location.state);

    try {
      axios.get("http://localhost:8080/api/booking/all/user/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "PENDING"} })
        .then(res => {
          const bookings = res.data;
          this.setState({pendingBookings: bookings});
          this.state.pendingBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/booking/all/user/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "CONFIRMED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({confirmedBookings: bookings});
          this.state.confirmedBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/booking/all/user/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "COMPLETED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({completedBookings: bookings});
          this.state.completedBookings = bookings;
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
        await axios.put("http://localhost:8080/api/booking/" + booking.id, changedBooking, { headers: {Authorization: this.props.location.state.auth} });
      } catch (err) {

      }
      this.props.history.push('/customer-dashboard', this.props.location.state);
      window.location.reload(false);
    }
  }
  
  render() {
    return (
      <div>
        <CustomerHeader state={this.props.location.state} />
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">G'day, {this.props.location.state.user.firstName}!</div>
              <Link className="btn book-btn" to={{
                pathname: '/choose-service',
                state: this.props.location.state
              }}>Book a service</Link>
            
              <div className="booking-title upcoming-title">Upcoming bookings</div>
              {
                this.state.pendingBookings.length + this.state.confirmedBookings.length > 0 &&
                <div className="cus-up-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="19%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="19%" className="book-header mid-title">End time</th>
                        <th scope="col" width="23%" className="book-header mid-title">Service</th>
                        <th scope="col" width="19%" className="book-header mid-title">Worker</th>
                        <th scope="col" width="15%" className="book-header mid-title">Status</th>
                        <th scope="col" width="2%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.pendingBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
                            <td className="table-remove text-center">
                              <button type="button" className="close" onClick={() => this.handleClick(booking)}>&times;</button>
                            </td>
                          </tr>
                        )
                      }
                      {
                        this.state.confirmedBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
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
                this.state.pendingBookings.length == 0 && this.state.confirmedBookings.length == 0 &&
                <div className="card no-booking-card upcoming-card">
                  <div className="no-booking-msg">No upcoming bookings at the moment.</div>
                </div>
              }
              
              <div className="booking-title past-title">Past bookings</div>
              {
                this.state.completedBookings.length > 0 &&
                <div className="cus-pa-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="19%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="19%" className="book-header mid-title">End time</th>
                        <th scope="col" width="23%" className="book-header mid-title">Service</th>
                        <th scope="col" width="19%" className="book-header mid-title">Worker</th>
                        <th scope="col" width="15%" className="book-header mid-title"></th>
                        <th scope="col" width="2%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.completedBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td></td>
                            <td></td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                this.state.completedBookings.length == 0 &&
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
