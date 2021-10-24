package com.github.swfox.trambient.light.ui;

import com.github.swfox.trambient.light.Configuration;
import com.github.swfox.trambient.light.Scheduler;
import nl.stijngroenen.tradfri.device.Light;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class UIController {
    final static Logger log = LoggerFactory.getLogger(UIController.class);

    private final Scheduler scheduler;
    private final Configuration configuration;
    private final UIModel model;

    public UIController(Scheduler scheduler, Configuration configuration, UIModel model) {
        this.scheduler = scheduler;
        this.configuration = configuration;
        this.model = model;
        selectLights();
    }

    public void selectLights() {
        List<String> selectedItems = model.getSelectedLights().get();
        String selectedItemsAsString = String.join(Configuration.SELECTED_LIGHTS_SEPARATOR, selectedItems);
        log.debug("select Light " + selectedItemsAsString);
        boolean changedLight = scheduler.getController().selectLights(selectedItems);
        if (changedLight) {
            log.debug("save selected light <{}> to settings", selectedItemsAsString);
            configuration.set(Configuration.SELECTED_LIGHTS, selectedItemsAsString);
        }
        log.debug("changedLight status is " + changedLight);
    }

    public void connect() {
        String ip = model.getGatewayIp().get();
        String password = model.getGatewaySecret().get();
        log.debug("connectButton clicked!");
        scheduler.stop();
        boolean connected = scheduler.getController().connect(ip, password);
        if (connected) {
            model.getIsConnected().set(true);
            configuration.set(Configuration.GATEWAY_IP, ip);
            configuration.set(Configuration.GATEWAY_SECRET, password);
            List<String> lights = scheduler.getController().getLights().stream().map(Light::getName).collect(Collectors.toList());
            log.debug("set lights " + String.join(",", lights));
            model.getAllLights().set(lights);
        }
        log.debug("connection status is " + connected);
    }

    public void start() {
        log.debug("runButton clicked!");
        if (scheduler.isRunning()) {
            scheduler.stop();
            model.getIsRunning().set(false);
        } else {
            scheduler.start();
            model.getIsRunning().set(true);
        }
    }
}
