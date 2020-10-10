import React from "react";
import Admin from '../js/AdminHeader';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<AdminHeader /> component Unit Test", () => {
    const wrapper = shallow(<Admin />);

    it("should render company's name AGME in <AdminHeader /> component", () => {
        const company = wrapper.find(".navbar-brand");
        expect(company).toHaveLength(1);
        expect(company.text()).toEqual("AGME");
    });


    it("should render logout label in <AdminHeader /> component", () => {
        const logout = wrapper.find(".head-admin-logout");
        expect(logout).toHaveLength(1);
        expect(logout.text()).toEqual("Logout");
    });
    
});