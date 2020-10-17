import React from "react";
import Login from '../js/Login';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<Login /> component Unit Test", () => {
  const wrapper = shallow(<Login />);

    it("should render login title in <Login /> component", () => {
        const login = wrapper.find(".login");
        expect(login).toHaveLength(1);
        expect(login.text()).toEqual("Login");
    });

    it("should render username label in <Login /> component", () => {
        const username = wrapper.find(".login-uname");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render password label in <Login /> component", () => {
        const pwd = wrapper.find(".login-pwd");
        expect(pwd).toHaveLength(1);
        expect(pwd.text()).toEqual("Password");
    });

    it("should render submit button in <Login /> component", () => {
        const submit = wrapper.find(".submit");
        expect(submit).toHaveLength(1);
        expect(submit.text()).toEqual("Submit");
    });

});