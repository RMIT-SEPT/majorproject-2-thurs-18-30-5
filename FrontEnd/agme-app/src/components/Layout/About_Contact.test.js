import React from "react";
import About_Contact from './About_Contact';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<About_Contact /> component Unit Test", () => {
  const wrapper = shallow(<About_Contact />);

    it("should render title of about in <About_Contact /> component", () => {
        const about_title = wrapper.find(".about-title");
        expect(about_title).toHaveLength(1);
        expect(about_title.text()).toEqual("About");
    });

    it("should render information about about in <About_Contact /> component", () => {
        const about_title = wrapper.find(".about-content");
        expect(about_title).toHaveLength(1);
    });

    it("should render title of contact us in <About_Contact />", () => {
        const contact_title = wrapper.find(".contact-title");
        expect(contact_title).toHaveLength(1);
        expect(contact_title.text()).toEqual("Contact us");
    });

    it("should render information about conact us in <About_Contact /> component", () => {
        const about_title = wrapper.find(".contact-content");
        expect(about_title).toHaveLength(1);
    });
});