import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import WorkerHeader from '../Layout/WorkerHeader';
import Footer from '../Layout/Footer';
import './WorkerJobs.css'
import axios from "axios";

export default class WorkerJobs extends Component {
  state = {
    pendingBookings: []
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://localhost:8080/api/booking/all/worker/" + this.props.location.state.user.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "PENDING"} })
        .then(res => {
          const bookings = res.data;
          this.setState({pendingBookings: bookings});
          this.state.pendingBookings = bookings;
        })
    } catch (err) {

    }

    this.onClick1 = this.onClick1.bind(this);
    this.onClick2 = this.onClick2.bind(this);
  }

  onClick1 = async booking => {
    if (window.confirm("Are you sure you want to accept the booking?")) {
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
        status: "CONFIRMED"
      };

      try {
        await axios.put("http://localhost:8080/api/booking/" + booking.id, changedBooking, {headers: {Authorization: this.props.location.state.auth}});
      } catch (err) {

      }
      window.location.reload(false);
    }
  }

  onClick2 = async booking => {
    if (window.confirm("Are you sure you want to decline the booking?")) {
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
              <div className="welcome-msg">Pending Jobs</div>
              
              {
                this.state.pendingBookings.length > 0 &&
                <div className="pending-jobs-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table job-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="20%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="20%" className="book-header mid-title">End time</th>
                        <th scope="col" width="21%" className="book-header mid-title">Customer</th>
                        <th scope="col" width="16%" className="book-header mid-title"></th>
                        <th scope="col" width="18%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.pendingBookings.map(booking =>
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.user.firstName}</td>
                            <td>
                              <button type="button" className="btn job-action accept-job" onClick={() => this.onClick1(booking)}>accept</button>
                            </td>
                            <td>
                              <button type="button" className="btn job-action decline-job" onClick={() => this.onClick2(booking)}>decline</button>
                            </td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                this.state.pendingBookings.length == 0 &&
                <div className="card no-pending-card">
                  <div className="no-booking-msg">No pending jobs at the moment.</div>
                </div>
              }
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
