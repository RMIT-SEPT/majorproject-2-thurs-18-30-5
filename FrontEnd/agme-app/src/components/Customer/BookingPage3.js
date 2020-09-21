import React, { Component } from 'react'
import Footer from '../Layout/Footer'
import CustomerHeader from '../Layout/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import './BookingPage.css';
import axios from "axios";

export default class BookingPage2 extends Component {
  constructor(props) {
    super(props);

    this.state = {
      startDate: "",
      endDate: "",
      service: this.props.location.state.service,
      workers: []
    };

    var d = this.props.location.state.startDate;
    var formattedStart = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+(d.getMinutes().toString().length==2?d.getMinutes().toString():"0"+d.getMinutes().toString());
    this.state.startDate = formattedStart;
    
    d = this.props.location.state.endDate;
    var formattedEnd = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+(d.getMinutes().toString().length==2?d.getMinutes().toString():"0"+d.getMinutes().toString());
    this.state.endDate = formattedEnd;
    
    axios.get("http://Backend-Dev-dev.us-east-1.elasticbeanstalk.com/api/worker/business/" + this.state.service.id.toString(), { params: { start: this.state.startDate, end: this.state.endDate } })
      .then(res => {
        const workers = res.data;
        this.setState({workers: workers});
      })
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
                { this.state.workers.length > 0 &&
                  this.state.workers.map(worker =>
                    <div><div className="service-div"><Link to={{
                      pathname: '/confirmBooking',
                      state: {startDate: this.state.startDate, endDate: this.state.endDate, service: this.state.service, worker: worker, user:this.props.location.state.user}
                    }}><button className="service-btn" type="submit">{worker.user.firstName}</button></Link></div><br/><br/></div>
                  )
                }
                { this.state.workers.length == 0 &&
                    <div><div className="service-div-2">Unfortunately there is not any worker available at this time; click <Link to={{
                      pathname: '/bookingPage2',
                      state: {service: this.state.service, user:this.props.location.state.user}
                    }}>here</Link> to choose another time.</div><br/><br/></div>
                }
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
