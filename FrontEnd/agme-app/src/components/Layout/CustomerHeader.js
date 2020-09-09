import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './Header.css'

export default class CustomerHeader extends Component {
	render() {
    return (
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={"/customer-dashboard"}>AGME</Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">

              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link cust-profile" to={"/customer-profile"}>Profile</Link>
                </li>

                <li className="nav-item">
                  <Link className="nav-link logout" to={"/"}>Logout</Link>
                </li>
              </ul>

            </div>
          </div>
        </nav>
      </div>
    )
  }
}
