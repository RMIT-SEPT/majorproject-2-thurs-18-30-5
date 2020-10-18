import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../../Layout/js/AdminHeader'
import Footer from '../../Layout/js/Footer'
import '../css/BookingSummary.css'
import axios from "axios";

export default class UpcomingBooking extends Component {
  state = {
    pendingBookings: [],
    confirmedBookings: []
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/booking/all/business/" + this.props.location.state.user.business.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "PENDING"} })
        .then(res => {
          const bookings = res.data;
          this.setState({pendingBookings: bookings});
          this.state.pendingBookings = bookings;
        })
    } catch (err) {

    }

    try {
      axios.get("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/booking/all/business/" + this.props.location.state.user.business.id, { headers: {Authorization: this.props.location.state.auth}, params: {bookingStatus: "CONFIRMED"} })
        .then(res => {
          const bookings = res.data;
          this.setState({confirmedBookings: bookings});
          this.state.confirmedBookings = bookings;
        })
    } catch (err) {

    }
  }
  render() {
    return (
      <div>
        <AdminHeader state={this.props.location.state} />
          <div className="admin-img">
            <div className="container book-summary-page">
              <div className="book-title">Upcoming Bookings</div>

              {
                this.state.pendingBookings.length + this.state.confirmedBookings.length > 0 &&
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
                        this.state.pendingBookings.map(booking =>
                          (booking.status == "PENDING" || booking.status == "CONFIRMED") &&
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td>{booking.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
                            <td></td>
                          </tr>
                        )
                      }
                      {
                        this.state.confirmedBookings.map(booking =>
                          (booking.status == "PENDING" || booking.status == "CONFIRMED") &&
                          <tr>
                            <td></td>
                            <td>{booking.start}</td>
                            <td>{booking.end}</td>
                            <td>{booking.worker.user.firstName}</td>
                            <td>{booking.user.firstName}</td>
                            <td className="booking-status">{booking.status}</td>
                            <td></td>
                          </tr>
                        )
                      }
                    </tbody>
                  </table>
                </div>
              }
              {
                this.state.pendingBookings.length == 0 && this.state.confirmedBookings.length == 0 &&
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
