import React, { Component } from 'react';
import { BrowserRouter as Router, Link } from "react-router-dom";
import WorkerHeader from '../Layout/WorkerHeader';
import Footer from '../Layout/Footer';
import './WorkerJobs.css'
import axios from "axios";

export default class WorkerJobs extends Component {
  render() {
    return (
      <div>
        <WorkerHeader/>
          <div className="cust-img">
            <div className="container customer-title">
              <div className="welcome-msg">Pending Jobs</div>
              
              {
                <div className="pending-jobs-scroll">
                  <table className="table table-editable text-nowrap table-borderless table-hover booking-table job-table">
                    <thead className="book-summary-title">
                      <tr>
                        <th scope="col" width="5%" className="book-header left-title"></th>
                        <th scope="col" width="20%" className="book-header mid-title">Start time</th>
                        <th scope="col" width="20%" className="book-header mid-title">End time</th>
                        <th scope="col" width="21%" className="book-header mid-title">Customer</th>
                        <th scope="col" width="16%" className="book-header mid-title"></th>
                        <th scope="col" width="18%" className="book-header right-title"></th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td></td>
                        <td>2020-10-03 14:00</td>
                        <td>2020-10-03 15:00</td>
                        <td>alexander</td>
                        <td>
                          <button type="button" className="btn job-action accept-job">accept</button>
                        </td>
                        <td>
                          <button type="button" className="btn job-action decline-job">decline</button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              }
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
