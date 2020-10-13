import React from "react";
import ChooseTime from '../js/ChooseTime';
import {mount, shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter()});

describe("<ChooseTime /> component Unit Test", () => {
    const mockLoc = jest.fn(); 
    mockLoc.state = {
        user: ""
    }
    const wrapper = shallow(<ChooseTime location={mockLoc}/>);

    it("should render choose date title in <ChooseTime /> component", () => {
        const dateTitle = wrapper.find(".date-h3");
        expect(dateTitle).toHaveLength(1);
        expect(dateTitle.text()).toEqual("Choose date and time");
    });

    it("should render start time title in <ChooseTime /> component", () => {
        const startTimeTitle = wrapper.find(".start-time-title");
        expect(startTimeTitle).toHaveLength(1);
        expect(startTimeTitle.text()).toEqual("Start time");
    });

    it("should render end time title in <ChooseTime /> component", () => {
        const endTimeTitle = wrapper.find(".end-time-title");
        expect(endTimeTitle).toHaveLength(1);
        expect(endTimeTitle.text()).toEqual("End time");
    });
});