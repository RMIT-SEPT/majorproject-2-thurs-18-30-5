import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import WorkerHeader from '../Layout/WorkerHeader';
import Footer from '../Layout/Footer';
import './WorkerDashboard.css';
import axios from "axios";

export default class WorkerDashboard extends Component {
  state = {
    upcomingBookings: [],
    pastBookings: []
  };
  constructor(props) {
    super(props);

    console.log(this.props.location.state.user);
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
  
  render() {
    return (
      <div>
        <WorkerHeader user={this.props.location.state} />
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">G'day, {this.props.location.state.user.user.firstName}!</div>
              <Link className="btn book-btn" to={{
                pathname: '/bookingPage1',
                state: this.props.location.state
              }}>Pending jobs</Link>
            
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
                  <div className="no-booking-msg">No upcoming bookings at the moment.</div>
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
