import React from "react";
import WorkerHeader from '../js/WorkerHeader';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<WorkerHeader /> component Unit Test", () => {
    const wrapper = shallow(<WorkerHeader />);

    it("should render company's name AGME in <WorkerHeader /> component", () => {
        const company = wrapper.find(".navbar-brand");
        expect(company).toHaveLength(1);
        expect(company.text()).toEqual("AGME");
    });

    it("should render profile label in <WorkerHeader /> component", () => {
        const profile = wrapper.find(".head-worker-profile");
        expect(profile).toHaveLength(1);
        expect(profile.text()).toEqual("Profile");
    });

    it("should render logout label in <WorkerHeader /> component", () => {
        const logout = wrapper.find(".head-worker-logout");
        expect(logout).toHaveLength(1);
        expect(logout.text()).toEqual("Logout");
    });
    
});