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
              <div className="card biz-info">
                <div className="biz-name">Pandemic Hair</div>
                <div className="biz-desc">
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                  Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
                  Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                </div>
              </div>

              <div className="worker-table-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover worker-table">
                  <thead>
                    <tr><th className="worker-table-title">Workers</th></tr>
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

              <div className="func-add-worker">
                <Link className="btn func-btn" to={"/add-worker"}>ADD new worker</Link>
              </div>
              <div className="func-add-business">
                <Link className="btn func-btn" to={"/business"}>EDIT Business</Link>
              </div>
              <div className="func-upcoming">
                <Link className="btn func-btn" to={"/upcoming-booking"}>Upcoming Bookings</Link>
              </div>
              <div className="func-past">
                <Link className="btn func-btn" to={"/past-booking"}>Past Bookings</Link>
              </div>

            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
