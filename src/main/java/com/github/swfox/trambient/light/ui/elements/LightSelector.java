package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.observer.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LightSelector extends JButton implements ActionListener, Observer {
    final static Logger log = LoggerFactory.getLogger(UIController.class);
    private final UIModel model;
    private final UIController controller;
    private final JPopupMenu popup;
    private List<JMenuItem> popupItems;

    public LightSelector(UIController controller, UIModel model) {
        super("Lampen wählen");
        this.controller = controller;
        this.model = model;
        this.addActionListener(this);
        this.setEnabled(false);
        model.getAllLights().registerObserver(this);
        model.getIsConnected().registerObserver(this);
        popup = new JPopupMenu();
        popupItems = new ArrayList<>();
    }

    @Override
    public void update() {
        this.setEnabled(model.getIsConnected().get());
        List<String> lights = model.getAllLights().get();
        List<String> selectedLights = model.getSelectedLights().get();
        popupItems = new ArrayList<>();
        popup.removeAll();
        for (String light : lights) {
            JMenuItem item = new JCheckBoxMenuItem(light);
            item.addActionListener(this);
            item.setSelected(selectedLights.contains(light));
            popupItems.add(item);
            popup.add(item);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            if (!popup.isVisible()) {
                Point p = this.getLocationOnScreen();
                popup.setInvoker(this);
                popup.setLocation((int) p.getX(),
                        (int) p.getY() + this.getHeight());
                popup.setVisible(true);
            } else {
                popup.setVisible(false);
            }
        } else if (popupItems.contains(e.getSource()) && e.getSource() instanceof JCheckBoxMenuItem) {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
            popup.show(this, 0, this.getHeight());
            log.debug("select light <{}> <{}>", item.getText(), item.isSelected() ? "enable" : "disable");
            List<String> selectedLights = new ArrayList<>(model.getSelectedLights().get());
            if (item.isSelected()) {
                selectedLights.add(item.getText());
            } else {
                selectedLights.remove(item.getText());
            }
            model.getSelectedLights().set(selectedLights);
            //TODO make controller use observer
            controller.selectLights();
        }
    }

}
