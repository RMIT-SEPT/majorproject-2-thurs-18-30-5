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
              <h3>Forgot Password?</h3>
              <br/>

              <div className="form-group">
                <label>Email address</label>
                <input type="email" className="form-control form-input" placeholder="Enter email" />
              </div>

              <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control form-input" placeholder="Enter username" />
              </div>

              <div className="form-group">
                <label>New password</label>
                <input type="password" className="form-control form-input" placeholder="Enter password" />
              </div>

              <div className="form-group">
                <label>New password confirmation</label>
                <input type="password" className="form-control form-input" placeholder="Enter password again" />
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
