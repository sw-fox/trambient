package com.github.swfox.trambient;

import com.github.swfox.trambient.light.Configuration;
import com.github.swfox.trambient.light.Scheduler;
import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.UIView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        Configuration configuration = new Configuration();
        Scheduler scheduler = new Scheduler(configuration);
        UIModel uiModel = new UIModel(configuration);
        UIController uiController = new UIController(scheduler, configuration, uiModel);
        UIView uiView = new UIView(uiController,uiModel);
        log.info("initialized application");
    }
}
