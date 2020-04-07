package com.soebes.performance.streams;

import java.util.List;

class FancyStuff {
    private Element element;
    private List<Element> elements;

    public FancyStuff(Element element, List<Element> elements) {
        this.element = element;
        this.elements = elements;
    }

    public Element getElement() {
        return element;
    }

    public List<Element> getElements() {
        return elements;
    }
}
