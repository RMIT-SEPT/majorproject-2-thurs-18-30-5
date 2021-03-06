import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import WorkerHeader from '../../Layout/js/WorkerHeader'
import Footer from '../../Layout/js/Footer'
import axios from "axios";

export default class WorkerProfile extends Component {
  constructor() {
    super();
  }
  
  render() {
    return (
      <div>
        <WorkerHeader state={this.props.location.state} />
          <div className="cust-img">
            <div className="container profile">
              <form>
                <h1 className="profile-title">Profile</h1>

                <div className="form-group row field-row">
                  <label htmlFor="inputusername" className="col-sm-2 col-form-label work-pf-uname">Username</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputusername" 
                      value={this.props.location.state.user.user.username} 
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputbizname" className="col-sm-2 col-form-label work-pf-biz">Business</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputbizname" 
                      value={this.props.location.state.user.business.name} 
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputfname" className="col-sm-2 col-form-label work-pf-fname">First name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputfname" 
                      placeholder={this.props.location.state.user.user.firstName} 
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputlname" className="col-sm-2 col-form-label work-pf-lname">Last name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputlname" 
                      placeholder={this.props.location.state.user.user.lastName} 
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputaddress" className="col-sm-2 col-form-label work-pf-addr">Address</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputaddress" 
                      placeholder={this.props.location.state.user.user.address} 
                      readOnly />
                  </div>
                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
