package com.github.swfox.trambient.light;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.device.Gateway;
import nl.stijngroenen.tradfri.device.Light;
import nl.stijngroenen.tradfri.util.ColourRGB;
import nl.stijngroenen.tradfri.util.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LightController {
    final static Logger log = LoggerFactory.getLogger(LightController.class);
    private final Configuration configuration;

    private Gateway gateway;
    private Light light = null;
    private ColourRGB previousColor = null;
    private boolean previousOn = false;

    public LightController(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean connect(String ip, String secureCode) {
        gateway = new Gateway(ip);
        Credentials cred;
        String identity = configuration.get("gateway.credentials.identity");
        String key = configuration.get("gateway.credentials.key");
        if (!identity.isEmpty() && !key.isEmpty()) {
            cred = new Credentials(identity,key);
            cred = gateway.connect(cred);
            log.info("connected to gateway via credentials");
        } else {
            cred = gateway.connect(secureCode);
            log.info("connected to gateway via securecode");
        }
        if (cred != null) {
            log.debug("connected to " + gateway.getDeviceIds().length + " devices");
            configuration.set("gateway.credentials.identity", cred.getIdentity());
            configuration.set("gateway.credentials.key", cred.getKey());
            return true;
        } else
            return false;
    }

    public void setColor(Color color) {
        if (light != null) {
            light.updateOn(true);
            light.updateColourRGB(color.getRed(), color.getGreen(), color.getBlue());
            light.updateTransitionTime(5);
            light.applyUpdates();
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
        light.setColour(previousColor);
        light.setOn(previousOn);
    }

    public boolean selectLight(String lightName) {
        if (gateway != null && lightName != null) {
            for (Device device : getLights()) {
                if (device.isLight() && device.getName().equals(lightName)) {
                    Light oldLight = light;
                    ColourRGB oldPreviousColor = previousColor;
                    boolean oldPreviousOn = previousOn;
                    previousColor = device.toLight().getColourRGB();
                    previousOn = device.toLight().getOn();
                    light = device.toLight();   //set this light
                    if (oldLight != null) {
                        oldLight.setColour(oldPreviousColor);
                        oldLight.setOn(oldPreviousOn);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
