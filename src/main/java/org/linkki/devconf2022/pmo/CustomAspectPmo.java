package org.linkki.devconf2022.pmo;

import org.linkki.core.ui.aspects.annotation.BindPlaceholder;
import org.linkki.core.ui.element.annotation.UILabel;
import org.linkki.core.ui.element.annotation.UITextField;
import org.linkki.core.ui.layout.annotation.UISection;

@UISection
public class CustomAspectPmo {

    private String name;

    public CustomAspectPmo() {
    }

    @BindPlaceholder("Name")
    @UITextField(position = 10, label = "Please type your name and press Enter")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @UILabel(position = 20)
    public String getHello() {
        if (name != null) {
            return "Hello " + name + "! This is a linkki example web application running on Spring Boot.";
        } else {
            return "";
        }
    }
}
