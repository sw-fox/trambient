package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.observer.Observer;
import com.github.swfox.trambient.light.ui.observer.ValueObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionInput extends JTextField implements ActionListener, Observer {
    final static Logger log = LoggerFactory.getLogger(UIController.class);
    private final ValueObservable<String> ip;
    private final ValueObservable<Boolean> isConnected;

    public ConnectionInput(ValueObservable<String> ip, ValueObservable<Boolean> isConnected) {
        super();
        this.ip = ip;
        this.isConnected = isConnected;
        this.setText(ip.get());
        this.addActionListener(this);
        this.isConnected.registerObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            log.debug("ip changed: " + this.getText());
            ip.set(this.getText());
        }
    }

    @Override
    public void update() {
        this.setEnabled(!isConnected.get());
    }
}
