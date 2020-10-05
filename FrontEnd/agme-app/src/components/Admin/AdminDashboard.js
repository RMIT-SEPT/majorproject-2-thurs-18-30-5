import React, { Component } from 'react'
import { BrowserRouter as Router, Link, Route } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './AdminDashboard.css'
import axios from "axios";

export default class AdminDashboard extends Component {
  state = {
    workers: [],
    business: {}
  };
  constructor(props) {
    super(props);

    try {
      axios.get("http://localhost:8080/api/worker/business/" + this.props.location.state.user.business.id, { params: { isAdmin: false } })
        .then(res => {
          const workers = res.data;
          this.setState({workers: workers});
          this.state.workers = workers;
        })
    } catch (err) {

    }

    try {
      axios.get("http://localhost:8080/api/business/" + this.props.location.state.user.business.id)
        .then(res => {
          const business = res.data;
          this.setState({business: business});
          this.state.business = business;
        })
    } catch (err) {

    }

  }
  render() {
    return (
      <div>
        <AdminHeader user={this.props.location.state} />
          <div className="admin-img">
            <div className="container admin-dashboard">
              <div className="admin-title">Admin Dashboard</div>
            </div>

            <div className="container admin-functions">
              <div className="card biz-info">
                <div className="biz-name">{this.state.business.name}</div>
                <div className="biz-desc">{this.state.business.description}</div>
              </div>

              <div className="worker-table-scroll">
                <table className="table table-editable text-nowrap table-borderless table-hover worker-table">
                  <thead>
                    <tr><th className="worker-table-title">Workers</th></tr>
                  </thead>
                  <tbody>
                    {
                      this.state.workers.map(worker =>
                        worker.admin == false &&
                        <tr><td className="worker-table-item"><Link to={{
                          pathname: '/worker-page',
                          state: {
                            user: this.props.location.state.user, 
                            worker: worker
                          }
                        }}>{worker.user.firstName}</Link></td></tr>
                      )
                    }
                  </tbody>
                </table>
              </div>

              <div className="func-add-worker">
                <Link className="btn func-btn" to={{
                  pathname: '/add-worker',
                  state: {
                    user: this.props.location.state.user,
                    business: this.state.business
                  }
                }}>ADD New Worker</Link>
              </div>
              <div className="func-add-business">
                <Link className="btn func-btn" to={{
                  pathname: '/business',
                  state: {
                    user: this.props.location.state.user,
                    business: this.state.business
                  }
                }}>EDIT Business</Link>
              </div>
              <div className="func-upcoming">
                <Link className="btn func-btn" to={{
                  pathname: '/upcoming-booking',
                  state: this.props.location.state
                }}>Upcoming Bookings</Link>
              </div>
              <div className="func-past">
                <Link className="btn func-btn" to={{
                  pathname: '/past-booking',
                  state: this.props.location.state
                }}>Past Bookings</Link>
              </div>

            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
