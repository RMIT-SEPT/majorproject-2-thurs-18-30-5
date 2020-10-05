import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './BookingSummary.css'
import axios from "axios";

export default class UpcomingBooking extends Component {
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
              <div className="book-title">Upcoming Bookings</div>

              <div className="book-summary-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover book-summary-table">
                  <thead className="book-summary-title">
                    <tr>
                      <th scope="col" width="16%" className="book-header left-title">Start Time</th>
                      <th scope="col" width="17%" className="book-header mid-title">End Time</th>
                      <th scope="col" width="18%" className="book-header mid-title">Service</th>
                      <th scope="col" width="17%" className="book-header mid-title">Worker</th>
                      <th scope="col" width="17%" className="book-header mid-title">Customer</th>
                      <th scope="col" width="15%" className="book-header right-title">Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      this.state.bookings.map(booking =>
                        (booking.status == "PENDING" || booking.status == "CONFIRMED") &&
                        <tr>
                          <th scope="row">{booking.start}</th>
                          <td>{booking.end}</td>
                          <td>{this.props.location.state.user.business.name}</td>
                          <td>{booking.worker.user.firstName}</td>
                          <td>{booking.user.firstName}</td>
                          <td>{booking.status}</td>
                        </tr>
                      )
                    }
                  </tbody>
                </table>
              </div>

            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
