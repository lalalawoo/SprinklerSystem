package ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Lexie on 11/17/16.
 */
class ConsumPanel extends JPanel {
    JPanel groupSelectionPanel;
    private JPanel cardPanel;
    Font fontSmall = new Font("Georgia", Font.PLAIN, 18);
    Font fontTiny = new Font("Georgia", Font.PLAIN, 14);

    public ConsumPanel() {
        super();
        setLayout(new BorderLayout());

        cardPanel = new JPanel();
        CardLayout cards = new CardLayout();
        cardPanel.setLayout(cards);

        groupSelectionPanel = createGroupSelection();

        add(groupSelectionPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createGroupSelection() {
        JButton systemButton = new JButton("System");
        systemButton.setName("SYSTEM");
        systemButton.setFont(fontSmall);
        JButton northButton = new JButton("North");
        northButton.setName("NORTH");
        northButton.setFont(fontSmall);
        JButton southButton = new JButton("South");
        southButton.setName("SOUTH");
        southButton.setFont(fontSmall);
        JButton eastButton = new JButton("East");
        eastButton.setName("EAST");
        eastButton.setFont(fontSmall);
        JButton westButton = new JButton("West");
        westButton.setName("WEST");
        westButton.setFont(fontSmall);

        JPanel panel = new JPanel();
        panel.setBorder(new CompoundBorder(new LineBorder(Color.decode("#ebf5ff")), new EmptyBorder(10, 10, 10, 10)));
        panel.add(systemButton);
        panel.add(northButton);
        panel.add(southButton);
        panel.add(eastButton);
        panel.add(westButton);

        return panel;
    }

    public void createBarChartByGroup(String name, int[] consumption) {
//        System.out.println("CONSUMPTION 0" + consumption[0]);
        BarChart barChart = new BarChart(getHighWaterVolumeWarning(name));
        for(int i = 0; i < consumption.length; i++) {
            barChart.addBar(consumption[i]);
        }
        barChart.setBorder(new CompoundBorder(new LineBorder(Color.decode("#ebf5ff")), new EmptyBorder(10, 10, 10, 10)));

        cardPanel.add(barChart, name);

        if (name.equals("SYSTEM")) {
            ((CardLayout)cardPanel.getLayout()).show(cardPanel, name);
        }
    }

    private int getHighWaterVolumeWarning(String name) {
        switch (name) {
            case "SYSTEM":
                return 2500;
            default:
                return 500;
        }
    }

    public JPanel getCardPanelByName(String name) {
        switch (name) {
            case "SYSTEM":
                return (JPanel)cardPanel.getComponent(0);
            case "NORTH":
                return (JPanel)cardPanel.getComponent(1);
            case "SOUTH":
                return (JPanel)cardPanel.getComponent(2);
            case "EAST":
                return (JPanel)cardPanel.getComponent(3);
            case "WEST":
                return (JPanel)cardPanel.getComponent(4);
            default:
                System.out.println("Input name invalid");
                return null;
        }
    }

    public void addGetSysConsumListener(ActionListener listener) {
        ((JButton) groupSelectionPanel.getComponent(0)).addActionListener(listener);
    }

    public void addGetGroupConsumListener(ActionListener listener) {
        for (int i = 1; i < groupSelectionPanel.getComponentCount() - 1; i++) {
            ((JButton) groupSelectionPanel.getComponent(i)).addActionListener(listener);
        }
    }


}
