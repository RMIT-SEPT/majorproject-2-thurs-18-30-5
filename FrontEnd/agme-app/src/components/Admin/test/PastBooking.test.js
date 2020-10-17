import React from "react";
import PastBooking from '../js/PastBooking';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<PastBooking /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<PastBooking location={mockLoc}/>);

    it("should render past bookings title in <PastBooking /> component", () => {
        const pastBookingTitle = wrapper.find(".book-title");
        expect(pastBookingTitle).toHaveLength(1);
        expect(pastBookingTitle.text()).toEqual("Past Bookings");
    });

    it("should render no past bookings message in <PastBooking /> component", () => {
        const noBookingMsg = wrapper.find(".no-booking-msg");
        expect(noBookingMsg).toHaveLength(1);
        expect(noBookingMsg.text()).toEqual("No past bookings at the moment.");
    });
});