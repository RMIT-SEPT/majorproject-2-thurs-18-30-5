import React from "react";
import CustomerDashboard from '../js/CustomerDashboard';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<CustomerDashboard /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }

    mockLoc.state.user = {
        firstName: "John"
    }
    const wrapper = shallow(<CustomerDashboard location={mockLoc}/>);

    it("should render welcome message in <CustomerDashboard /> component", () => {
        const welcomeMsg = wrapper.find(".welcome-msg");
        expect(welcomeMsg).toHaveLength(1);
        expect(welcomeMsg.text()).toEqual("G'day, John!");
    });

    it("should render book button in <CustomerDashboard /> component", () => {
        const bookBtn = wrapper.find(".book-btn");
        expect(bookBtn).toHaveLength(1);
        expect(bookBtn.text()).toEqual("Book a service");
    });

    it("should render upcoming booking title in <CustomerDashboard /> component", () => {
        const upcomingTitle = wrapper.find(".upcoming-title");
        expect(upcomingTitle).toHaveLength(1);
        expect(upcomingTitle.text()).toEqual("Upcoming bookings");
    });

    it("should render past booking title in <CustomerDashboard /> component", () => {
        const pastTitle = wrapper.find(".past-title");
        expect(pastTitle).toHaveLength(1);
        expect(pastTitle.text()).toEqual("Past bookings");
    });
});