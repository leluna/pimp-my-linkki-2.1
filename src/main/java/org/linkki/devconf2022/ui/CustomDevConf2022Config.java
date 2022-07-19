package org.linkki.devconf2022.ui;

import org.linkki.framework.ui.application.ApplicationConfig;
import org.linkki.framework.ui.application.menu.ApplicationMenuItemDefinition;
import org.linkki.util.Sequence;

public class CustomDevConf2022Config implements ApplicationConfig {

    @Override
    public CustomDevConf2022Info getApplicationInfo() {
        return new CustomDevConf2022Info();
    }

    @Override
    public Sequence<ApplicationMenuItemDefinition> getMenuItemDefinitions() {
        return Sequence.of(new ApplicationMenuItemDefinition("Pimp My linkki 2.1", "home", ""));
    }

}