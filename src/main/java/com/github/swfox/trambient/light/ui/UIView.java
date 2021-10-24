package com.github.swfox.trambient.light.ui;

import com.github.swfox.trambient.light.ui.elements.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIView {
    final static Logger log = LoggerFactory.getLogger(UIController.class);

    private final ConnectionButton connectButton;
    private final JTextField ipInput;
    private final JPasswordField secretInput;
    private final StartButton runButton;

    public UIView(UIController controller, UIModel model) {

        //build UI
        JFrame frame = new JFrame("TrAmbient Light");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.setResizable(false);
        GridLayout layout = new GridLayout(0, 2, 10, 10);
        final JPanel grid = new JPanel(layout);

        //Line 1
        JLabel ipLabel = new JLabel("IP");
        ipInput = new ConnectionInput(model.getGatewayIp(), model.getIsConnected());
        grid.add(ipLabel);
        grid.add(ipInput);

        //Line 2
        JLabel secretLabel = new JLabel("Secret");
        secretInput = new ConnectionSecretInput(model.getGatewaySecret(), model.getIsConnected());
        grid.add(secretLabel);
        grid.add(secretInput);

        //Line 3
        JLabel emptyLabel = new JLabel("");
        connectButton = new ConnectionButton(controller, model);
        grid.add(emptyLabel);
        grid.add(connectButton);

        //Line 4
        JLabel choseLight = new JLabel("Licht wählen");
        LightSelector comboBox = new LightSelector(controller, model);
        grid.add(choseLight);
        grid.add(comboBox);

        //Line 5
        runButton = new StartButton(controller, model);
        grid.add(runButton);

        // Adds Button to content pane of frame
        frame.getContentPane().add(grid);
        frame.setVisible(true);
    }
}
