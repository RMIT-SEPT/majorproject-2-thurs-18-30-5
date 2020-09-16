import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './AdminDashboard.css'

export default class AdminDashboard extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container admin-dashboard">
              <div className="admin-title">Admin Dashboard</div>
            </div>

            <div className="container admin-functions">
              <div className="func-add-worker">
                <Link className="btn func-btn" to={"/add-worker"}>Add new worker</Link>
              </div>
              <div className="func-add-business">
                <Link className="btn func-btn" to={"/business"}>Edit business</Link>
              </div>
              <div className="func-upcoming">
                <Link className="btn func-btn" to={"/upcoming-booking"}>Upcoming bookings</Link>
              </div>
              <div className="func-past">
                <Link className="btn func-btn" to={"/past-booking"}>Past bookings</Link>
              </div>
              <div className="func-availability">
                <Link className="btn func-btn" to={"/worker-availability"}>Workers availability</Link>
              </div>

              <div className="worker-table-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover worker-table">
                  <thead>
                    <tr><th className="worker-table-title">WORKERS</th></tr>
                  </thead>
                  <tbody>
                    <tr><td className="worker-table-item">Mark</td></tr>
                    <tr><td className="worker-table-item">Sarah</td></tr>
                    <tr><td className="worker-table-item">Peter</td></tr>
                    <tr><td className="worker-table-item">Mark</td></tr>
                    <tr><td className="worker-table-item">Sarah</td></tr>
                    <tr><td className="worker-table-item">Peter</td></tr>
                    <tr><td className="worker-table-item">Mark</td></tr>
                    <tr><td className="worker-table-item">Sarah</td></tr>
                    <tr><td className="worker-table-item">Peter</td></tr>
                    <tr><td className="worker-table-item">Mark</td></tr>
                    <tr><td className="worker-table-item">Sarah</td></tr>
                    <tr><td className="worker-table-item">Peter</td></tr>
                    <tr><td className="worker-table-item">Mark</td></tr>
                    <tr><td className="worker-table-item">Sarah</td></tr>
                    <tr><td className="worker-table-item">Peter</td></tr>
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
