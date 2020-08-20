import './Login-signup.css';
import React, { Component } from "react";
import Header from './Layout/Header';
import Footer from './Layout/Footer';

class Login extends Component {
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
              <h3>Login</h3>

              <div className="form-group">
                <label>Email address</label>
                <input type="email" className="form-control" placeholder="Enter email" />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="Enter password" />
              </div>

              <div className="form-group">
                <div className="custom-control custom-checkbox">
                    <input type="checkbox" className="custom-control-input" id="customCheck1" />
                    <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                </div>
              </div>

              <button type="submit" className="btn btn-primary btn-block">Submit</button> 
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    );
  }
}
export default Login;
