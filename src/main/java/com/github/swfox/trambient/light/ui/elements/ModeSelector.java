package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.Screengraber;
import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.observer.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelector extends JComboBox implements ActionListener, Observer {
    final static Logger log = LoggerFactory.getLogger(UIController.class);
    private final UIModel model;
    private final UIController controller;
    private final static String[] values = {"Mitte", "Außen", "Alles"};

    public ModeSelector(UIController controller, UIModel model) {
        super(values);
        this.setSelectedItem(values[1]);
        this.controller = controller;
        this.model = model;
        this.addActionListener(this);
        this.setEnabled(false);
        model.getIsConnected().registerObserver(this);
    }

    @Override
    public void update() {
        this.setEnabled(model.getIsConnected().get());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            Object selectedItem = this.getSelectedItem();
            if (selectedItem instanceof String) {
                Screengraber.MODE mode = translateMode((String) selectedItem);
                log.info("set mode to <{}>", mode);
                controller.setMode(mode);
            }
        }
    }

    private Screengraber.MODE translateMode(String selectedItem) {
        if(values[0].equals(selectedItem)){
            return Screengraber.MODE.MID;
        } else if(values[1].equals(selectedItem)){
            return Screengraber.MODE.OUTER;
        } else if(values[2].equals(selectedItem)){
            return Screengraber.MODE.ALL;
        }
        return Screengraber.MODE.ALL;
    }
}
