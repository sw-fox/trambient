package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.observer.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionButton extends JButton implements ActionListener, Observer {
    final static Logger log = LoggerFactory.getLogger(UIController.class);
    private final UIModel model;
    private final UIController controller;

    public ConnectionButton(UIController controller, UIModel model) {
        super("Verbinden");
        this.controller = controller;
        this.model = model;
        model.getIsConnected().registerObserver(this);
        this.addActionListener(this);
    }

    @Override
    public void update() {
        this.setText(model.getIsConnected().get() ? "Verbunden" : "Verbinden");
        this.setEnabled(!model.getIsConnected().get());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            log.debug("connectButton clicked!");
            controller.connect();
        }
    }
}
