import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import CustomerHeader from '../../Layout/js/CustomerHeader'
import Footer from '../../Layout/js/Footer'
import '../css/CustomerProfile.css'
import axios from "axios";

export default class CustomerProfile extends Component {
  constructor() {
    super();

    this.state = {
      "address": "",
      "password": "",
      "firstName": "",
      "lastName": ""
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  onChange(e){
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newPerson = {
          id: this.props.location.state.user.id,
          username: this.props.location.state.user.username,
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          password: this.state.password,
          address: this.state.address
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
        console.log(this.props.location.state.auth);
        console.log(newPerson);
        await axios.put("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/customer", newPerson, { headers: {Authorization: this.props.location.state.auth} });
        this.props.history.push('/customer-profile', {user: newPerson, auth: this.props.location.state.auth});
      } catch (err) {
        window.alert("Incorrect password; please try again.");
      }
      window.location.reload(false);
  }
  onClick(e){
    window.location.reload(false);
  }
  render() {
    return (
      <div>
        <CustomerHeader state={this.props.location.state} />
          <div className="cust-img">
            <div className="container profile">
              <form onSubmit={this.onSubmit}>
                <h1 className="profile-title">Profile</h1>

                <div className="form-group row field-row">
                  <label htmlFor="inputusername" className="col-sm-2 col-form-label cus-pro-uname">Username</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputusername" 
                      value={this.props.location.state.user.username} 
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputfname" className="col-sm-2 col-form-label cus-pro-fname">First name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputfname" 
                      placeholder={this.props.location.state.user.firstName} 
                      name="firstName"
                      value= {this.state.firstName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputlname" className="col-sm-2 col-form-label cus-pro-lname">Last name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputlname" 
                      placeholder={this.props.location.state.user.lastName} 
                      name="lastName"
                      value= {this.state.lastName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputaddress" className="col-sm-2 col-form-label cus-pro-addr">Address</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input" 
                      id="inputaddress" 
                      placeholder={this.props.location.state.user.address} 
                      name="address"
                      value= {this.state.address}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label htmlFor="inputpwd" className="col-sm-2 col-form-label cus-pwd-label cus-pro-pwd">Password</label>
                  <div className="col-sm-10">
                    <input 
                      type="password" 
                      className="form-control field-input cus-pwd" 
                      id="inputpwd" 
                      placeholder="Enter password to confirm changes" 
                      name="password"
                      required="true"
                      value= {this.state.password}
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
