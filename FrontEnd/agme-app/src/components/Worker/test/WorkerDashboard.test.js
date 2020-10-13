import React from "react";
import WorkerDashboard from '../js/WorkerDashboard';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<WorkerDashboard /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: "",
        confirmedBookings: [],
        completedBookings: []
    }

    mockLoc.state.user = {
        user: ""
    }

    mockLoc.state.user.user = {
        firstName: "Mike"
    }

    const wrapper = shallow(<WorkerDashboard location={mockLoc}/>);

    it("should render welcome message in <WorkerDashboard /> component", () => {
        const welcomeMsg = wrapper.find(".welcome-msg");
        expect(welcomeMsg).toHaveLength(1);
        expect(welcomeMsg.text()).toEqual("G'day, Mike!");
    });

    it("should render confirmed bookings title in <WorkerDashboard /> component", () => {
        const confirmedTitle = wrapper.find(".upcoming-title");
        expect(confirmedTitle).toHaveLength(1);
        expect(confirmedTitle.text()).toEqual("Confirmed bookings");
    });

    it("should render completed bookings title in <WorkerDashboard /> component", () => {
        const completedTitle = wrapper.find(".past-title");
        expect(completedTitle).toHaveLength(1);
        expect(completedTitle.text()).toEqual("Completed bookings");
    });

    it("should render no confirmed bookings message in <WorkerDashboard /> component", () => {
        const noConfMsg = wrapper.find(".conf-msg");
        expect(noConfMsg).toHaveLength(1);
        expect(noConfMsg.text()).toEqual("No confirmed bookings at the moment.");
    });

    it("should render no completed bookings message in <WorkerDashboard /> component", () => {
        const noCompMsg = wrapper.find(".comp-msg");
        expect(noCompMsg).toHaveLength(1);
        expect(noCompMsg.text()).toEqual("No completed bookings recorded.");
    });
});