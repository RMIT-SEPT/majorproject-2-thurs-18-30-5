import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './BookingSummary.css'

export default class PastBooking extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container book-summary-page">
              <div className="book-title">Past Bookings</div>

              <div className="book-summary-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover book-summary-table">
                  <thead className="book-summary-title">
                    <tr>
                      <th scope="col" width="20%" className="date">DATE</th>
                      <th scope="col" width="20%" className="time">TIME</th>
                      <th scope="col" width="30%" className="service">SERVICE</th>
                      <th scope="col" width="25%" className="worker">WORKER</th>
                      <th scope="col" width="20%" className="customer">CUSTOMER</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row">14 May 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 May 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 May 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 May 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 May 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 May 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 May 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 May 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 May 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td>Lisa</td>
                    </tr>
                    <tr>
                      <th scope="row">14 May 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td>John</td>
                    </tr>
                    <tr>
                      <th scope="row">18 May 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td>Alex</td>
                    </tr>
                    <tr>
                      <th scope="row">22 May 2020</th>
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
