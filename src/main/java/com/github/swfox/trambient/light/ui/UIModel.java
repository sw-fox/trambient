package com.github.swfox.trambient.light.ui;

import com.github.swfox.trambient.light.Configuration;
import com.github.swfox.trambient.light.ui.observer.ValueObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UIModel {
    private final ValueObservable<String> gatewayIp;
    private final ValueObservable<String> gatewaySecrets;
    private final ValueObservable<List<String>> selectedLights;
    private final ValueObservable<List<String>> allLights;
    private final ValueObservable<Boolean> isConnected;
    private final ValueObservable<Boolean> isRunning;

    public UIModel(Configuration configuration) {
        gatewayIp = new ValueObservable<>(configuration.get(Configuration.GATEWAY_IP));
        gatewaySecrets = new ValueObservable<>(configuration.get(Configuration.GATEWAY_SECRET));
        selectedLights = new ValueObservable<>(Arrays.asList(configuration.get(Configuration.SELECTED_LIGHTS)
                .split(Configuration.SELECTED_LIGHTS_SEPARATOR)));
        allLights = new ValueObservable<>(new ArrayList<>());
        isConnected = new ValueObservable<>(false);
        isRunning = new ValueObservable<>(false);
    }

    public ValueObservable<String> getGatewayIp() {
        return gatewayIp;
    }

    public ValueObservable<String> getGatewaySecret() {
        return gatewaySecrets;
    }

    public ValueObservable<List<String>> getSelectedLights() {
        return selectedLights;
    }

    public ValueObservable<List<String>> getAllLights() {
        return allLights;
    }

    public ValueObservable<Boolean> getIsConnected() {
        return isConnected;
    }

    public ValueObservable<Boolean> getIsRunning() {
        return isRunning;
    }
}
