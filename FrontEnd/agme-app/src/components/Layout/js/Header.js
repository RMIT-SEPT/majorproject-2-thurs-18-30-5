import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import '../css/Header.css'

export default class Header extends Component {
  render() {
    return (
      <div>
        <div className="App">
          <nav className="navbar navbar-expand-lg navbar-light fixed-top">
            <div className="container">

              <Link className="navbar-brand" to={"/"}>AGME</Link>
              <div className="collapse navbar-collapse" id="navbarTogglerDemo02">

                <ul className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link className="nav-link" to={"/sign-in"}>Login</Link>
                  </li>

                  <li className="nav-item">
                    <Link className="nav-link" to={"/sign-up"}>Sign up</Link>
                  </li>
                </ul>

              </div>
            </div>
          </nav>
        </div>
      </div>
    )
  }
}