import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import WorkerHeader from '../Layout/WorkerHeader'
import Footer from '../Layout/Footer'
import axios from "axios";

export default class WorkerProfile extends Component {
  constructor() {
    super();
  }
  
  render() {
    return (
      <div>
        <WorkerHeader user={this.props.location.state} />
          <div className="cust-img">
            <div className="container profile">
              <form>
                <h1 className="profile-title">Profile</h1>

                <div className="form-group row field-row">
                  <label htmlFor="inputusername" className="col-sm-2 col-form-label">Username</label>
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
                  <label htmlFor="inputfname" className="col-sm-2 col-form-label">First name</label>
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
                  <label htmlFor="inputlname" className="col-sm-2 col-form-label">Last name</label>
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
                  <label htmlFor="inputaddress" className="col-sm-2 col-form-label">Address</label>
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
