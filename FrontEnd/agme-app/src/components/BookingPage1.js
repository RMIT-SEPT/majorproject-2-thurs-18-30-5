import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';

const services = ['Service-1', 'Service-2', 'Service-3'];

const serviceList = services.map(service =>
    // expression goes here:
    <div><div className="service-div"><Link to={{
      pathname: '/bookingPage2',
      state: {
        service: service
      }
    }}><button className="service-btn" type="submit">{service}</button></Link></div><br/><br/></div>
  );

export default class BookingPage1 extends Component {
  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
                <h3>Choose a service</h3>
                <br/>
                <br/>
                {serviceList}
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
