import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom";
import './Header.css'

export default class Header extends Component {
  render() {
    return (
      <div>
        <Router>
          <div className="App">
            <nav className="navbar navbar-expand-lg navbar-light fixed-top">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link" to={"/sign-in"}>Login</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={"/sign-up"}>Sign up</Link>
                </li>
              </ul>
            </nav>
          </div>
        </Router>
      </div>
    )
  }
}
