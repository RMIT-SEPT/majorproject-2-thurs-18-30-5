import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import './LandingPage.css'

export default class LandingPage extends Component {
  render() {
    return (
      <div>
        <Header/>
        <div className="img">
            <div className="about-title">About</div>
            <div className="about-content">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
            </div>
            <div className="contact-title">Contact us</div>
            <div className="contact-content">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
            </div>
        </div>
        <Footer/>
      </div>
    )
  }
}
