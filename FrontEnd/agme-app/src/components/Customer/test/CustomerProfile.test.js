import React from "react";
import CustomerProfile from '../js/CustomerProfile';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<CustomerProfile /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<CustomerProfile location={mockLoc}/>);

    it("should render profile title in <CustomerProfile /> component", () => {
        const profileTitle = wrapper.find(".profile-title");
        expect(profileTitle).toHaveLength(1);
        expect(profileTitle.text()).toEqual("Profile");
    });

    it("should render username label in <CustomerProfile /> component", () => {
        const userName = wrapper.find(".cus-pro-uname");
        expect(userName).toHaveLength(1);
        expect(userName.text()).toEqual("Username");
    });

    it("should render first name label in <CustomerProfile /> component", () => {
        const firstname = wrapper.find(".cus-pro-fname");
        expect(firstname).toHaveLength(1);
        expect(firstname.text()).toEqual("First name");
    });

    it("should render last name label in <CustomerProfile /> component", () => {
        const lastName = wrapper.find(".cus-pro-lname");
        expect(lastName).toHaveLength(1);
        expect(lastName.text()).toEqual("Last name");
    });

    it("should render address label in <CustomerProfile /> component", () => {
        const address = wrapper.find(".cus-pro-addr");
        expect(address).toHaveLength(1);
        expect(address.text()).toEqual("Address");
    });

    it("should render password label in <CustomerProfile /> component", () => {
        const password = wrapper.find(".cus-pro-pwd");
        expect(password).toHaveLength(1);
        expect(password.text()).toEqual("Password");
    });

});