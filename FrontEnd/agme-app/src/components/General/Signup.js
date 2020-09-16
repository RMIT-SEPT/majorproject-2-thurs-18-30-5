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
    console.log(this.props);
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

      console.log(newPerson);

      if (this.state.password == this.state.secpass) {
        try {
          const res = await axios.post("http://localhost:8080/api/customer", newPerson);
          // history.push("/sign-up");
        } catch (err) {
        
        }
      }
      window.location.reload(false);
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