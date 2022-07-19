package org.linkki.devconf2022.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Landing page for the presentation. Not a good example for anyone.
 */
@PageTitle("Pimp My linkki 2.1")
@Route(value = "")
public class HomeView extends VerticalLayout {

    public HomeView() {
        setAlignItems(Alignment.CENTER);
        getStyle().set("background", "linear-gradient(170deg, #fff 70%, rgb(248, 183, 38) 70%), linear-gradient(190deg, #fff 70%, rgb(248, 183, 38) 70%)");
        getStyle().set("background-blend-mode", "darken");
        getStyle().set("padding-bottom", "5em");
        setSizeFull();

        var content = new VerticalLayout();
        add(content);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setAlignItems(Alignment.CENTER);
        setFlexGrow(1, content);

        var logo = new Image("https://linkki-framework.org/images/linkki.png", "linkki-logo");
        logo.getStyle().set("max-width", "100px");
        content.add(logo);

        var title = new H1("Pimp My linkki 2.1");
        title.getStyle().set("color", "#f8b726");
        title.getStyle().set("font-weight", "normal");
        title.getStyle().set("margin-top", "0");
        title.getStyle().set("margin-bottom", ".2em");
        content.add(title);

        var button = new Button("Show Me How", VaadinIcon.ARROW_RIGHT.create(), e -> UI.getCurrent().navigate("/examples"));
        button.getStyle().set("outline", "1px #f8b726 solid");
        button.getStyle().set("--_lumo-button-color", "#f8b726");
        button.getStyle().set("--_lumo-button-background-color", "#fff");
        content.add(button);

        var author = new Span("Hengrui Jiang, 15.07.2022 @ linkki DevConf Sommer 2022");
        author.getStyle().set("color", "#fff");
        add(author);
    }
}
