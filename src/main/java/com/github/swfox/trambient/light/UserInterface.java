package com.github.swfox.trambient.light;

import nl.stijngroenen.tradfri.device.Light;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class UserInterface implements ActionListener {
    final static Logger log = LoggerFactory.getLogger(UserInterface.class);

    private final Scheduler scheduler;
    private final Configuration configuration;

    private final JButton connectButton;
    private final JTextField ipInput;
    private final JPasswordField secretInput;
    private final JButton runButton;
    private final JComboBox comboBox;

    public UserInterface(Scheduler scheduler, Configuration configuration) {
        this.scheduler = scheduler;
        this.configuration = configuration;
        JFrame frame = new JFrame("TrAmbient Light");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.setResizable(false);
        GridLayout layout = new GridLayout(0, 2, 10, 10);
        final JPanel grid = new JPanel(layout);

        //Line 1
        JLabel ipLabel = new JLabel("IP");
        ipInput = new JTextField();
        ipInput.setText(configuration.get("gateway.ip"));
        grid.add(ipLabel);
        grid.add(ipInput);

        //Line 2
        JLabel secretLabel = new JLabel("Secret");
        secretInput = new JPasswordField();
        secretInput.setEchoChar((char)0);
        if (configuration.get("gateway.secret") != null && !configuration.get("gateway.secret").isEmpty()) {
            secretInput.setEchoChar('*');
        }
        secretInput.setText(configuration.get("gateway.secret"));
        grid.add(secretLabel);
        grid.add(secretInput);

        //Line 3
        JLabel emptyLabel = new JLabel("");
        connectButton = new JButton("Verbinden");
        connectButton.addActionListener(this);
        grid.add(emptyLabel);
        grid.add(connectButton);

        //Line 4
        JLabel choseLight = new JLabel("Licht wählen");
        comboBox = new JComboBox();
        comboBox.addActionListener(this);
        initCombobox();
        grid.add(choseLight);
        grid.add(comboBox);

        //Line 5
        runButton = new JButton("Start");
        runButton.addActionListener(this);
        runButton.setEnabled(false);
        grid.add(runButton);
        frame.getContentPane().add(grid); // Adds Button to content pane of frame
        frame.setVisible(true);
    }

    private void initCombobox() {
        List<String> lights = scheduler.getController().getLights().stream().map(Light::getName).collect(toList());
        String selectedLight = configuration.get("selected.light");
        log.debug("loaded following default light from settings <{}>", selectedLight);
        comboBox.removeAllItems();
        for (String light : lights) {
            comboBox.addItem(light);
        }
        if (selectedLight != null && !selectedLight.isEmpty() && lights.contains(selectedLight)) {
            log.debug("select loaded default light <{}>", selectedLight);
            comboBox.setSelectedItem(selectedLight);
            runButton.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton) {
            log.debug("runButton clicked!");
            if (scheduler.isRunning()) {
                scheduler.stop();
                runButton.setText("Start");
            } else {
                scheduler.start();
                runButton.setText("Stop");
            }
        } else if (e.getSource() == connectButton) {
            log.debug("connectButton clicked!");
            scheduler.stop();
            boolean connected = scheduler.getController().connect(ipInput.getText(), new String(secretInput.getPassword()));
            if (connected) {
                initCombobox();
                connectButton.setEnabled(false);
                connectButton.setText("Verbunden");
                configuration.set("gateway.ip", ipInput.getText());
                configuration.set("gateway.secret", new String(secretInput.getPassword()));
                secretInput.setEchoChar('*');
            }
            log.debug("connection status is " + connected);
        } else if (e.getSource() == comboBox) {
            log.debug("select Light " + comboBox.getSelectedItem());
            boolean changedLight = scheduler.getController().selectLight(comboBox.getSelectedItem().toString());
            if (changedLight) {
                log.debug("save selected light <{}> to settings", comboBox.getSelectedItem().toString());
                configuration.set("selected.light", comboBox.getSelectedItem().toString());
                runButton.setEnabled(true);
            }
            log.debug("changedLight status is " + changedLight);
        }
    }
}
