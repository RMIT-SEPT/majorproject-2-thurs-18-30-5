import React from "react";
import WorkerProfile from '../js/WorkerProfile';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<WorkerProfile /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }

    mockLoc.state.user = {
        user: "",
        business: ""
    }

    mockLoc.state.user.business = {
        name: ""
    }

    mockLoc.state.user.user = {
        username: ""
    }

    const wrapper = shallow(<WorkerProfile location={mockLoc}/>);

    it("should render profile title in <WorkerProfile /> component", () => {
        const profileTitle = wrapper.find(".profile-title");
        expect(profileTitle).toHaveLength(1);
        expect(profileTitle.text()).toEqual("Profile");
    });

    it("should render username label in <WorkerProfile /> component", () => {
        const username = wrapper.find(".work-pf-uname");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render username label in <WorkerProfile /> component", () => {
        const bizName = wrapper.find(".work-pf-biz");
        expect(bizName).toHaveLength(1);
        expect(bizName.text()).toEqual("Business");
    });

    it("should render username label in <WorkerProfile /> component", () => {
        const firstName = wrapper.find(".work-pf-fname");
        expect(firstName).toHaveLength(1);
        expect(firstName.text()).toEqual("First name");
    });

    it("should render username label in <WorkerProfile /> component", () => {
        const lastName = wrapper.find(".work-pf-lname");
        expect(lastName).toHaveLength(1);
        expect(lastName.text()).toEqual("Last name");
    });

    it("should render username label in <WorkerProfile /> component", () => {
        const address = wrapper.find(".work-pf-addr");
        expect(address).toHaveLength(1);
        expect(address.text()).toEqual("Address");
    });
});