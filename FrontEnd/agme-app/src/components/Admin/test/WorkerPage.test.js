import React from "react";
import WorkerPage from '../js/WorkerPage';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<WorkerPage /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        worker: "",
        date: "",
        is_day_selected: true
    }

    mockLoc.state.worker = {
        user: ""
    }

    mockLoc.state.worker.user = {
        useranem: ""
    }
    const wrapper = shallow(<WorkerPage location={mockLoc}/>);

    it("should render worker title in <WorkerPage /> component", () => {
        const workerTitle = wrapper.find(".profile-title");
        expect(workerTitle).toHaveLength(1);
        expect(workerTitle.text()).toEqual("Worker data");
    });

    it("should render username label in <WorkerPage /> component", () => {
        const username = wrapper.find(".work-uname");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render first name label in <WorkerPage /> component", () => {
        const firstName = wrapper.find(".work-fname");
        expect(firstName).toHaveLength(1);
        expect(firstName.text()).toEqual("First name");
    });

    it("should render last name label in <WorkerPage /> component", () => {
        const lastName = wrapper.find(".work-lname");
        expect(lastName).toHaveLength(1);
        expect(lastName.text()).toEqual("Last name");
    });

    it("should render address label in <WorkerPage /> component", () => {
        const address = wrapper.find(".work-addr");
        expect(address).toHaveLength(1);
        expect(address.text()).toEqual("Address");
    });

    it("should render worker password label in <WorkerPage /> component", () => {
        const workerPwd = wrapper.find(".admin-pwd-title");
        expect(workerPwd).toHaveLength(1);
        expect(workerPwd.text()).toEqual("WORKER password");
    });

    it("should render availability title in <WorkerPage /> component", () => {
        const availabilityTitle = wrapper.find(".availability-title");
        expect(availabilityTitle).toHaveLength(1);
        expect(availabilityTitle.text()).toEqual("Availability");
    });

    it("should render working hour title in <WorkerPage /> component", () => {
        const workingHourTitle = wrapper.find(".working-hour-title");
        expect(workingHourTitle).toHaveLength(1);
        expect(workingHourTitle.text()).toEqual("Working hours");
    });

});

describe("<WorkerPage /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        worker: "",
        date: null
    }

    mockLoc.state.worker = {
        user: ""
    }

    mockLoc.state.worker.user = {
        useranem: ""
    }

    const wrapper = shallow(<WorkerPage location={mockLoc}/>);
    it("should render no date message in <WorkerPage /> component", () => {
        const noDateMsg = wrapper.find(".no-date-msg");
        expect(noDateMsg).toHaveLength(1);
        expect(noDateMsg.text()).toEqual("Select a day and click 'CHECK TIME' to view availability and working hour");
    });
});