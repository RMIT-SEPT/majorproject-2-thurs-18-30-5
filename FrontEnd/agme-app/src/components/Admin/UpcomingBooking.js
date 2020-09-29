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
                      <th scope="col" width="15%" className="book-header date">Start Time</th>
                      <th scope="col" width="20%" className="book-header time">End Time</th>
                      <th scope="col" width="25%" className="book-header service">Service</th>
                      <th scope="col" width="20%" className="book-header worker">Worker</th>
                      <th scope="col" width="20%" className="book-header customer">Customer</th>
                    </tr>
                  </thead>
                  <tbody>
                    {
                      this.state.bookings.map(booking =>
                        booking.status == "PENDING" &&
                        <tr>
                          <th scope="row">{booking.start}</th>
                          <td>{booking.end}</td>
                          <td>{this.props.location.state.user.business.name}</td>
                          <td>{booking.worker.user.firstName}</td>
                          <td>{booking.user.firstName}</td>
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
