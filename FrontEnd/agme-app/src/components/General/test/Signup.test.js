import React from "react";
import Signup from '../js/Signup';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<Signup /> component Unit Test", () => {
  const wrapper = shallow(<Signup />);

    it("should render sign up title in <Signup /> component", () => {
        const signup = wrapper.find(".signup");
        expect(signup).toHaveLength(1);
        expect(signup.text()).toEqual("Sign Up");
    });

    it("should render name label in <Signup /> component", () => {
        const name = wrapper.find(".name");
        expect(name).toHaveLength(2);
    });

    it("should render username label in <Signup /> component", () => {
        const username = wrapper.find(".username");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render password label in <Signup /> component", () => {
        const password = wrapper.find(".password");
        expect(password).toHaveLength(1);
        expect(password.text()).toEqual("Password");
    });

    it("should render password confirmation label in <Signup /> component", () => {
        const pwd_confirm = wrapper.find(".pwd-confirm");
        expect(pwd_confirm).toHaveLength(1);
        expect(pwd_confirm.text()).toEqual("Password confirmation");
    });

    it("should render submit button in <Signup /> component", () => {
        const submit = wrapper.find(".submit");
        expect(submit).toHaveLength(1);
        expect(submit.text()).toEqual("Submit");
    });

});