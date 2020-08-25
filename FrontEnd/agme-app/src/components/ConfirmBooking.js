import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';

export default class ConfirmBooking extends Component {

    state = {
        date: this.props.location.state.date,
        service: this.props.location.state.service,
        worker: this.props.location.state.worker
    };

  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
                <h3>Booking details</h3>
                <hr/>
                <br/>
                <br/>
                <h6>Service:</h6>
                <h5 className="hCol">{this.state.service}</h5>
                <br/>
                <h6>Date & Time:</h6>
                <h5 className="hCol">{this.state.date.toString()}</h5>
                <br/>
                <h6>Worker:</h6>
                <h5 className="hCol">{this.state.worker}</h5>
                <br/>
                <br/>

                <div className="btn2-div">
                    <Link to={{
                        pathname: '/customer-dashboard',
                        state: this.state
                      }}>
                        <button type="submit" className="mybtn btn-primary">Confirm booking</button> 
                    </Link>
                </div>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
