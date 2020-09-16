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
    // Comparing start and end time
    if (this.state.endDate <= this.state.startDate || this.state.endDate.getDate() != this.state.startDate.getDate()) {
      bad = true;
    }
    if (this.state.endDate.getDay() != this.state.startDate.getDay()) {
      bad = true;
    }
    // Booking for maximum of 2 hours
    var diffHours = this.state.endDate.getHours() - this.state.startDate.getHours();
    if (diffHours > 2 || (diffHours == 2 && this.state.endDate.getMinutes() > this.state.startDate.getMinutes())) {
      bad = true;
    }

    if (bad == false) {
      try {
        this.props.history.push('/bookingPage3', {service: this.state.service,
                                                  startDate: this.state.startDate,
                                                  endDate: this.state.endDate,
                                                  user: this.props.location.state.user});
      } catch (err) {
        console.log(err);
        window.location.reload(false);
      }
    }
    else {
      window.location.reload(false);
    }

    /*<Link to={{
      pathname: '/bookingPage3',
      state: this.state
    }}>*/
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
