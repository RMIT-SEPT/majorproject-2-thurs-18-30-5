import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import './Business.css'

export default class Business extends Component {
  render() {
    return (
      <div>
        <AdminHeader/>
          <div className="admin-img">
            <div className="container profile">
              <form>
                <div className="profile-title">Edit business</div>

                <div className="form-group row field-row">
                  <label for="inputbizname" className="col-sm-2 col-form-label username">Business name</label>
                  <div className="col-sm-10">
                    <input type="text" className="form-control field-input" id="inputbizname" placeholder="Enter business name" />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputbizdesc" className="col-sm-2 col-form-label name">Description</label>
                  <div className="col-sm-10">
                    <textarea type="text" className="form-control field-input" id="inputbizdesc" placeholder="Enter description" rows="10" />
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