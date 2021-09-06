package com.github.swfox.trambient;

import com.github.swfox.trambient.light.Configuration;
import com.github.swfox.trambient.light.Scheduler;
import com.github.swfox.trambient.light.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        Configuration configuration = new Configuration();
        Scheduler scheduler = new Scheduler(configuration);
        new UserInterface(scheduler, configuration);
        log.info("initialized application");
    }
}
