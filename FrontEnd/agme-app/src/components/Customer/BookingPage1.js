import React, { Component } from 'react'
import Footer from '../Layout/Footer'
import CustomerHeader from '../Layout/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';
import axios from "axios";

export default class BookingPage1 extends Component {
  state = {
    businesses: []
  }
  constructor() {
    super();

    axios.get("http://Backend-Dev-dev.us-east-1.elasticbeanstalk.com/api/business/all")
      .then(res => {
        const businesses = res.data;
        this.setState({ businesses });
      })
  }
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <CustomerHeader user={this.props.location.state} />

            <form className="service-form">
                <h3 className="service-h3">Choose a service</h3>
                <hr/>
                <br/>
                <br/>
                {this.state.businesses.map(business => <div><div className="service-div"><Link to={{
                  pathname: '/bookingPage2',
                  state: {user: this.props.location.state.user,
                          service: business}
                }}><button className="service-btn" type="submit">{business.name}</button></Link></div><br/><br/></div>)}
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
