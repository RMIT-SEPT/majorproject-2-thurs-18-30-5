import '../css/Login-signup.css';
import React, { Component } from "react";
import Header from '../../Layout/js/Header';
import Footer from '../../Layout/js/Footer';
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
        const res = await axios.get("http://localhost:8080/api/worker/auth/username/" + this.state.username, { params: { password: this.state.password, isAdmin: true } });
        const res2 = await axios.get("http://localhost:8080/api/worker/" + res.data.id, { headers: { Authorization: res.data.tokenType + " " + res.data.accessToken } });
        this.person = res2.data;
        this.props.history.push('/admin-dashboard', {user: this.person, auth: res.data.tokenType + " " + res.data.accessToken});
      } catch (err) {
          try {
            const res = await axios.get("http://localhost:8080/api/worker/auth/username/" + this.state.username, { params: { password: this.state.password, isAdmin: false } });
            const res2 = await axios.get("http://localhost:8080/api/worker/" + res.data.id, { headers: { Authorization: res.data.tokenType + " " + res.data.accessToken } });
            this.person = res2.data;
            this.props.history.push('/worker-dashboard', {user: this.person, auth: res.data.tokenType + " " + res.data.accessToken});
          } catch (err2) {
            try {
              const res = await axios.get("http://localhost:8080/api/customer/auth/" + this.state.username, { params: { password: this.state.password } });
              const res2 = await axios.get("http://localhost:8080/api/customer/" + res.data.id, { headers: { Authorization: res.data.tokenType + " " + res.data.accessToken } });
              this.person = res2.data;
              this.props.history.push('/customer-dashboard', {user: this.person, auth: res.data.tokenType + " " + res.data.accessToken});
            } catch (err3) {
              window.location.reload(false);
            }
          }
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
                <label className="login-uname">Username</label>
                <input 
                  type="text" 
                  className="form-control form-input" 
                  placeholder="Enter username" 
                  name="username"
                  spellCheck="false"
                  required="true"
                  value= {this.state.username}
                  onChange = {this.onChange} />
              </div>

              <div className="form-group">
                <label className="login-pwd">Password</label>
                <input 
                  type="password" 
                  className="form-control form-input"
                  placeholder="Enter password" 
                  name="password"
                  spellCheck="false"
                  required="true"
                  value= {this.state.password}
                  onChange = {this.onChange} />
              </div>

              <div className="submit-button">
                  <button type="submit" className="btn submit">Submit</button>
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
