import './Login-signup.css';
import React, { Component } from "react";
import Header from '../Layout/Header';
import Footer from '../Layout/Footer';
import { BrowserRouter as Router, Link } from "react-router-dom"
import axios from "axios";

class Login extends Component {
  constructor() {
    super();

    this.state = {
      "username": "",
      "password": "",
    }
    this.person = {}
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  onChange(e){
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newPerson = {
          username: this.state.username,
          password: this.state.password
      }

      try {
        const res = await axios.get("http://sept-backend.us-east-1.elasticbeanstalk.com/api/customer/auth/" + this.state.username, { params: { password: this.state.password } });
        this.person = res.data;
        this.props.history.push('/customer-dashboard', {user: this.person});
      } catch (err) {
        window.location.reload(false);
      }
  }
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form onSubmit={this.onSubmit}>
              <h3 className="login">Login</h3>
              <br/>

              <div className="form-group">
                <label className="email-username">Username</label>
                <input type="text" className="form-control form-input" placeholder="Enter username" name="username"
                value= {this.state.username}
                onChange = {this.onChange} />
              </div>

              <div className="form-group">
                <label className="pwd">Password</label>
                <input type="password" className="form-control form-input" placeholder="Enter password" name="password"
                value= {this.state.password}
                onChange = {this.onChange} />
              </div>

              <div className="submit-button">
                
                  <button className="service-btn" type="submit">Submit</button>
                
              </div>

              <div className="forgot-pwd">
                <Link className="forgot-pwd-option" to={"/sign-in"}>Forgot password?</Link>
              </div>
              
            </form>
          </div>
          <Footer/>
        </div>
      </div>
    );
  }
}
export default Login;
