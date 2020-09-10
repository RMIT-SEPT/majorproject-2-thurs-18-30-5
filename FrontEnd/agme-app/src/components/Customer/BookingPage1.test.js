import React from "react";
import BookingPage1 from './BookingPage1';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<BookinggPage1 /> component Unit Test", () => {
    const wrapper = shallow(<BookingPage1 />);
    
    it("should render background image component of <BookingPage1 /> component", () => {
        const img = wrapper.find(".img-bg");
        expect(img.children()).toHaveLength(1);
    });

    it("should render wrapper component of <BookingPage1 /> component", () => {
        const wrp = wrapper.find(".auth-wrapper");
        expect(wrp.children()).toHaveLength(2);
    });

    it("should render inner component of <BookingPage1 /> component", () => {
        const inner = wrapper.find(".auth-inner");
        expect(inner.children()).toHaveLength(2);
    });

    it("should render service form of <BookingPage1 /> component", () => {
        const form = wrapper.find(".service-form");
        expect(form.children()).toHaveLength(7);
    });

    it("should render service h3 of <BookingPage1 /> component", () => {
        const h3 = wrapper.find(".service-h3");
        expect(h3.children()).toHaveLength(1);
        expect(h3.text()).toEqual("Choose a service");
    });
});