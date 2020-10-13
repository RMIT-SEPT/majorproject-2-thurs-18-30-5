import React from "react";
import Signup from '../js/Signup';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<Signup /> component Unit Test", () => {
  const wrapper = shallow(<Signup />);

    it("should render sign up title in <Signup /> component", () => {
        const signup = wrapper.find(".signup");
        expect(signup).toHaveLength(1);
        expect(signup.text()).toEqual("Sign Up");
    });

    it("should render first name label in <Signup /> component", () => {
        const firstName = wrapper.find(".signup-fname");
        expect(firstName).toHaveLength(1);
        expect(firstName.text()).toEqual("First name");
    });

    it("should render last name label in <Signup /> component", () => {
        const lastName = wrapper.find(".signup-lname");
        expect(lastName).toHaveLength(1);
        expect(lastName.text()).toEqual("Last name");
    });

    it("should render username label in <Signup /> component", () => {
        const username = wrapper.find(".signup-uname");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render password label in <Signup /> component", () => {
        const pwd = wrapper.find(".signup-pwd");
        expect(pwd).toHaveLength(1);
        expect(pwd.text()).toEqual("Password");
    });

    it("should render password confirmation label in <Signup /> component", () => {
        const pwdConfirm = wrapper.find(".signup-pwd-confirm");
        expect(pwdConfirm).toHaveLength(1);
        expect(pwdConfirm.text()).toEqual("Password confirmation");
    });

    it("should render submit button in <Signup /> component", () => {
        const submit = wrapper.find(".submit");
        expect(submit).toHaveLength(1);
        expect(submit.text()).toEqual("Submit");
    });

});