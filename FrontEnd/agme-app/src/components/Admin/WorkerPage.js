import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './WorkerPage.css'

export default class WorkerPage extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container worker-page">
              <div className="worker-title">Worker information</div>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
