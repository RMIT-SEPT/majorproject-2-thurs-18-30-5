import React from "react";
import CustomerProfile from './CustomerProfile';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<CustomerProfile /> component Unit Test", () => {
  const wrapper = shallow(<CustomerProfile />);

    it("should render title of profile in <CustomerProfile /> component", () => {
        const title = wrapper.find(".profile-title");
        expect(title).toHaveLength(1);
        expect(title.text()).toEqual("Profile");
    });

    it("should render name label in <CustomerProfile /> component", () => {
      const name = wrapper.find(".name");
      expect(name).toHaveLength(1);
      expect(name.text()).toEqual("Name");
    });

    it("should render username label in <CustomerProfile /> component", () => {
        const username = wrapper.find(".username");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render password label in <CustomerProfile /> component", () => {
        const pwd = wrapper.find(".pwd");
        expect(pwd).toHaveLength(1);
        expect(pwd.text()).toEqual("Password");
    });

    it("should render password confirmation label in <CustomerProfile /> component", () => {
        const confirm_pwd = wrapper.find(".confirm-pwd");
        expect(confirm_pwd).toHaveLength(1);
        expect(confirm_pwd.text()).toEqual("Confirm password");
    });

    it("should render email label in <CustomerProfile /> component", () => {
        const email = wrapper.find(".email");
        expect(email).toHaveLength(1);
        expect(email.text()).toEqual("E-mail");
    });

    it("should render contact number label in <CustomerProfile /> component", () => {
        const contact_no = wrapper.find(".contact-no");
        expect(contact_no).toHaveLength(1);
        expect(contact_no.text()).toEqual("Contact number");
    });

    it("should render cancel button in <CustomerProfile /> component", () => {
        const cancel = wrapper.find(".cancel-changes");
        expect(cancel).toHaveLength(1);
        expect(cancel.text()).toEqual("cancel");
    });

    it("should render save button in <CustomerProfile /> component", () => {
        const save = wrapper.find(".save-changes");
        expect(save).toHaveLength(1);
        expect(save.text()).toEqual("Save changes");
    });

});