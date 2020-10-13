import React, { Component } from 'react'
import '../css/About_Contact.css'

export default class About_Contact extends Component {
  render() {
    return (
        <div className="container about-contact-info">
          <div className="about-title">About</div>
          <div className="about-content">
            Welcome to AGME company's Online Appointment Booking System. 
            This is a web application booking system that can be used by any service provider, 
            allowing customers to book 24/7 a time slot for an appointment for a specific service with an assigned service provider. 
            You can use the application for any business, e.g. a hairdresser, a gym, a dentist and so on.  
          </div>

          <div className="contact-title">Contact us</div>
          <div className="contact-content">
            You can contact us via our email address or phone number.
            <br/>
            Email-address: agme@agmecompany.com
            <br/>
            Phone-number: +61-123456789
          </div>
          <br/>
        </div>
    )
  }
}
