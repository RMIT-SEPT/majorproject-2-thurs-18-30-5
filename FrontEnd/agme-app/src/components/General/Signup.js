import './Login-signup.css';
import React, { Component } from "react";
import Header from '../Layout/Header';
import Footer from '../Layout/Footer';
import axios from "axios";

class SignUp extends Component {
  constructor() {
    super();

    this.state = {
      "username": "",
      "password": "",
      "secpass": "",
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
                  const res = await axios.post("http://Backend-Dev-dev.us-east-1.elasticbeanstalk.com/customer", newPerson);
                  this.props.history.push('/sign-in');
                } catch (err) {
                  window.alert("Username already exists; please try again.");      
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
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>
              <form onSubmit={this.onSubmit}>
                <h3 className="signup">Sign Up</h3>

                <div className="form-group">
                  <label className="name">First name</label>
                  <input type="text" className="form-control form-input" placeholder="Enter first name" name="firstName"
                  value= {this.state.firstName}
                  onChange = {this.onChange} />
                </div>

                <div className="form-group">
                  <label className="name">Last name</label>
                  <input type="text" className="form-control form-input" placeholder="Enter last name" name="lastName"
                  value= {this.state.lastName}
                  onChange = {this.onChange} />
                </div>

                <div className="form-group">
                  <label className="username">Username</label>
                  <input type="text" className="form-control form-input" placeholder="Enter username" name="username"
                  value= {this.state.username}
                  onChange = {this.onChange} />
                </div>

                <div className="form-group">
                  <label className="password">Password</label>
                  <input type="password" className="form-control form-input" placeholder="Enter password" name="password"
                  value= {this.state.password}
                  onChange = {this.onChange} />
                </div>

                <div className="form-group">
                  <label className="pwd-confirm">Password confirmation</label>
                  <input type="password" className="form-control form-input" placeholder="Enter password again" name="secpass"
                  value= {this.state.secpass}
                  onChange = {this.onChange} />
                </div>

                <div className="submit-button">
                  <button type="submit" className="btn submit">Submit</button>
                </div>
              </form>
          </div>
          <Footer/>
        </div>
      </div>
    );
  }
}
export default SignUp;
