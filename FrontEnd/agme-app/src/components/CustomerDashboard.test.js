import React from "react";
import CustomerDashboard from './CustomerDashboard';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<CustomerDashboard /> component Unit Test", () => {
  const wrapper = shallow(<CustomerDashboard />);

    it("should render booking button in <CustomerDashboard /> component", () => {
        const book = wrapper.find(".book-btn");
        expect(book).toHaveLength(1);
        expect(book.text()).toEqual("Book a service");
    });

    it("should render title of upcoming bookings label in <CustomerDashboard /> component", () => {
        const upcoming_title = wrapper.find(".upcoming-title");
        expect(upcoming_title).toHaveLength(1);
        expect(upcoming_title.text()).toEqual("Upcoming bookings");
    });

    it("should render title of past bookings label in <CustomerDashboard /> component", () => {
        const past_title = wrapper.find(".past-title");
        expect(past_title).toHaveLength(1);
        expect(past_title.text()).toEqual("Past bookings");
    });

    it("should render date label in booking summary", () => {
        const date = wrapper.find(".date");
        expect(date).toHaveLength(1);
        expect(date.text()).toEqual("Date");
    });

    it("should render time label in booking summary", () => {
        const time = wrapper.find(".time");
        expect(time).toHaveLength(1);
        expect(time.text()).toEqual("Time");
    });

    it("should render service label in booking summary", () => {
        const service = wrapper.find(".service");
        expect(service).toHaveLength(1);
        expect(service.text()).toEqual("Service");
    });

    it("should render worker label in booking summary", () => {
        const worker = wrapper.find(".worker");
        expect(worker).toHaveLength(1);
        expect(worker.text()).toEqual("Worker");
    });
});