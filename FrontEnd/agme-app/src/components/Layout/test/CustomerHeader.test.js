import React from "react";
import CustomerHeader from '../js/CustomerHeader';
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

    it("should render profile label in <CustomerHeader /> component", () => {
        const profile = wrapper.find(".head-cust-profile");
        expect(profile).toHaveLength(1);
        expect(profile.text()).toEqual("Profile");
    });

    it("should render logout label in <CustomerHeader /> component", () => {
        const logout = wrapper.find(".head-cust-logout");
        expect(logout).toHaveLength(1);
        expect(logout.text()).toEqual("Logout");
    });
    
});