import React, { Component } from 'react'
import Footer from './Layout/Footer'
import Header from './Layout/Header'
import { BrowserRouter as Router, Link } from "react-router-dom"
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default class BookingPage2 extends Component {

    state = {
        date: new Date(),
        service: this.props.location.state.service
      };
     
      handleChange = date => {
        this.setState({
          date: date
        });
      };

      handleSelect = date => {
        this.setState({
          date: date
        });
      };

  render() {
    return (
        <div className="img-bg">
        <div className="auth-wrapper">
          <div className="auth-inner">
            <Header/>

            <form>
                <h3>Choose date and time</h3>
                <hr/>
                <br/>
                <br/>
                <div>
                    <DatePicker className="datePicker"
                        selected={this.state.date}
                        onSelect={this.handleSelect}
                        onChange={this.handleChange}
                        showTimeSelect
                        dateFormat="Pp"
                    />
                </div>
                
                <br/>
                <br/>

                <div className="btn-div">
                    <Link to={{
                        pathname: '/bookingPage3',
                        state: this.state
                      }}>
                        <button type="submit" className="mybtn btn-primary">Show available workers</button> 
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
