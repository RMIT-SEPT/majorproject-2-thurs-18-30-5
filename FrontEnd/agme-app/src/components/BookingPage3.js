import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';

const workers = ['Worker-1', 'Worker-2', 'worker-3'];

export default class BookingPage2 extends Component {

    state = {
        date: this.props.location.state.date,
        service: this.props.location.state.service,
      };

  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
                <h3>Choose a worker</h3>
                <br/>
                <br/>
                {
                    workers.map(worker =>
                        // expression goes here:
                        <div><div className="service-div"><Link to={{
                            pathname: '/confirmBooking',
                            state: {date: this.state.date, service: this.state.service, worker: worker}
                          }}><button className="service-btn" type="submit">{worker}</button></Link></div><br/><br/></div>
                      )
                }
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
