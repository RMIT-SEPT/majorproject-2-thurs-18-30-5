import React, { Component } from 'react'
import Footer from '../../Layout/js/Footer'
import CustomerHeader from '../../Layout/js/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import '../css/BookingPage.css';
import axios from "axios";

export default class ChooseWorker extends Component {
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
    
    axios.get("http://localhost:8080/api/worker/business/" + this.state.service.id.toString(), { headers: {Authorization: this.props.location.state.auth}, params: { start: this.state.startDate, end: this.state.endDate, isAdmin: false } })
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
            <CustomerHeader state={this.props.location.state} />

            <form className="service-form">
              <h3 className="choose-worker-title">Choose a worker</h3>
              <hr/>

              { 
                this.state.workers.length > 0 &&
                this.state.workers.map(worker =>
                  <div className="service-table-scroll">
                    <table className="table text-nowrap table-borderless service-table">
                      <tbody>
                        <tr>
                          <div className="service-div">
                            <Link to={{
                                pathname: '/confirmBooking',
                                state: {startDate: this.state.startDate, 
                                        endDate: this.state.endDate, 
                                        service: this.state.service, 
                                        worker: worker, 
                                        user: this.props.location.state.user,
                                        auth: this.props.location.state.auth}
                            }}>
                              <button className="booking-btn service-btn" type="submit">{worker.user.firstName}</button>
                            </Link>
                          <br/><br/>
                          </div>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                )
              }

              { 
                this.state.workers.length == 0 &&
                <div className="service-div-2">
                  Unfortunately there is no worker available at this time. Click 
                  <Link to={{
                    pathname: '/choose-time',
                    state: {service: this.state.service, user:this.props.location.state.user, auth: this.props.location.state.auth}
                  }}> here </Link>
                  to choose another time.
                  <br/><br/>
                </div>
                
              }
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
