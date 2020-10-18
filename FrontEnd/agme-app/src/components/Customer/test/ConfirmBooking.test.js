import React from "react";
import ConfirmBooking from '../js/ConfirmBooking';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<ConfirmBooking /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        startDate: "2020-10-15 11:30",
        endDate: "2020-10-15 13:00",
        service: "",
        worker: ""
    }

    mockLoc.state.service = {
        name: "Pandemic Hair"
    }

    mockLoc.state.worker = {
        user: ""
    }
    
    mockLoc.state.worker.user = {
        firstName: "John"
    }
    
    const wrapper = shallow(<ConfirmBooking location={mockLoc}/>);

    it("should render booking details title in <ConfirmBooking /> component", () => {
        const bookDetailsTitle = wrapper.find(".book-details-title");
        expect(bookDetailsTitle).toHaveLength(1);
        expect(bookDetailsTitle.text()).toEqual("Booking details");
    });

    it("should render service label in <ConfirmBooking /> component", () => {
        const serviceLabel = wrapper.find(".book-label-service");
        expect(serviceLabel).toHaveLength(1);
        expect(serviceLabel.text()).toEqual("Service");
    });

    it("should render start time label in <ConfirmBooking /> component", () => {
        const startLabel = wrapper.find(".book-label-start");
        expect(startLabel).toHaveLength(1);
        expect(startLabel.text()).toEqual("Start date & time");
    });

    it("should render end time label in <ConfirmBooking /> component", () => {
        const endLabel = wrapper.find(".book-label-end");
        expect(endLabel).toHaveLength(1);
        expect(endLabel.text()).toEqual("End date & time");
    });

    it("should render worker label in <ConfirmBooking /> component", () => {
        const workerLabel = wrapper.find(".book-label-worker");
        expect(workerLabel).toHaveLength(1);
        expect(workerLabel.text()).toEqual("Worker");
    });

    it("should render service detail in <ConfirmBooking /> component", () => {
        const serviceDetail = wrapper.find(".service-detail");
        expect(serviceDetail).toHaveLength(1);
        expect(serviceDetail.text()).toEqual("Pandemic Hair");
    });

    it("should render start time in <ConfirmBooking /> component", () => {
        const startDetail = wrapper.find(".start-detail");
        expect(startDetail).toHaveLength(1);
        expect(startDetail.text()).toEqual("2020-10-15 11:30");
    });

    it("should render end time in <ConfirmBooking /> component", () => {
        const endDetail = wrapper.find(".end-detail");
        expect(endDetail).toHaveLength(1);
        expect(endDetail.text()).toEqual("2020-10-15 13:00");
    });

    it("should render worker name in <ConfirmBooking /> component", () => {
        const workerDetail = wrapper.find(".worker-detail");
        expect(workerDetail).toHaveLength(1);
        expect(workerDetail.text()).toEqual("John");
    });
});