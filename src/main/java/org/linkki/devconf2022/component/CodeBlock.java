package org.linkki.devconf2022.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.theme.lumo.LumoUtility;

@StyleSheet("http://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/styles/a11y-light.min.css")
@JavaScript("http://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.5.1/highlight.min.js")
@Tag("pre")
public class CodeBlock extends Component implements HasText {

    public CodeBlock() {
        var codeElement = new Element("code");
        getElement().appendChild(codeElement);
        getElement().getClassList().add("language-java");
        getElement().getClassList().add(LumoUtility.Margin.NONE);
    }

    @Override
    public String getText() {
        return getElement().getChild(0).getText();
    }

    @Override
    public void setText(String text) {
        getElement().getChild(0).setText(text);
        getElement().executeJs("hljs.highlightAll();");
    }
}
