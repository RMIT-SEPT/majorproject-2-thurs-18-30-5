import React from "react";
import WorkerJobs from '../js/WorkerJobs';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<WorkerJobs /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: "",
        pendingBookings: []
    }

    const wrapper = shallow(<WorkerJobs location={mockLoc}/>);

    it("should render pending job title in <WorkerJobs /> component", () => {
        const pageTitle = wrapper.find(".welcome-msg");
        expect(pageTitle).toHaveLength(1);
        expect(pageTitle.text()).toEqual("Pending Jobs");
    });

    it("should render no pending jobs message in <WorkerJobs /> component", () => {
        const noBookingMsg = wrapper.find(".no-booking-msg");
        expect(noBookingMsg).toHaveLength(1);
        expect(noBookingMsg.text()).toEqual("No pending jobs at the moment.");
    });
});