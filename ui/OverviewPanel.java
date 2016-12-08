package ui;

import system.Temperature;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

/**
 * Created by Lexie on 11/17/16.
 */
class OverviewPanel extends JPanel{

    private JPanel controlPanel;
    private JLabel system;
    private JLabel status;
    private JButton statusSwitch;
    private JLabel temperature;
    private JLabel curTemp;
    private JLabel tempUnit;
    private JButton higherTemp;
    private JButton lowerTemp;

    Font fontSmall = new Font("Georgia", Font.PLAIN, 18);
    Font fontTiny = new Font("Georgia", Font.PLAIN, 14);


    public OverviewPanel() {
        super();

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 1));
        createControlPanel();
        controlPanel.setBorder(new CompoundBorder(new LineBorder(Color.decode("#ebf5ff")), new EmptyBorder(10, 10, 10, 10)));
        add(controlPanel);
    }

    private void createControlPanel() {
        JPanel systemStatusPanel = createSysStatus();
        JPanel curTempPanel = createTemp();

        controlPanel.add(systemStatusPanel);
        controlPanel.add(curTempPanel);
    }

    private JPanel createSysStatus() {
        system = new JLabel("System Status: ");
        system.setFont(fontSmall);
        status = new JLabel();
        status.setFont(fontSmall);
        status.setForeground(Color.decode("#3e5266"));
        statusSwitch = new JButton();
        statusSwitch.setOpaque(true);
        statusSwitch.setFont(fontSmall);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(system);
        panel.add(status);
        panel.add(statusSwitch);

        TitledBorder border = new TitledBorder(new EtchedBorder(), "Status");
        panel.setBorder(border);
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(fontTiny);

        return panel;
    }

    private JPanel createTemp() {
        temperature = new JLabel("Current Temperature: ");
        temperature.setFont(fontSmall);
        curTemp = new JLabel();
        curTemp.setFont(fontSmall);
        curTemp.setForeground(Color.decode("#3e5266"));
        tempUnit = new JLabel("℉");
        tempUnit.setFont(fontSmall);
        higherTemp = new JButton("+");
        higherTemp.setFont(fontSmall);
        higherTemp.setForeground(Color.decode("#3e5266"));
        lowerTemp = new JButton("-");
        lowerTemp.setFont(fontSmall);
        lowerTemp.setForeground(Color.decode("#3e5266"));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(temperature);
        panel.add(curTemp);
        panel.add(tempUnit);
        panel.add(higherTemp);
        panel.add(lowerTemp);
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Temperature"));
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleFont(fontTiny);

        return panel;
    }

    public void showSysStatus(boolean isSysOn) {
        if (isSysOn) {
            status.setText("ON");
            statusSwitch.setText("TURN OFF");
        }
        else {
            status.setText("OFF");
            statusSwitch.setText("TURN ON");
        }
    }

    public void showSysTempValue(int tempValue) {
        curTemp.setText("" + tempValue);
    }

    public void addSysStatusListener(ActionListener listener) {
        statusSwitch.addActionListener(listener);
    }


    public void addSysTempChangeListener(ActionListener listener) {
        higherTemp.addActionListener(listener);
        lowerTemp.addActionListener(listener);
    }

}
