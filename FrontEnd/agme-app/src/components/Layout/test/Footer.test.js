import React from "react";
import Footer from '../js/Footer';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<Footer /> component Unit Test", () => {
    const wrapper = shallow(<Footer />);

    it("should render About & contact us in <Footer /> component", () => {
        const about_title = wrapper.find(".aboutcontact");
        expect(about_title).toHaveLength(1);
        expect(about_title.text()).toEqual("About & contact us");
    });

    it("should render title company's name with copyright in <Footer /> component", () => {
        const about_title = wrapper.find(".copyright");
        expect(about_title).toHaveLength(1);
        expect(about_title.text()).toEqual("© 2020 AGME");
    });
});