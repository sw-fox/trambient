package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.observer.Observer;
import com.github.swfox.trambient.light.ui.observer.ValueObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionSecretInput extends JPasswordField implements ActionListener, Observer {
    final static Logger log = LoggerFactory.getLogger(UIController.class);
    private final ValueObservable<String> secret;
    private final ValueObservable<Boolean> isConnected;

    public ConnectionSecretInput(ValueObservable<String> secret, ValueObservable<Boolean> isConnected) {
        super();
        this.secret = secret;
        this.isConnected = isConnected;
        this.setText(secret.get());
        this.setEchoChar((char) 0);
        if (secret != null && !secret.get().isEmpty()) {
            this.setEchoChar('*');
        }
        this.addActionListener(this);
        this.isConnected.registerObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            log.debug("secret changed: ***");
            secret.set(new String(this.getPassword()));
        }
    }

    @Override
    public void update() {
        this.setEnabled(!isConnected.get());
        if (isConnected.get()) {
            this.setEchoChar('*');
        }
    }
}
