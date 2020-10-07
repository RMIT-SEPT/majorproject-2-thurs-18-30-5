import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './Header.css'

export default class WorkerHeader extends Component {
	render() {
    return (
      <div>
        <div className="App">
          <nav className="navbar navbar-expand-lg navbar-light fixed-top">
            <div className="container">
							<Link className="navbar-brand" to={{
                pathname: '/worker-dashboard',
                state: this.props.user
              }}>AGME</Link>
              <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link className="nav-link" to={{
                      pathname: '/worker-profile',
                      state: this.props.user
                    }}>Profile</Link>
                  </li>

                  <li className="nav-item">
                    <Link className="nav-link" to={"/"}>Logout</Link>
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