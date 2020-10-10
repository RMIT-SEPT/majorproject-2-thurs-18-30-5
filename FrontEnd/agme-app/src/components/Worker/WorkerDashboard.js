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
      axios.get("http://localhost:8080/api/booking/all/worker/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "CONFIRMED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({confirmedBookings: bookings});
          this.state.confirmedBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/booking/all/worker/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "COMPLETED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({completedBookings: bookings});
          this.state.completedBookings = bookings;
        })
    } catch (err) {

    }

    this.onClick = this.onClick.bind(this);
  }
  
  onClick = async booking => {
    if (window.confirm("Are you sure you completed the booking?")) {
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
        status: "COMPLETED"
      };

      try {
        await axios.put("http://localhost:8080/api/booking/" + booking.id, changedBooking, {headers: {Authorization: this.props.location.state.auth}});
      } catch (err) {

      }
      window.location.reload(false);
    }
  }

  render() {
    return (
      <div>
        <WorkerHeader state={this.props.location.state} />
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">G'day, {this.props.location.state.user.user.firstName}!</div>
              <Link className="btn book-btn" to={{
                pathname: '/worker-jobs',
                state: this.props.location.state
              }}>Pending Jobs</Link>
              
              <div className="booking-title upcoming-title">Confirmed bookings</div>
              {
                this.state.confirmedBookings.length > 0 &&
                <div className="cus-up-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table job-table">
                    <thead className="book-summary-title">
                      <tr>
                      <th scope="col" width="5%" className="book-header left-title"></th>
                      <th scope="col" width="18%" className="book-header mid-title">Start time</th>
                      <th scope="col" width="18%" className="book-header mid-title">End time</th>
                      <th scope="col" width="20%" className="book-header mid-title">Customer</th>
                      <th scope="col" width="19%" className="book-header mid-title">Status</th>
                      <th scope="col" width="15%" className="book-header mid-title"></th>
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
                            <td>{booking.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
                            <td>
                              <button type="button" className="btn job-action accept-job" onClick={() => this.onClick(booking)}>complete</button>
                            </td>
                            <td></td>
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
                  <div className="no-booking-msg">No confirmed bookings at the moment.</div>
                </div>
              }
              
              <div className="booking-title past-title">Completed bookings</div>
              {
                this.state.completedBookings.length > 0 &&
                <div className="cus-pa-book-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table">
                  <thead className="book-summary-title">
                      <tr>
                      <th scope="col" width="5%" className="book-header left-title"></th>
                      <th scope="col" width="18%" className="book-header mid-title">Start time</th>
                      <th scope="col" width="18%" className="book-header mid-title">End time</th>
                      <th scope="col" width="20%" className="book-header mid-title">Customer</th>
                      <th scope="col" width="19%" className="book-header mid-title">Status</th>
                      <th scope="col" width="15%" className="book-header mid-title"></th>
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
                            <td>{booking.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
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
                  <div className="no-booking-msg">No completed bookings recorded.</div>
                </div>
              }
              
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
