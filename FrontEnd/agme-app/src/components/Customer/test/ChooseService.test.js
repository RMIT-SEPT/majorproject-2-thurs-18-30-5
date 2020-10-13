import React from "react";
import ChooseService from '../js/ChooseService';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<ChooseService /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<ChooseService location={mockLoc}/>);

    it("should render choose service title in <ChooseService /> component", () => {
        const serviceTitle = wrapper.find(".service-h3");
        expect(serviceTitle).toHaveLength(1);
        expect(serviceTitle.text()).toEqual("Choose a service");
    });

});