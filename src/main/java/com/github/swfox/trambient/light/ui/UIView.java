package com.github.swfox.trambient.light.ui;

import com.github.swfox.trambient.light.ui.elements.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

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

        //set icon
        ArrayList<Image> icons = getIcons();
        frame.setIconImages(icons);

        frame.setVisible(true);

        checkForNewVersion(frame, model);
    }

    private void checkForNewVersion(JFrame frame, UIModel model) {
        if (model.isUpdateAvailable()) {
            String message = "Es ist ein Update verfügbar! Möchtest du das Update herunterladen?";
            int answer = JOptionPane.showConfirmDialog(frame, message, "Update", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(answer == JOptionPane.YES_OPTION){
                String updateUrl ="https://github.com/sw-fox/trambient/releases";
                boolean worked = openWebpage(updateUrl);
                if(!worked){
                    String goToUrl ="Browser kann nicht geöffnet werden, bitte rufe folgende URL auf: " + updateUrl;
                    JOptionPane.showConfirmDialog(frame, goToUrl, "Update", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

                }
            }
        }
    }
    public static boolean openWebpage(String uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(uri));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private ArrayList<Image> getIcons() {
        ArrayList<Image> list = new ArrayList<Image>();
        for (String size : new String[]{"32", "64", "128"}) {
            URL icon = getClass().getResource("/favicon_" + size + ".png");
            // iconURL is null when not found
            if (icon != null) {
                ImageIcon imageIcon = new ImageIcon(icon);
                list.add(imageIcon.getImage());
            }
        }
        return list;
    }
}
