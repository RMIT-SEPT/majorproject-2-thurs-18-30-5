import React from "react";
import Header from '../js/Header';
import {shallow} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({ adapter: new Adapter( )});

describe("<Header /> component Unit Test", () => {
    const wrapper = shallow(<Header />);

    it("should render company's name AGME in <Header /> component", () => {
        const company = wrapper.find(".navbar-brand");
        expect(company).toHaveLength(1);
        expect(company.text()).toEqual("AGME");
    });

    it("should render login in <Header /> component", () => {
        const sign_in = wrapper.find(".sign-in");
        expect(sign_in).toHaveLength(0);
    });

    it("should render sign up in <Header /> component", () => {
        const sign_up = wrapper.find(".sign-up");
        expect(sign_up).toHaveLength(0);
    });
});