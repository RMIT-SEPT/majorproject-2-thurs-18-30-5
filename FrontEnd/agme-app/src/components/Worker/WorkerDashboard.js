import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import WorkerHeader from '../Layout/WorkerHeader';
import Footer from '../Layout/Footer';
import axios from "axios";

export default class WorkerDashboard extends Component {
  state = {
    confirmedBookings: [],
    completedBookings: []
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://localhost:8080/api/booking/all/worker/" + this.props.location.state.user.id, { params: {bookingStatus: "CONFIRMED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({confirmedBookings: bookings});
          this.state.confirmedBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/booking/all/worker/" + this.props.location.state.user.id, { params: {bookingStatus: "COMPLETED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({completedBookings: bookings});
          this.state.completedBookings = bookings;
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
                pathname: '/worker-jobs',
                state: this.props.location.state
              }}>Pending Jobs</Link>
              
              <div className="booking-title upcoming-title">Upcoming bookings</div>
              {
                this.state.confirmedBookings.length > 0 &&
                <div className="cus-up-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="17%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="17%" className="book-header mid-title">End time</th>
                        <th scope="col" width="19%" className="book-header mid-title">Customer</th>
                        <th scope="col" width="19%" className="book-header mid-title">Status</th>
                        <th scope="col" width="5%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.confirmedBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.business.name}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td>{booking.status}</td>
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
                this.state.confirmedBookings.length == 0 &&
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
                      <th scope="col" width="17%" className="book-header mid-title">Start time</th>
                      <th scope="col" width="17%" className="book-header mid-title">End time</th>
                      <th scope="col" width="19%" className="book-header mid-title">Service</th>
                      <th scope="col" width="19%" className="book-header mid-title">Worker</th>
                      <th scope="col" width="19%" className="book-header mid-title">Status</th>
                      <th scope="col" width="5%" className="book-header right-title"></th>
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
                            <td>{booking.status}</td>
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
