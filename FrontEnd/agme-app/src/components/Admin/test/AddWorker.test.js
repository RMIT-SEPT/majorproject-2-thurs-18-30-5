import React from "react";
import AddWorker from '../js/AddWorker';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<AddWorker /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<AddWorker location={mockLoc}/>);

    it("should render add new worker title in <AddWorker /> component", () => {
        const profileTitle = wrapper.find(".profile-title");
        expect(profileTitle).toHaveLength(1);
        expect(profileTitle.text()).toEqual("Add new worker");
    });

    it("should render username label in <AddWorker /> component", () => {
        const username = wrapper.find(".add-uname");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render username label in <AddWorker /> component", () => {
        const firstName = wrapper.find(".add-fname");
        expect(firstName).toHaveLength(1);
        expect(firstName.text()).toEqual("First name");
    });

    it("should render username label in <AddWorker /> component", () => {
        const lastName = wrapper.find(".add-lname");
        expect(lastName).toHaveLength(1);
        expect(lastName.text()).toEqual("Last name");
    });

    it("should render username label in <AddWorker /> component", () => {
        const password = wrapper.find(".add-pwd");
        expect(password).toHaveLength(1);
        expect(password.text()).toEqual("Password");
    });

    it("should render username label in <AddWorker /> component", () => {
        const conPassword = wrapper.find(".add-con-pwd");
        expect(conPassword).toHaveLength(1);
        expect(conPassword.text()).toEqual("Confirm password");
    });

    it("should render username label in <AddWorker /> component", () => {
        const adminPassword = wrapper.find(".admin-pwd-title");
        expect(adminPassword).toHaveLength(1);
        expect(adminPassword.text()).toEqual("ADMIN password");
    });
});