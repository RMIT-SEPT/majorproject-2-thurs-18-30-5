import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './BookingSummary.css'

export default class UpcomingBooking extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container book-summary-page">
              <div className="book-title">Upcoming Bookings</div>

              <div className="book-summary-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover book-summary-table">
                  <thead className="book-summary-title">
                    <tr>
                      <th scope="col" width="15%" className="book-header left-title">Date</th>
                      <th scope="col" width="20%" className="book-header mid-title">Time</th>
                      <th scope="col" width="25%" className="book-header mid-title">Service</th>
                      <th scope="col" width="20%" className="book-header mid-title">Worker</th>
                      <th scope="col" width="20%" className="book-header right-title">Customer</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row">14 Oct 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 Oct 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 Oct 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 Oct 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 Oct 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 Oct 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 Oct 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 Oct 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 Oct 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 Oct 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 Oct 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 Oct 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    
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
