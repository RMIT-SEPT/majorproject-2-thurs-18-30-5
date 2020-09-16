import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import CustomerHeader from '../Layout/CustomerHeader'
import Footer from '../Layout/Footer'
import './CustomerDashboard.css'

export default class CustomerDashboard extends Component {
  render() {
    return (
      <div>
        <CustomerHeader user={this.props.location.state} />
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">G'day, {this.props.location.state.user.firstName}!</div>
              <Link className="btn book-btn" to={"/bookingPage1"}>Book a service</Link>
            </div>
            {console.log(this.props)}
            <div className="container upcoming-booking">
              <div className="upcoming-title">Upcoming bookings</div>
              <div className="table-wrapper-scroll-y my-custom-scrollbar">
                <table className="table table-editable text-nowrap table-borderless table-hover">
                  <thead>
                    <tr>
                      <th scope="col" width="20%" className="date">Date</th>
                      <th scope="col" width="20%" className="time">Time</th>
                      <th scope="col" width="30%" className="service">Service</th>
                      <th scope="col" width="25%" className="worker">Worker</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row">14 Oct 2020</th>
                      <td>4pm - 6pm</td>
                      <td>Gym</td>
                      <td>Mark</td>
                      <td className="table-remove text-center cancel-booking">
                        <button type="button" className="close">&times;</button>
                      </td>
                    </tr>
                    <tr>
                      <th scope="row">18 Oct 2020</th>
                      <td>1pm - 2pm</td>
                      <td>Hairdressing</td>
                      <td>Sarah</td>
                      <td className="table-remove text-center cancel-booking">
                        <button type="button" className="close">&times;</button>
                      </td>
                    </tr>
                    <tr>
                      <th scope="row">22 Oct 2020</th>
                      <td>10am - 11am</td>
                      <td>Dentist</td>
                      <td>Peter</td>
                      <td className="table-remove text-center cancel-booking">
                        <button type="button" className="close">&times;</button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div className="container past-booking">
            <div className="past-title">Past bookings</div>
              <table className="table table-editable text-nowrap table-borderless table-hover">
                <thead>
                  <tr>
                    <th scope="col" width="20%">Date</th>
                    <th scope="col" width="20%">Time</th>
                    <th scope="col" width="30%">Service</th>
                    <th scope="col" width="25%">Worker</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">14 May 2020</th>
                    <td>4pm - 6pm</td>
                    <td>Gym</td>
                    <td>Mark</td>
                    <td className="table-remove text-center cancel-booking"></td>
                  </tr>
                  <tr>
                    <th scope="row">18 May 2020</th>
                    <td>1pm - 2pm</td>
                    <td>Hairdressing</td>
                    <td>Sarah</td>
                    <td className="table-remove text-center cancel-booking"></td>
                  </tr>
                  <tr>
                    <th scope="row">22 May 2020</th>
                    <td>10am - 11am</td>
                    <td>Dentist</td>
                    <td>Peter</td>
                    <td className="table-remove text-center cancel-booking"></td>
                  </tr>
                </tbody>
              </table>
            </div>
            
          </div>
        <Footer/>
      </div>
    )
  }
}
