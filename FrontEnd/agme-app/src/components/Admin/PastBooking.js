import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './BookingSummary.css'
import axios from "axios";

export default class PastBooking extends Component {
  state = {
    bookings: []
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://localhost:8080/api/booking/all/business/" + this.props.location.state.user.business.id)
        .then(res => {
          const bookings = res.data;
          this.setState({bookings: bookings});
          this.state.bookings = bookings;
        })
    } catch (err) {

    }
  }
  render() {
    return (
      <div>
        <AdminHeader user={this.props.location.state} />
          <div className="admin-img">
            <div className="container book-summary-page">
              <div className="book-title">Past Bookings</div>

              {
                <div className="book-summary-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover book-summary-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="19%" className="book-header mid-title">Start Time</th>
                        <th scope="col" width="19%" className="book-header mid-title">End Time</th>
                        <th scope="col" width="19%" className="book-header mid-title">Worker</th>
                        <th scope="col" width="19%" className="book-header mid-title">Customer</th>
                        <th scope="col" width="14%" className="book-header mid-title">Status</th>
                        <th scope="col" width="5%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        this.state.bookings.map(booking =>
                          (booking.status == "COMPLETED" || booking.status == "CANCELLED") &&
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td>{booking.user.firstName}</td>
                            <td>{booking.status}</td>
                            <td></td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                
                <div className="card admin-booking-card">
                  <div className="no-booking-msg">No upcoming bookings at the moment.</div>
                </div>
              }

            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
