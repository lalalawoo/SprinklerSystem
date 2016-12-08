package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Lexie on 11/17/16.
 */
class StatusPanel extends JPanel {

    private JButton refreshBtn;

    private JPanel masterPaneNorth;
    private JPanel masterPaneSouth;
    private JPanel masterPaneEast;
    private JPanel masterPaneWest;

    Font fontSmall = new Font("Georgia", Font.PLAIN, 18);
    Font fontTiny = new Font("Georgia", Font.PLAIN, 14);

    public StatusPanel() {
        super();
        setLayout(new BorderLayout());

        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(fontSmall);
        refreshPanel.add(refreshBtn);

        add(refreshPanel, BorderLayout.NORTH);

        masterPaneNorth = createPanel();
        masterPaneEast = createPanel();
        masterPaneSouth = createPanel();
        masterPaneWest = createPanel();

        JScrollPane scrollPaneNorth = new JScrollPane(masterPaneNorth);
        JScrollPane scrollPaneSouth = new JScrollPane(masterPaneSouth);
        JScrollPane scrollPaneEast = new JScrollPane(masterPaneEast);
        JScrollPane scrollPaneWest = new JScrollPane(masterPaneWest);

        JPanel masterPaneGroup = new JPanel();
        masterPaneGroup.setLayout(new GridLayout(2, 2));
        masterPaneGroup.add(scrollPaneNorth);
        masterPaneGroup.add(scrollPaneSouth);
        masterPaneGroup.add(scrollPaneEast);
        masterPaneGroup.add(scrollPaneWest);

        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel note = new JLabel("*You can not change status of those sprinklers that are not working");
        note.setFont(fontTiny);
        notePanel.add(note);

        add(masterPaneGroup, BorderLayout.CENTER);
        add(notePanel, BorderLayout.SOUTH);
    }

//    public void updateStatus() {
//        masterPaneNorth.invalidate();
//        masterPaneNorth.repaint();
//
//        masterPaneEast.invalidate();
//        masterPaneEast.repaint();
//
//        masterPaneSouth.invalidate();
//        masterPaneSouth.repaint();
//
//        masterPaneWest.invalidate();
//        masterPaneWest.repaint();
//
//    }
    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel groupName = new JLabel("Group ");
        groupName.setFont(fontSmall);
        JLabel groupStatus = new JLabel();
        groupStatus.setFont(fontSmall);
        JButton statusChangeBtn = new JButton();
        statusChangeBtn.setFont(fontSmall);
        groupPanel.add(groupName);
        groupPanel.add(groupStatus);
        groupPanel.add(statusChangeBtn);

        panel.add(groupPanel);
        return panel;
    }

    public void showGroupStatus(Map<String, Boolean> groupStatusMap) {
        for (Map.Entry<String, Boolean> entry : groupStatusMap.entrySet()) {
            JPanel parentPanel = (JPanel)getPanelBasedOnName(entry.getKey()).getComponent(0);
            switch (entry.getKey()) {
                case "NORTH":
                    parentPanel.getComponent(2).setName("NORTH");
                    getPanelBasedOnName(entry.getKey()).setBorder(new TitledBorder(new EtchedBorder(), "NORTH"));
                    ((javax.swing.border.TitledBorder)getPanelBasedOnName(entry.getKey()).getBorder()).setTitleFont(fontTiny);
                    break;
                case "SOUTH":
                    parentPanel.getComponent(2).setName("SOUTH");
                    getPanelBasedOnName(entry.getKey()).setBorder(new TitledBorder(new EtchedBorder(), "SOUTH"));
                    ((javax.swing.border.TitledBorder)getPanelBasedOnName(entry.getKey()).getBorder()).setTitleFont(fontTiny);
                    break;
                case "EAST":
                    parentPanel.getComponent(2).setName("EAST");
                    getPanelBasedOnName(entry.getKey()).setBorder(new TitledBorder(new EtchedBorder(), "EAST"));
                    ((javax.swing.border.TitledBorder)getPanelBasedOnName(entry.getKey()).getBorder()).setTitleFont(fontTiny);
                    break;
                case "WEST":
                    parentPanel.getComponent(2).setName("WEST");
                    getPanelBasedOnName(entry.getKey()).setBorder(new TitledBorder(new EtchedBorder(), "WEST"));
                    ((javax.swing.border.TitledBorder)getPanelBasedOnName(entry.getKey()).getBorder()).setTitleFont(fontTiny);
                    break;
                default:
                    System.out.println("No matching group names");
            }
            ((JLabel)parentPanel.getComponent(1)).setText(entry.getValue() ? "ON" : "NOTON");
            ((JButton)parentPanel.getComponent(2)).setText(entry.getValue() ? "DISABLE" : "ENABLE");
        }
    }

    private JPanel getPanelBasedOnName(String name) {
        switch (name) {
            case "NORTH":
                return masterPaneNorth;
            case "SOUTH":
                return masterPaneSouth;
            case "EAST":
                return masterPaneEast;
            case "WEST":
                return masterPaneWest;
            default:
                System.out.println("No matching group names");
                return new JPanel();
        }
    }

    public void createIndividualStatus(String groupName, Map<String, Boolean[]> northEachStatus) {
        JPanel panel = getPanelBasedOnName(groupName);
        for (Map.Entry<String, Boolean[]> entry : northEachStatus.entrySet()) {
            JPanel individualPanel = createIndividualPanel(entry.getKey(), entry.getValue());
            panel.add(individualPanel);
        }
    }

    public void updateIndividualStatus(String groupName, Map<String, Boolean[]> groupEachStatus) {
        JPanel panel = getPanelBasedOnName(groupName);
        int count = 1;
        for (Map.Entry<String, Boolean[]> entry : groupEachStatus.entrySet()) {
            JPanel individualPanel = (JPanel) panel.getComponent(count);
            count++;

            String sprinklerID = entry.getKey();
            Boolean[] sprinklerStatusMap = entry.getValue();

            JLabel sprinklerName = (JLabel) individualPanel.getComponent(0);
            sprinklerName.setText(sprinklerID);
            sprinklerName.setFont(fontSmall);

            JLabel sprinklerCurrentlyOn = (JLabel) individualPanel.getComponent(1);
            sprinklerCurrentlyOn.setText(sprinklerStatusMap[0] ? "ON" : "NOTON");
            sprinklerCurrentlyOn.setFont(fontSmall);

            JLabel sprinklerFunctional = (JLabel) individualPanel.getComponent(2);
            sprinklerFunctional.setText(sprinklerStatusMap[1] ? "OK" : "NOTOK*");
            sprinklerFunctional.setFont(fontSmall);

            JButton sprinklerStatusChange = (JButton) individualPanel.getComponent(3);
            sprinklerStatusChange.setText(sprinklerStatusMap[0] ? "DISABLE" : "ENABLE");
            sprinklerStatusChange.setName(sprinklerID);
            sprinklerStatusChange.setFont(fontSmall);
        }
    }

    private JPanel createIndividualPanel(String sprinklerID, Boolean[] sprinklerStatusMap) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel sprinklerName = new JLabel();
        sprinklerName.setText(sprinklerID);
        sprinklerName.setFont(fontSmall);

        JLabel sprinklerCurrentlyOn = new JLabel();
        sprinklerCurrentlyOn.setText(sprinklerStatusMap[0] ? "ON" : "NOTON");
        sprinklerCurrentlyOn.setFont(fontSmall);

        JLabel sprinklerFunctional = new JLabel();
        sprinklerFunctional.setText(sprinklerStatusMap[1] ? "OK" : "NOTOK*");
        sprinklerFunctional.setFont(fontSmall);

        JButton sprinklerStatusChange = new JButton();
        sprinklerStatusChange.setText(sprinklerStatusMap[0] ? "DISABLE" : "ENABLE");
        sprinklerStatusChange.setName(sprinklerID);
        sprinklerStatusChange.setFont(fontSmall);
        if (!sprinklerStatusMap[1]) {
            sprinklerStatusChange.setEnabled(false);
        }


        panel.add(sprinklerName);
        panel.add(sprinklerCurrentlyOn);
        panel.add(sprinklerFunctional);
        panel.add(sprinklerStatusChange);
        return panel;
    }

    public void addIndividualStatusListener(String groupName, ActionListener listener) {
        JPanel panel = getPanelBasedOnName(groupName);

        for (int i = 1; i < panel.getComponentCount(); i++) {
            JPanel individualPanel = (JPanel)getPanelBasedOnName(groupName).getComponent(i);
            ((JButton)individualPanel.getComponent(3)).addActionListener(listener);
        }
    }
    public void addGroupStatusListener(String groupName, ActionListener listener) {
        JPanel panel = (JPanel)getPanelBasedOnName(groupName).getComponent(0);
        ((JButton)panel.getComponent(2)).addActionListener(listener);
    }

    public void addRefreshListener(ActionListener listener) {
        refreshBtn.addActionListener(listener);
    }
}
