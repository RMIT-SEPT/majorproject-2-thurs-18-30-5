import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import CustomerHeader from './Layout/CustomerHeader'
import Footer from './Layout/Footer'
import './CustomerProfile.css'

export default class CustomerProfile extends Component {
  render() {
    return (
      <div>
        <CustomerHeader/>
          <div className="cust-img">
            <div className="container profile">
              <form>
                <h1 className="profile-title">Profile</h1>

                <div className="form-group row field-row">
                  <label for="inputname" className="col-sm-2 col-form-label name">Name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputname" placeholder="John" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputusername" className="col-sm-2 col-form-label username">Username</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputusername" placeholder="johnsmith123" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputpassword" className="col-sm-2 col-form-label pwd">Password</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputpassword" placeholder="12345678" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputpassword" className="col-sm-2 col-form-label confirm-pwd">Confirm password</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputpassword" placeholder="12345678" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputemail" className="col-sm-2 col-form-label email">E-mail</label>
                  <div className="col-sm-10">
                    <input type="email" className="form-control field-input" id="inputemail" placeholder="johnsmith123@mail.com" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputcontactno" className="col-sm-2 col-form-label contact-no">Contact number</label>
                  <div className="col-sm-10">
                    <input type="tel" className="form-control field-input" id="inputcontactno" placeholder="04 9999 9999" />
                  </div>
                </div>

                <div className="container edit-action">
                  <button type="reset" className="btn cancel-changes">cancel</button>
                  <button type="submit" className="btn save-changes">Save changes</button>
                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
