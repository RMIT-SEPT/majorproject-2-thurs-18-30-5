import React, { Component } from 'react'
import './Footer.css'

export default class Footer extends Component {
  render() {
    return (
      <div>
        <footer className="footer">
          <div className="aboutcontact">
            <span>About & contact us</span>
          </div>
          <div className="copyright">
            <span>&copy; 2020 AGME</span>
          </div>
        </footer>
      </div>
    )
  }
}
