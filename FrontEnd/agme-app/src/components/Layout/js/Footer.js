import React, { Component } from 'react'
import '../css/Footer.css'
import { BrowserRouter as Router, Link } from "react-router-dom"

export default class Footer extends Component {
  render() {
    return (
      <div>
        <footer className="footer">
          <Link className="aboutcontact" to={"/"}>About & contact us</Link>
          <div className="copyright">&copy; 2020 AGME</div>
        </footer>
      </div>
    )
  }
}
