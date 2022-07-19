package org.linkki.devconf2022.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.ui.creation.VaadinUiCreator;
import org.linkki.core.vaadin.component.tablayout.LinkkiTabLayout;
import org.linkki.core.vaadin.component.tablayout.LinkkiTabSheet;
import org.linkki.devconf2022.pmo.*;
import org.linkki.devconf2022.ui.CustomDevConf2022AppLayout;

@PageTitle("Pimp My linkki 2.1")
@Route(value = "examples", layout = CustomDevConf2022AppLayout.class)
public class ExamplesView extends VerticalLayout implements HasUrlParameter<String> {

    private static final long serialVersionUID = 1L;
    private final LinkkiTabLayout linkkiTabLayout;

    public ExamplesView() {
        linkkiTabLayout = LinkkiTabLayout.newSidebarLayout();
        linkkiTabLayout.addTabSheets(
                createExampleSheet("suffix",
                        "@BindSuffix",
                        "Custom component with @BindSuffix",
                        new BindSuffixPmo()),
                createExampleSheet("vaadin-component",
                        "Vaadin Component",
                        "Creating a linkki annotation for a Vaadin component",
                        new VaadinComponentPmo()),
                createExampleSheet("nested-component",
                        "@UINestedComponent",
                        "Good old (new?) friend: @UINestedComponent",
                        new NestedComponentPmo()),
                createExampleSheet("nested-component-layout",
                        "More with @UINestedComponents",
                        "More with @UINestedComponent",
                        new NestedComponentLayoutPmo())
                );
        add(linkkiTabLayout);

        setPadding(false);
        setSizeFull();
    }

    private LinkkiTabSheet createExampleSheet(String id, String caption, String title, Object pmo) {
        return LinkkiTabSheet.builder(id)
                .caption(new Anchor("/examples/" + id, caption))
                .content(() -> VaadinUiCreator.createComponent(new ExamplePmo(title, pmo), new BindingContext(id)))
                .build();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @WildcardParameter String s) {
        if (!s.isEmpty()) {
            linkkiTabLayout.setSelectedTabSheet(new Location(s).getFirstSegment());
        } else {
            linkkiTabLayout.setSelectedIndex(0);
        }
    }
}