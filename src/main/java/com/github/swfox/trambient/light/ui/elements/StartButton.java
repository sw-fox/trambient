package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.observer.Observer;
import com.github.swfox.trambient.light.ui.observer.ValueObservable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StartButton extends JButton implements ActionListener, Observer {
    private final ValueObservable<Boolean> isRunning;
    private final ValueObservable<Boolean> isConnected;
    private final ValueObservable<List<String>> selectedLights;
    private final UIController controller;

    public StartButton(UIController controller, UIModel model) {
        super("Start");
        this.setEnabled(false);
        this.controller = controller;
        this.isRunning = model.getIsRunning();
        this.isConnected = model.getIsConnected();
        this.selectedLights = model.getSelectedLights();
        isRunning.registerObserver(this);
        isConnected.registerObserver(this);
        selectedLights.registerObserver(this);
        this.addActionListener(this);
    }

    @Override
    public void update() {
        this.setText(isRunning.get() ? "Stop" : "Start");
        this.setEnabled(isConnected.get() && isLightSelected());
    }

    private boolean isLightSelected() {
        return selectedLights.get() != null && !selectedLights.get().isEmpty();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            controller.start();
        }
    }
}
