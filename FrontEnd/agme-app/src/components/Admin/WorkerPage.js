import React, { Component } from 'react'
import { BrowserRouter as Router, Link } from "react-router-dom"
import AdminHeader from '../Layout/AdminHeader'
import Footer from '../Layout/Footer'
import DatePicker from "react-datepicker";
import './WorkerPage.css'
import "react-datepicker/dist/react-datepicker.css";
import axios from "axios";

export default class AddWorker extends Component {
  constructor(props) {
    super(props);

    console.log (this.props);
    this.state = {
      "address": "",
      "password": "",
      "firstName": "",
      "lastName": "",
      "date": "",
      "startDate": "",
      "endDate": "",
      "hoursList":[]
    }

    try {
      var date = this.props.location.state.date;
      this.state.date = date;
    } catch (err) {

    }

    try {
      var d = this.state.date;
      var formattedDate = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString());
      axios.get("http://localhost:8080/api/hours/available/" + this.props.location.state.worker.user.id, { params: { date: formattedDate } })
        .then(res => {
          const hoursList = res.data;
          this.setState({hoursList: hoursList});
          this.state.hoursList = hoursList;
        })
    } catch (err) {

    }

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onSubmit1 = this.onSubmit1.bind(this);
    this.onClick = this.onClick.bind(this);
    this.onClick2 = this.onClick2.bind(this);
  }
  onChange(e){
    this.setState({[e.target.name]: e.target.value});
  }
  onSubmit = async e =>{
      e.preventDefault();
      const newPerson = {
          id: this.props.location.state.worker.id,
          username: this.props.location.state.worker.user.username,
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          password: this.state.password,
          address: this.state.address
      }

      if (this.state.firstName == "") {
        newPerson.firstName = this.props.location.state.worker.user.firstName;
      }
      if (this.state.lastName == "") {
        newPerson.lastName = this.props.location.state.worker.user.lastName;
      }
      if (this.state.address == "") {
        newPerson.address = this.props.location.state.worker.user.address;
      }

      
      try {
        await axios.put("http://localhost:8080/api/customer", newPerson);
        const worker = this.props.location.state.worker;
        worker.user = newPerson;
        this.props.history.push('/worker-page', {user: this.props.location.state.user, worker: worker});
      } catch (err) {
        window.alert("Incorrect password; please try again.");
      }
      window.location.reload(false);
  }
  onClick(e){
    window.location.reload(false);
  }

  handleDateChange = date => {
    this.setState({
      date: date
    });
  };
  handleDateSelect = date => {
    this.setState({
      date: date
    });
  };
  onSubmit1(e){
    e.preventDefault();
    this.props.history.push('/worker-page', {
      user: this.props.location.state.user, 
      worker: this.props.location.state.worker,
      date: this.state.date
    });
    window.location.reload(false);
  }

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
  handleendSelect = date => {
    this.setState({
      endDate: date
    });
  };
  onClick2 = async e =>{
    e.preventDefault();

    var bad = false;
    var diffHours = this.state.endDate.getHours() - this.state.startDate.getHours();
    // Comparing start and end time
    if (this.state.endDate <= this.state.startDate) {
      window.alert("The ending time of the hours should be after starting time; please try again.");
      bad = true;
    }
    else if (this.state.endDate.getDate() != this.state.startDate.getDate() || this.state.endDate.getDay() != this.state.startDate.getDay()){
      window.alert("Date for start and end time should be the same; please try again.");
      bad = true;
    }
    // Minimum working time of 2 hours
    else if (diffHours < 1) {
      window.alert("Minimum working time is 2 hours; please try again.");
      bad = true;
    }

    const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];

    if (bad == false) {
      if (this.state.date == "") {
        window.alert("Please select a day first!");
      }
      else {
        try {
          await axios.delete("http://localhost:8080/api/hours/" + this.props.location.state.worker.id, { params: { dayOfWeek: days[this.state.startDate.getDay()] } });
        } catch (err) {
        }

        try {
          var d = this.state.startDate;
          var formattedStart = (d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+(d.getMinutes().toString().length==2?d.getMinutes().toString():"0"+d.getMinutes().toString());
          this.state.startDate = formattedStart;
        
          d = this.state.endDate;
          var formattedEnd = (d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+(d.getMinutes().toString().length==2?d.getMinutes().toString():"0"+d.getMinutes().toString());
          this.state.endDate = formattedEnd;
          const hours = {
            start: this.state.startDate,
            end: this.state.endDate,
            id:{
              dayOfWeek: (this.state.date.getDay() + 6) % 7
            }
          };
          await axios.post("http://localhost:8080/api/hours/" + this.props.location.state.worker.id, hours);
        } catch (err) {

        }
      }
    }

    window.location.reload(false);
  }

  render() {
    return (
      <div>
        <AdminHeader user={this.props.location.state} />
          <div className="admin-img">
            <div className="container profile">
              <form onSubmit={this.onSubmit}>
                <div className="profile-title">Worker data</div>

                <div className="form-group row field-row">
                  <label for="inputusername" className="col-sm-2 col-form-label">Username</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputusername" 
                      value={this.props.location.state.worker.user.username}
                      readOnly />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputfname" className="col-sm-2 col-form-label">First name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputfname" 
                      spellCheck="false"
                      placeholder={this.props.location.state.worker.user.firstName} name="firstName"
                      value= {this.state.firstName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputlname" className="col-sm-2 col-form-label">Last name</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputlname" 
                      spellCheck="false"
                      placeholder={this.props.location.state.worker.user.lastName} name="lastName"
                      value= {this.state.lastName}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row">
                  <label for="inputaddress" className="col-sm-2 col-form-label address">Address</label>
                  <div className="col-sm-10">
                    <input 
                      type="text" 
                      className="form-control field-input work-field" 
                      id="inputaddress" 
                      spellCheck="false"
                      placeholder={this.props.location.state.worker.user.address} name="address"
                      value= {this.state.address}
                      onChange = {this.onChange} />
                  </div>
                </div>

                <div className="form-group row field-row worker-admin-pwd">
                  <label for="inputpwd" className="col-sm-2 col-form-label admin-pwd-title">WORKER password</label>
                  <div className="col-sm-10">
                  <input 
                    type="password" 
                    className="form-control field-input admin-pwd edit-admin-pwd" 
                    id="inputpwd" 
                    placeholder="Enter WORKER password to confirm changes" name="password"
                    value= {this.state.password}
                    onChange= {this.onChange} />
                  </div>
                </div>

                <div className="container edit-worker">
                  <button type="reset" className="btn cancel-changes" onClick={this.onClick}>cancel</button>
                  <button type="submit" className="btn save-changes worker-save">Submit</button>
                </div>
              </form>

              <form onSubmit={this.onSubmit1}>
                <div className="card work-info">
                  <div className="select-date">
                    <DatePicker 
                      className="date-time work-day" 
                      minDate={new Date()}
                      maxDate={new Date(new Date().setDate(new Date().getDate()+6))}
                      placeholderText="Select a day"
                      selected={this.state.date}
                      onSelect={this.handleDateSelect}
                      onChange={this.handleDateChange}
                      dateFormat= "yyyy-MM-dd" />
                  </div>

                  <div className="check-time-btn">
                    <button type="submit" className="btn check-time">check time</button>
                  </div>
  
                  <div className="card-title availability-title">Availability</div>

                  <div className="avail-table-scroll">
                    <table className="table table-editable text-nowrap table-borderless table-hover avail-table">
                      <thead className="avail-title">
                        <tr>
                          <th scope="col" className="avail-header avail-from">from</th>
                          <th scope="col" className="avail-header avail-to">to</th>
                        </tr>
                      </thead>
                      <tbody>
                        {
                          this.state.hoursList.map(hours =>
                            <tr>
                              <td> {hours.start} </td>
                              <td> {hours.end} </td>
                            </tr>
                          )
                        }
                      </tbody>
                    </table>
                  </div>
                  
                  <div className="card-title working-hour-title">Working hours</div>

                  <div className="select-start-time">
                    <DatePicker className="date-time select-time" 
                      placeholderText="start time" 
                      selected={this.state.startDate}
                      onSelect={this.handleStartSelect}
                      onChange={this.handleStartChange}
                      showTimeSelect
                      dateFormat="HH:mm"
                      minDate={this.state.date}
                      maxDate={this.state.date} />
                  </div>

                  <div className="select-end-time">
                    <DatePicker className="date-time select-time" 
                      placeholderText="end time" 
                      selected={this.state.endDate}
                      onSelect={this.handleEndSelect}
                      onChange={this.handleEndChange}
                      showTimeSelect
                      dateFormat="HH:mm"
                      minDate={this.state.date}
                      maxDate={this.state.date} />
                  </div>

                  <div className="change-hour-btn">
                    <button className="btn check-time change-time" onClick={this.onClick2}>update working hours</button>
                  </div>

                </div> 

              </form>
            </div>
          </div>
        <Footer/>
      </div>
    )
  }
}
