package com.github.swfox.trambient.light;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.device.Gateway;
import nl.stijngroenen.tradfri.device.Light;
import nl.stijngroenen.tradfri.util.ColourRGB;
import nl.stijngroenen.tradfri.util.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class LightController {
    final static Logger log = LoggerFactory.getLogger(LightController.class);
    private final Configuration configuration;

    private Gateway gateway;
    private List<Light> selectedLights;
    private Map<String, ColourRGB> previousColors;
    private Map<String, Boolean> previousStates;

    public LightController(Configuration configuration) {
        this.configuration = configuration;
        selectedLights = new ArrayList<>();
        previousColors = new HashMap<>();
        previousStates = new HashMap<>();
    }

    public boolean connect(String ip, String secureCode) {
        gateway = new Gateway(ip);
        Credentials cred;
        String identity = configuration.get(Configuration.GATEWAY_CREDENTIALS_IDENTITY);
        String key = configuration.get(Configuration.GATEWAY_CREDENTIALS_KEY);
        if (!identity.isEmpty() && !key.isEmpty()) {
            cred = new Credentials(identity, key);
            cred = gateway.connect(cred);
            log.info("connected to gateway via credentials");
        } else {
            cred = gateway.connect(secureCode);
            log.info("connected to gateway via securecode");
        }
        if (cred != null) {
            log.debug("connected to " + gateway.getDeviceIds().length + " devices");
            configuration.set(Configuration.GATEWAY_CREDENTIALS_IDENTITY, cred.getIdentity());
            configuration.set(Configuration.GATEWAY_CREDENTIALS_KEY, cred.getKey());

            selectLights(Arrays.asList(this.configuration.get(Configuration.SELECTED_LIGHTS).split(Configuration.SELECTED_LIGHTS_SEPARATOR)));
            return true;
        } else
            return false;
    }

    public void setColor(Color color) {
        if (selectedLights != null) {
            for (Light light : selectedLights) {
                light.updateOn(true);
                light.updateColourRGB(color.getRed(), color.getGreen(), color.getBlue());
                light.updateTransitionTime(5);
                light.applyUpdates();
            }
        }
    }

    public List<Light> getLights() {
        List<Light> lights = new ArrayList();
        if (gateway != null) {
            for (Device device : gateway.getDevices()) {
                if (device.isLight()) {
                    lights.add(device.toLight());
                }
            }
        }
        return lights;
    }

    public void resetColor() {
        if (selectedLights != null && previousColors != null) {
            for (Light light : selectedLights) {
                resetLight(light);
            }
        }
    }

    private void resetLight(Light light) {
        if (previousStates.containsKey(light.getName())) {
            light.setOn(previousStates.get(light.getName()));
        }
        if (previousColors.containsKey(light.getName())) {
            light.setColour(previousColors.get(light.getName()));
        }
    }

    public boolean selectLights(List<String> uiSelectedLights) {
        List<String> addedLights = uiSelectedLights.stream().filter(l -> !selectedLights.contains(l)).collect(Collectors.toList());
        List<Light> removedLights = selectedLights.stream().filter(l -> !uiSelectedLights.contains(l)).collect(Collectors.toList());

        log.debug("remove Lights <{}>", removedLights.stream().map(Device::getName).collect(Collectors.joining(",")));
        //reset removed lights
        for (Light light : removedLights) {
            resetLight(light);
            selectedLights.remove(light);
        }

        //add new lights
        log.debug("add Lights <{}>", String.join(",", addedLights));
        List<Light> newLights = getLights().stream().filter(l -> addedLights.contains(l.getName())).collect(Collectors.toList());
        for (Light light : newLights) {
            previousStates.put(light.getName(), light.getOn());
            previousColors.put(light.getName(), light.getColourRGB());
            selectedLights.add(light);
        }
        return !addedLights.isEmpty() || !removedLights.isEmpty();
    }
}
