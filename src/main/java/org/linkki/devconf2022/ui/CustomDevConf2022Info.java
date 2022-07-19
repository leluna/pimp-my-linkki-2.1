package org.linkki.devconf2022.ui;

import java.time.Year;

import org.linkki.framework.ui.application.ApplicationInfo;

public class CustomDevConf2022Info implements ApplicationInfo {

    public static final String APPLICATION_NAME = "CustomDevConf2022";

    @Override
    public String getApplicationName() {
        return APPLICATION_NAME;
    }

    @Override
    public String getApplicationVersion() {
        return "1.0";
    }

    @Override
    public String getApplicationDescription() {
        return "CustomDevConf2022";
    }

    @Override
    public String getCopyright() {
        return "© Faktor Zehn " + Year.now();
    }
}