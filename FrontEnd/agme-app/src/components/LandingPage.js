import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import About_Contact from './Layout/About_Contact'
import './LandingPage.css'

export default class LandingPage extends Component {
  render() {
    return (
      <div>
        <Header/>
          <div className="img">
            <About_Contact/>
          </div>
        <Footer/>
      </div>
    )
  }
}
