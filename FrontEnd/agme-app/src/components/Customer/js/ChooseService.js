import React, { Component } from 'react'
import Footer from '../../Layout/js/Footer'
import CustomerHeader from '../../Layout/js/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import '../css/BookingPage.css';
import axios from "axios";

export default class ChooseService extends Component {
  state = {
    businesses: []
  }
  constructor(props) {
    super(props);

    axios.get("http://localhost:8080/api/business/all", { headers: {Authorization: this.props.location.state.auth}})
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
            <CustomerHeader state={this.props.location.state} />

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
                            pathname: '/choose-time',
                            state: {user: this.props.location.state.user,
                                    service: business,
                                    auth: this.props.location.state.auth}
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
