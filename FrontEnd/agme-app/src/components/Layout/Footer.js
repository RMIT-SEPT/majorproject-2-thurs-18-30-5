import React, { Component } from 'react'
import './Footer.css'
import { BrowserRouter as Router, Link } from "react-router-dom"

export default class Footer extends Component {
  render() {
    return (
      <div>
        <Router>
          <footer className="footer">
            <Link className="aboutcontact" to={"/"}>About & contact us</Link>
            
            <div className="copyright">
              <span>&copy; 2020 AGME</span>
            </div>

          </footer>
        </Router>
      </div>
    )
  }
}
