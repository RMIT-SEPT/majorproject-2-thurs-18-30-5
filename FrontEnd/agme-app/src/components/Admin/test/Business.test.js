import React from "react";
import Business from '../js/Business';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<Business /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: "",
        business: ""
    }

    mockLoc.state.user = {
        business: ""
    }

    mockLoc.state.business = {
        description: ""
    }
   
    const wrapper = shallow(<Business location={mockLoc}/>);

    it("should render edit business title in <Business /> component", () => {
        const profileTitle = wrapper.find(".profile-title");
        expect(profileTitle).toHaveLength(1);
        expect(profileTitle.text()).toEqual("Edit business");
    });

    it("should render business name label in <Business /> component", () => {
        const bizName = wrapper.find(".username");
        expect(bizName).toHaveLength(1);
        expect(bizName.text()).toEqual("Business name");
    });

    it("should render business desc label in <Business /> component", () => {
        const bizDesc = wrapper.find(".name");
        expect(bizDesc).toHaveLength(1);
        expect(bizDesc.text()).toEqual("Description");
    });

    it("should render admin pwd label in <Business /> component", () => {
        const adminPwdTitle = wrapper.find(".admin-pwd-title");
        expect(adminPwdTitle).toHaveLength(1);
        expect(adminPwdTitle.text()).toEqual("ADMIN Password");
    });
});