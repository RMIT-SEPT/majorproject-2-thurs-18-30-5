import React from "react";
import UpcomingBooking from '../js/UpcomingBooking';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<UpcomingBooking /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<UpcomingBooking location={mockLoc}/>);

    it("should render upcoming bookings title in <UpcomingBooking /> component", () => {
        const upcomingBookingTitle = wrapper.find(".book-title");
        expect(upcomingBookingTitle).toHaveLength(1);
        expect(upcomingBookingTitle.text()).toEqual("Upcoming Bookings");
    });

    it("should render no upcoming bookings message in <UpcomingBooking /> component", () => {
        const noBookingMsg = wrapper.find(".no-booking-msg");
        expect(noBookingMsg).toHaveLength(1);
        expect(noBookingMsg.text()).toEqual("No upcoming bookings at the moment.");
    });
});