import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import DatePicker from "react-datepicker";
import './WorkerPage.css'
import "react-datepicker/dist/react-datepicker.css";

export default class AddWorker extends Component {
  constructor(props) {
    super(props);

    console.log(this.props.location.state);
  }
  render() {
    return (
      <div>
        <AdminHeader user={this.props.location.state} />
          <div className="admin-img">
            <div className="container profile">
              <form>
                <div className="profile-title">Worker data</div>

                <div className="form-group row field-row">
                  <label for="inputusername" className="col-sm-2 col-form-label username">Username</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputusername" 
                      value={this.props.location.state.worker.user.username}
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputfname" className="col-sm-2 col-form-label fname">First name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputfname" 
                      placeholder="Enter first name" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputlname" className="col-sm-2 col-form-label lname">Last name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputlname" 
                      placeholder="Enter last name" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputaddress" className="col-sm-2 col-form-label address">Address</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputaddress" 
                      placeholder="Enter address" />
                  </div>
                </div>

                <div className="form-group row field-row worker-admin-pwd">
                  <label for="inputpwd" className="col-sm-2 col-form-label admin-pwd">ADMIN password</label>
                  <div className="col-sm-10">
                  <input 
                    type="password" 
                    className="form-control field-input admin-pwd" 
                    id="inputpwd" 
                    placeholder="Enter ADMIN password to confirm changes" />
                  </div>
                </div>

                <div className="card work-info">
                  <div className="select-date">
                    <DatePicker 
                      className="date-time work-day" 
                      minDate={new Date()}
                      placeholderText="Select a day" />
                  </div>

                  <div className="check-time-btn">
                    <button className="btn check-time">check time</button>
                  </div>
                
                  <div className="card-title working-hour-title">Working hour</div>

                  <div className="select-start-time">
                    <DatePicker className="date-time select-time" placeholderText="start time" />
                  </div>

                  <div className="select-end-time">
                    <DatePicker className="date-time select-time" placeholderText="end time" />
                  </div>

                  <div className="change-hour-btn">
                    <button className="btn check-time change-time">change time</button>
                  </div>

                  <div className="card-title availability-title">Availability</div>

                  <div className="avail-table-scroll">
                    <table className="table table-editable text-nowrap table-borderless table-hover avail-table">
                      <thead className="avail-title">
                        <tr>
                          <th scope="col" className="avail-header avail-from">from</th>
                          <th scope="col" className="avail-header avail-to">to</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>10.00am</td>
                          <td>11.00am</td>
                        </tr>
                        <tr>
                          <td>12.30pm</td>
                          <td>2.30pm</td>
                        </tr>
                        <tr>
                          <td>4.00pm</td>
                          <td>6.00pm</td>
                        </tr>
                        <tr>
                          <td>10.00am</td>
                          <td>11.00am</td>
                        </tr>
                        <tr>
                          <td>12.30pm</td>
                          <td>2.30pm</td>
                        </tr>
                        <tr>
                          <td>4.00pm</td>
                          <td>6.00pm</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>

                <div className="container edit-worker">
                  <button type="reset" className="btn cancel-changes">cancel</button>
                  <button type="submit" className="btn save-changes worker-save">Submit</button>
                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
