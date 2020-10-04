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

    axios.get("http://localhost:8080/api/business/all")
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

              <div className="service-table-scroll">
                <table className="table text-nowrap table-borderless service-table">
                  <tbody>
                    {
                      this.state.businesses.map(business => 
                      <tr>
                        <div className="service-div">
                          <Link to={{
                            pathname: '/bookingPage2',
                            state: {user: this.props.location.state.user,
                                    service: business}
                          }}>
                            <button className="booking-btn service-btn" type="submit">{business.name}</button>
                          </Link>
                        <br/><br/>
                        </div>
                      </tr>)
                    }
                  </tbody>
                </table>
              </div>
              <br/><br/>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
