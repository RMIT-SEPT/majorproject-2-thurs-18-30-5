import React from "react";
import ForgotPassword from './ForgotPassword';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<ForgotPassword /> component Unit Test", () => {
  const wrapper = shallow(<ForgotPassword />);

    it("should render forgot password title in <ForgotPassword /> component", () => {
        const forgot_pwd = wrapper.find(".forgot-pwd");
        expect(forgot_pwd).toHaveLength(1);
        expect(forgot_pwd.text()).toEqual("Forgot Password?");
    });

    it("should render email label in <ForgotPassword /> component", () => {
      const email = wrapper.find(".email");
      expect(email).toHaveLength(1);
      expect(email.text()).toEqual("Email address");
    });

    it("should render username label in <ForgotPassword /> component", () => {
        const username = wrapper.find(".username");
        expect(username).toHaveLength(1);
        expect(username.text()).toEqual("Username");
    });

    it("should render new password label in <ForgotPassword /> component", () => {
        const password = wrapper.find(".pwd");
        expect(password).toHaveLength(1);
        expect(password.text()).toEqual("New password");
    });

    it("should render new password confirmation label in <ForgotPassword /> component", () => {
        const pwd_confirm = wrapper.find(".pwd-confirm");
        expect(pwd_confirm).toHaveLength(1);
        expect(pwd_confirm.text()).toEqual("New password confirmation");
    });

    it("should render submit button in <ForgotPassword /> component", () => {
        const submit = wrapper.find(".submit");
        expect(submit).toHaveLength(1);
        expect(submit.text()).toEqual("Submit");
    });

});