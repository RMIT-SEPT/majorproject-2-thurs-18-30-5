import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../../Layout/js/AdminHeader'
import Footer from '../../Layout/js/Footer'
import axios from "axios";
import '../css/AddWorker.css'

export default class AddWorker extends Component {
  constructor(props) {
    super(props);

    this.state = {
      "username": "",
      "password": "",
      "secpass": "",
      "firstName": "",
      "lastName": "",
      "adminPass": ""
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onClick = this.onClick.bind(this);
  }
  onChange(e){
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newPerson = {
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          username: this.state.username,
          password: this.state.password
      }
      if (this.state.password == ""){
        window.alert("Password field cannot be empty; please try again.");
      }
      else if (this.state.password == this.state.secpass) {
        if (this.state.firstName != "") {
          if (this.state.lastName != "") {
            if (this.state.username.length < 6) {
              window.alert("The size of the username should be at least 6 characters; please try again.");
            }
            else {
              if (this.state.username.length > 15) {
                window.alert("The size of the username should be at most 15 characters; please try again.");
              }
              else {
                try {
                  await axios.get("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/worker/auth/username/" + this.props.location.state.user.user.username, { headers: {Authorization: this.props.location.state.auth}, params: { password: this.state.adminPass, isAdmin: true } });
                  
                  try {
                    await axios.post("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/customer", newPerson, { headers: {Authorization: this.props.location.state.auth} });
                    const res = await axios.get("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/customer/auth/" + this.state.username, { headers: {Authorization: this.props.location.state.auth}, params: { password: this.state.password } });

                    const newWorker = {
                      id: res.data.id,
                      user_id: res.data.id,
                      business: {
                        id: this.props.location.state.business.id
                      }
                    };

                    await axios.post("http://ec2-18-234-246-40.compute-1.amazonaws.com:8080/api/worker", newWorker, { headers: {Authorization: this.props.location.state.auth} });
                    this.props.history.push('/admin-dashboard', {user: this.props.location.state.user, auth: this.props.location.state.auth});

                  } catch (err) {
                    window.alert("Username already exists; please try again.");      
                  }
                }
                catch(err) {
                  window.alert("Incorrect admin password; please try again.");
                }
              }
            }
          }
          else{
            window.alert("Last name field cannot be empty; please try again.");
          }
        }
        else {
          window.alert("First name field cannot be empty; please try again.");
        }
      } 
      else {
        window.alert("The passwords do not match; please try again.");
      }
  }
  onClick(e) {
    window.location.reload(false);
  }
  render() {
    return (
      <div>
        <AdminHeader state={this.props.location.state} />
          <div className="admin-img">
            <div className="container profile">
              <form onSubmit={this.onSubmit}>
                <div className="profile-title">Add new worker</div>

                <div className="form-group row field-row">
                  <label for="inputusername" className="col-sm-2 col-form-label add-uname">Username</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input add-worker-field" 
                      id="inputusername" 
                      placeholder="Enter username" 
                      name="username"
                      spellCheck="false"
                      required="true"
                      value= {this.state.username}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputfirstname" className="col-sm-2 col-form-label add-fname">First name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input add-worker-field" 
                      id="inputfirstname" 
                      placeholder="Enter first name" 
                      name="firstName"
                      spellCheck="false"
                      required="true"
                      value= {this.state.firstName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputlastname" className="col-sm-2 col-form-label add-lname">Last name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input add-worker-field" 
                      id="inputlastname" 
                      placeholder="Enter last name" 
                      name="lastName"
                      spellCheck="false"
                      required="true"
                      value= {this.state.lastName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputpassword" className="col-sm-2 col-form-label add-pwd">Password</label>
                  <div className="col-sm-10">
                    <input 
                      type="password" 
                      className="form-control field-input add-worker-field" 
                      id="inputpassword" 
                      placeholder="Enter worker password" 
                      name="password"
                      spellCheck="false"
                      required="true"
                      value= {this.state.password}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputpassword" className="col-sm-2 col-form-label add-con-pwd">Confirm password</label>
                  <div className="col-sm-10">
                    <input 
                      type="password" 
                      className="form-control field-input add-worker-field" 
                      id="inputpassword" 
                      placeholder="Enter worker password again" 
                      name="secpass"
                      spellCheck="false"
                      required="true"
                      value= {this.state.secpass}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row add-worker-pwd">
                  <label for="inputpwd" className="col-sm-2 col-form-label admin-pwd-title">ADMIN password</label>
                  <div className="col-sm-10">
                    <input 
                      type="password" 
                      className="form-control field-input admin-pwd" 
                      id="inputpwd" 
                      placeholder="Enter ADMIN password to confirm changes" 
                      name="adminPass"
                      required="true"
                      value= {this.state.adminPass}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="container add-worker">
                  <button type="reset" className="btn cancel-changes" onClick={this.onClick}>cancel</button>
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
