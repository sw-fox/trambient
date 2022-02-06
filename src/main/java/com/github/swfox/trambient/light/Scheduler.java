package com.github.swfox.trambient.light;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler {
    final static Logger log = LoggerFactory.getLogger(Scheduler.class);

    private final LightController controller;
    private final Screengraber grabber;
    private boolean running;
    private Screengraber.MODE mode;

    public Scheduler(Configuration configuration) {
        controller = new LightController(configuration);
        grabber = new Screengraber();
        running = false;
        mode = Screengraber.MODE.OUTER;
    }

    public void start() {
        running = true;
        Thread thread = new Thread(() -> {
            log.debug("Scheduler Running");
            while (running) {
                calculate();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("Scheduler stopped");
            controller.resetColor();
        });
        thread.start();

    }

    public void stop() {
        running = false;
    }

    public void calculate() {
        Color color = grabber.grab(mode);
        controller.setColor(color);
    }

    public LightController getController() {
        return controller;
    }

    public boolean isRunning() {
        return running;
    }

    public void setMode(Screengraber.MODE mode) {
        this.mode = mode;
    }
}
