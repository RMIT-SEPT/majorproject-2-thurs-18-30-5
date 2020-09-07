import React from "react";
import CustomerHeader from './CustomerHeader';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<CustomerHeader /> component Unit Test", () => {
    const wrapper = shallow(<CustomerHeader />);

    it("should render company's name AGME in <CustomerHeader /> component", () => {
        const company = wrapper.find(".navbar-brand");
        expect(company).toHaveLength(1);
        expect(company.text()).toEqual("AGME");
    });

    it("should render login in <CustomerHeader /> component", () => {
        const profile = wrapper.find(".profile");
        expect(profile).toHaveLength(1);
        expect(profile.text()).toEqual("Profile");
    });

    it("should render sign up in <CustomerHeader /> component", () => {
        const logout = wrapper.find(".logout");
        expect(logout).toHaveLength(1);
        expect(logout.text()).toEqual("Logout");
    });
    
});