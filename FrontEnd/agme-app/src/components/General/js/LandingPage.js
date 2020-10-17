import React, { Component } from 'react'
import Footer from '../../Layout/js/Footer'
import Header from '../../Layout/js/Header'
import About_Contact from '../../Layout/js/About_Contact'
import '../css/LandingPage.css'

export default class LandingPage extends Component {
  render() {
    return (
      <div>
        <Header/>
          <div className="landing-img">
            <About_Contact/>
          </div>
        <Footer/>
      </div>
    )
  }
}
