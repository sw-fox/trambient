package com.github.swfox.trambient;

import com.github.swfox.trambient.light.Configuration;
import com.github.swfox.trambient.light.Scheduler;
import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.UIView;
import com.github.swfox.trambient.light.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        Version version = new Version();
        log.info("initialized application");
        log.info("Version <{}> started", version.getVersion());
        log.info("Update available <{}> available version <{}>", version.isUdateAvailable(), version.getLatestVersion());

        Configuration configuration = new Configuration();
        Scheduler scheduler = new Scheduler(configuration);
        UIModel uiModel = new UIModel(configuration, version);
        UIController uiController = new UIController(scheduler, configuration, uiModel);
        UIView uiView = new UIView(uiController, uiModel);
    }
}
