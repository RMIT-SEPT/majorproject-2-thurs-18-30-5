import React from "react";
import Login from './Login';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<Login /> component Unit Test", () => {
  const wrapper = shallow(<Login />);

    it("should render login title in <Login /> component", () => {
        const login = wrapper.find(".login");
        expect(login).toHaveLength(1);
        expect(login.text()).toEqual("Login");
    });

    it("should render email or username label in <Login /> component", () => {
        const email_username = wrapper.find(".email-username");
        expect(email_username).toHaveLength(1);
        expect(email_username.text()).toEqual("Email / username");
    });

    it("should render password label in <Login /> component", () => {
        const password = wrapper.find(".pwd");
        expect(password).toHaveLength(1);
        expect(password.text()).toEqual("Password");
    });

    it("should render submit button in <Login /> component", () => {
        const submit = wrapper.find(".submit");
        expect(submit).toHaveLength(1);
        expect(submit.text()).toEqual("Submit");
    });

    it("should render forgot password button in <Login /> component", () => {
        const fgt_pwd = wrapper.find(".forgot-pwd-option");
        expect(fgt_pwd).toHaveLength(1);
        expect(fgt_pwd.text()).toEqual("Forgot password?");
    });

});