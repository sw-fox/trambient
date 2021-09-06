package com.github.swfox.trambient.light;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class Configuration {
    final static Logger log = LoggerFactory.getLogger(LightController.class);
    private static final String FILENAME = "trambient.settings";
    private final Properties properties;

    public Configuration() {
        Reader reader = null;
        properties = new Properties();
        try {
            reader = new FileReader(FILENAME);
            properties.load(reader);
            log.debug("loaded properties from file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                log.error("error in closing reader");
            }
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if(value == null){
            return "";
        }
        return value;
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);

        //write
        Writer writer = null;
        try {
            writer = new FileWriter(FILENAME);
            properties.store(writer, "previous entered values");
            log.debug("stored properties to file");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                log.error("error in closing writer");
            }
        }
    }

}
