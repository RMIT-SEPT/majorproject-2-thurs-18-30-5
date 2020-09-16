import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'

export default class AddWorker extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container profile">
              <form>
                <div className="profile-title">Add new worker</div>

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
                  <label for="inputpassword" className="col-sm-2 col-form-label pwd">Password</label>
                  <div className="col-sm-10">
                    <input type="password" className="form-control field-input" id="inputpassword" placeholder="Enter password" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputpassword" className="col-sm-2 col-form-label confirm-pwd">Confirm password</label>
                  <div className="col-sm-10">
                    <input type="password" className="form-control field-input" id="inputpassword" placeholder="Enter password again" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputaddress" className="col-sm-2 col-form-label email">Address</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputaddress" placeholder="Enter address" />
                  </div>
                </div>

                <div className="container edit-action">
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
