import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './Business.css'
import axios from "axios";

export default class Business extends Component {
  constructor(props) {
    super(props);

    this.state = {
      "name": "",
      "description": "",
      "password": ""
    }
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }
  onChange(e){
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newBusiness = {
          name: this.props.location.state.user.business.name,
          description: this.props.location.state.user.business.description
      }

      if (this.state.name != "") {
        newBusiness.name = this.state.name;
      }
      if (this.state.description != "") {
        newBusiness.description = this.state.description;
      }
      
      try {
        await axios.get("http://localhost:8080/api/worker/auth/username/" + this.props.location.state.user.user.username, { params: { password: this.state.password, isAdmin: true } });
        
        try {
          const res = await axios.put("http://localhost:8080/api/business/" + this.props.location.state.user.business.id, newBusiness);
          this.props.history.push('/admin-dashboard', {user: this.props.location.state.user});
        } catch (err) {
          window.alert("Invalid info; please try again.");
        }
      }
      catch (err) {
        window.alert("Incorrect admin password; please try again.");
      }
      window.location.reload(false);
  }
  render() {
    return (
      <div>
        <AdminHeader user={this.props.location.state} />
          <div className="admin-img">
            <div className="container profile">
              <form onSubmit={this.onSubmit}>
                <div className="profile-title">Edit business</div>

                <div className="form-group row field-row">
                  <label for="inputbizname" className="col-sm-2 col-form-label username">Business name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputbizname" value={this.props.location.state.user.business.name} readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputbizdesc" className="col-sm-2 col-form-label name">Description</label>
                  <div className="col-sm-10">
                    <textarea 
                      type="text" 
                      className="form-control field-input" 
                      id="inputbizdesc" 
                      placeholder={this.props.location.state.user.business.description} 
                      rows="10"
                      maxLength="380" name="description"
                      value= {this.state.description}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row biz-pwd">
                  <label for="inputpwd" className="col-sm-2 col-form-label admin-pwd-title">ADMIN Password</label>
                  <div className="col-sm-10">
                  <input type="password" className="form-control field-input admin-pwd" id="inputpwd" placeholder="Enter ADMIN password to confirm changes" name="password"
                  value= {this.state.password}
                  onChange = {this.onChange} />
                  </div>
                </div>

                <div className="container edit-business">
                  <button type="reset" className="btn cancel-changes">cancel</button>
                  <button type="submit" className="btn save-changes">Save changes</button>
                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
