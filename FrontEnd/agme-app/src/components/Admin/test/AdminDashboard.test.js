import React from "react";
import AdminDashboard from '../js/AdminDashboard';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<AdminDashboard /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<AdminDashboard location={mockLoc}/>);

    it("should render dashboard title in <AdminDashboard /> component", () => {
        const adminTitle = wrapper.find(".admin-title");
        expect(adminTitle).toHaveLength(1);
        expect(adminTitle.text()).toEqual("Admin Dashboard");
    });

    it("should render no worker message in <AdminDashboard /> component", () => {
        const noWorkerMsg = wrapper.find(".no-booking-msg");
        expect(noWorkerMsg).toHaveLength(1);
        expect(noWorkerMsg.text()).toEqual("No workers added");
    });
});