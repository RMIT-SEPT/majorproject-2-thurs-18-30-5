import React from "react";
import LandingPage from '../js/LandingPage';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<LandingPage /> component Unit Test", () => {
    const wrapper = shallow(<LandingPage />);
    
    it("should render <LandingPage /> component", () => {
        const component = wrapper.find("div");
        expect(component.children()).toHaveLength(4);
    });
});