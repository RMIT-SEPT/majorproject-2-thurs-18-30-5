import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import CustomerHeader from '../Layout/CustomerHeader'
import Footer from '../Layout/Footer'
import './CustomerProfile.css'
import axios from "axios";

export default class CustomerProfile extends Component {
  constructor() {
    super();

    this.state = {
      "address": "",
      "password": "",
      "secpass": "",
      "firstName": "",
      "lastName": ""
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  onChange(e){
    console.log(this.props);
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newPerson = {
          id: this.props.location.state.user.id,
          username: this.props.location.state.user.username,
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          address: this.state.address,
          password: this.state.password,
          bookings: this.props.location.state.user.bookings
      }

      console.log(newPerson);

      if (this.state.password == this.state.secpass) {
        if (this.state.password == "") {
          newPerson.password = this.props.location.state.user.password;
        }
        if (this.state.firstName == "") {
          newPerson.firstName = this.props.location.state.user.firstName;
        }
        if (this.state.lastName == "") {
          newPerson.lastName = this.props.location.state.user.lastName;
        }
        if (this.state.address == "") {
          newPerson.address = this.props.location.state.user.address;
        }
        try {
          const res = await axios.post("http://localhost:8080/api/customer", newPerson);
          this.props.history.push('/customer-profile', {user: newPerson});
        } catch (err) {
          console.log(err);
        }
      }
      window.location.reload(false);
  }
  onClick(e){
    window.location.reload(false);
  }
  render() {
    return (
      <div>
        <CustomerHeader user={this.props.location.state} />
          <div className="cust-img">
            <div className="container profile">
              <form onSubmit={this.onSubmit}>
                <h1 className="profile-title">Profile</h1>

                <div className="form-group row field-row">
                  <label htmlFor="inputusername" className="col-sm-2 col-form-label username">Username</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputusername" value={this.props.location.state.user.username} readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputname" className="col-sm-2 col-form-label name">First name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputname" placeholder={this.props.location.state.user.firstName} name="firstName"
                    value= {this.state.firstName}
                    onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputemail" className="col-sm-2 col-form-label email">Last name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputemail" placeholder={this.props.location.state.user.lastName} name="lastName"
                    value= {this.state.lastName}
                    onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputpassword" className="col-sm-2 col-form-label confirm-pwd">New password</label>
                  <div className="col-sm-10">
                    <input type="password" className="form-control field-input" id="inputpassword" placeholder={this.props.location.state.user.password} name="password"
                    value= {this.state.password}
                    onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputpassword" className="col-sm-2 col-form-label confirm-pwd">Confirm new password</label>
                  <div className="col-sm-10">
                    <input type="password" className="form-control field-input" id="inputpassword2" placeholder={this.props.location.state.user.password} name="secpass"
                    value= {this.state.secpass}
                    onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputcontactno" className="col-sm-2 col-form-label contact-no">Address</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputcontactno" placeholder={this.props.location.state.user.address} name="address"
                    value= {this.state.address}
                    onChange = {this.onChange} />
                  </div>
                </div>

                <div className="container edit-action">
                  <button type="reset" className="btn cancel-changes" onClick={this.onClick}>Cancel</button>
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