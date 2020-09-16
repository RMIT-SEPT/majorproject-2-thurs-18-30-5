import React, { Component } from 'react'
import Footer from '../Layout/Footer'
import CustomerHeader from '../Layout/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';
import axios from "axios";

export default class BookingPage2 extends Component {
  state = {
    startDate: this.props.location.state.startDate,
    endDate: this.props.location.state.endDate,
    service: this.props.location.state.service,
    workers: []
  };
  constructor(props) {
    super(props);

    axios.get("http://localhost:8080/api/worker/all")
      .then(res => {
        const workers = res.data;
        this.setState({workers: workers});
      })
    console.log(this.state);
  }
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <CustomerHeader user={this.props.location.state} />

            <form>
                <h3>Choose a worker</h3>
                <hr/>
                <br/>
                <br/>
                {
                  this.state.workers.map(worker =>
                    <div><div className="service-div"><Link to={{
                      pathname: '/confirmBooking',
                      state: {startDate: this.state.startDate, endDate: this.state.endDate, service: this.state.service, worker: worker, user:this.props.location.state.user}
                    }}><button className="service-btn" type="submit">{worker.user.firstName}</button></Link></div><br/><br/></div>
                  )
                }
                {console.log(this.state.workers)}
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
