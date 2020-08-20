import './Login-signup.css';
import React, { Component } from "react";
import Header from './Layout/Header';
import Footer from './Layout/Footer';

class SignUp extends Component {
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>
            <form>
              <h3>Sign Up</h3>

              <div className="form-group">
                <label>Name</label>
                <input type="text" className="form-control" placeholder="Enter name" />
              </div>

              <div className="form-group">
                <label>Email address</label>
                <input type="email" className="form-control" placeholder="Enter email" />
              </div>

              <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" placeholder="Enter username" />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="Enter password" />
              </div>

              <div className="form-group">
                <label>Password confirmation</label>
                <input type="password" className="form-control" placeholder="Enter password again" />
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
export default SignUp;
