import React, { Component } from 'react'
import Footer from '../Layout/Footer'
import CustomerHeader from '../Layout/CustomerHeader'
import { BrowserRouter as Router, Link } from "react-router-dom"
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default class BookingPage2 extends Component {
    
    handleStartChange = date => {
      this.setState({
        startDate: date
      });
    };

    handleStartSelect = date => {
      this.setState({
        startDate: date
      });
    };
    handleEndChange = date => {
      this.setState({
        endDate: date
      });
    };

    handleEndSelect = date => {
      this.setState({
        endDate: date
      });
    };
  constructor(props) {
    super(props);

    this.state = {
      startDate: new Date(),
      endDate: new Date(),
      service: this.props.location.state.service
    };
  }
  onSubmit = async e =>{
    e.preventDefault();

    var bad = false;
    var diffHours = this.state.endDate.getHours() - this.state.startDate.getHours();
    // Comparing start and end time
    if (this.state.endDate <= this.state.startDate) {
      window.alert("The ending time of the booking should be after starting time; please try again.");
      bad = true;
    }
    else if (this.state.endDate.getDate() != this.state.startDate.getDate() || this.state.endDate.getDay() != this.state.startDate.getDay()){
      window.alert("Booking date for start and end time should be the same; please try again.");
      bad = true;
    }
    // Booking for maximum of 2 hours
    else if (diffHours > 2 || (diffHours == 2 && this.state.endDate.getMinutes() > this.state.startDate.getMinutes())) {
      window.alert("Maximum booking time is 2 hours; please try again.");
      bad = true;
    }
    // Booking date should be in the future
    else if (this.state.startDate < new Date()) {
      window.alert("Booking time cannot be in the past; please try again.");
      bad = true;
    }

    if (bad == false) {
      this.props.history.push('/bookingPage3', {service: this.state.service,
                                                startDate: this.state.startDate,
                                                endDate: this.state.endDate,
                                                user: this.props.location.state.user});
    }
  }
  render() {
    return (
      <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <CustomerHeader user={this.props.location.state} />

            <form className="date-form" onSubmit={this.onSubmit}>
                <h3 className="date-h3">Choose date and time</h3>
                <hr/>
                <br/>
                <br/>
                <div> Start time: </div>
                <div>
                  <DatePicker className="datePicker"
                      selected={this.state.startDate}
                      onSelect={this.handleStartSelect}
                      onChange={this.handleStartChange}
                      showTimeSelect
                      dateFormat="Pp"
                  />
                </div>

                <br/>
                <br/>
                <div> End time: </div>
                <div>
                  <DatePicker className="datePicker"
                      selected={this.state.endDate}
                      onSelect={this.handleEndSelect}
                      onChange={this.handleEndChange}
                      showTimeSelect
                      dateFormat="Pp"
                  />
                </div>
                
                <br/>
                <br/>

                <div className="btn-div">
                    <button type="submit" className="mybtn btn-primary">Show available workers</button> 
                </div>
            </form>

          </div>
          <Footer/>
        </div>
      </div>
    )
  }
}
