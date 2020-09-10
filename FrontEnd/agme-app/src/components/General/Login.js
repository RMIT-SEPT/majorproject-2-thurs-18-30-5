import './Login-signup.css';
import React, { Component } from "react";
import Header from '../Layout/Header';
import Footer from '../Layout/Footer';
import { BrowserRouter as Router, Link } from "react-router-dom"

class Login extends Component {
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
              <h3 className="login">Login</h3>
              <br/>

              <div className="form-group">
                <label className="email-username">Email / username</label>
                <input type="text" className="form-control form-input" placeholder="Enter email / username" />
              </div>

              <div className="form-group">
                <label className="pwd">Password</label>
                <input type="password" className="form-control form-input" placeholder="Enter password" />
              </div>

              <div className="submit-button">
                <Link className="btn submit" to={"/customer-dashboard"}>Submit</Link> 
              </div>

              <div className="forgot-pwd">
                <Link className="forgot-pwd-option" to={"/forgotPassword"}>Forgot password?</Link>
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
