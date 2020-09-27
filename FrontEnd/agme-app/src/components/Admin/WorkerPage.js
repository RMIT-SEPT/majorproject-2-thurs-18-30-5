import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import DatePicker from "react-datepicker";
import './WorkerPage.css'
import addDays from "date-fns/subDays";
import "react-datepicker/dist/react-datepicker.css";

export default class AddWorker extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container profile">
              <form>
                <div className="profile-title">Edit worker</div>

                <div className="form-group row field-row">
                  <label for="inputusername" className="col-sm-2 col-form-label username">Username</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputusername" placeholder="Enter username" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputfirstname" className="col-sm-2 col-form-label name">First name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputfirstname" placeholder="Enter first name" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputlastname" className="col-sm-2 col-form-label username">Last name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputlastname" placeholder="Enter last name" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputaddress" className="col-sm-2 col-form-label email">Address</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputaddress" placeholder="Enter address" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputhr" className="col-sm-2 col-form-label job">Working hour</label>
                  <div>
                    <DatePicker 
                      className="date-time work-day" 
                      minDate={new Date()}
                      placeholderText="Select a day" />
                  </div>
                    <button className="btn check-hour">check time</button>
                  <div>
                    
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputhr" className="col-sm-2 col-form-label job"></label>
                  <div>
                    <DatePicker className="date-time select-time start-time" placeholderText="start time" />
                  </div>
                  <div>
                    <DatePicker className="date-time select-time end-time" placeholderText="end time" />
                  </div>
                </div>

                <div className="form-group row field-row add-worker-pwd">
                  <label for="inputpwd" className="col-sm-2 col-form-label pwd">ADMIN password</label>
                  <div className="col-sm-10">
                  <input type="password" className="form-control field-input pwd" id="inputpwd" placeholder="Enter ADMIN password to confirm changes" />
                  </div>
                </div>

                <div className="container edit-add-worker">
                  <button type="reset" className="btn cancel-changes">cancel</button>
                  <button type="submit" className="btn save-changes">Submit</button>
                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
