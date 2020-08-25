import './Login-signup.css';
import React, { Component } from "react";
import Header from './Layout/Header';
import Footer from './Layout/Footer';
import { BrowserRouter as Router, Link } from "react-router-dom"

class Login extends Component {
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
              <h3>Login</h3>
              <br/>

              <div className="form-group">
                <label>Email address / Username</label>
                <input type="email" className="form-control" placeholder="Enter email / username" />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="Enter password" />
              </div>

              <button type="submit" className="btn btn-primary btn-block">Submit</button> 

              <Link className="nav-link" to={"/forgotPassword"}>Forgot password?</Link>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    );
  }
}
export default Login;
