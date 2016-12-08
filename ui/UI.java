package ui;

import system.Schedule;
import system.SprinklerSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

/**
 * Created by Lexie on 11/17/16.
 */
public class UI extends JFrame {

    int height;
    int width;
    JTabbedPane tabbedPane;
    Container contentPane;

    OverviewPanel overviewPanel;
    StatusPanel statusPanel;
    ConfigPanel configPanel;
    ConsumPanel consumPanel;
    ImagePanel imagePanel;

    String northGroup = "NORTH";
    String southGroup = "SOUTH";
    String eastGroup = "EAST";
    String westGroup = "WEST";

    SprinklerSystem mySystem;

    public UI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height * 3 / 4;
        width = screenSize.width * 3 / 4;

        tabbedPane = new JTabbedPane();
        overviewPanel = new OverviewPanel();
        statusPanel = new StatusPanel();
        configPanel = new ConfigPanel();
        consumPanel = new ConsumPanel();
        imagePanel = new ImagePanel();

        tabbedPane.setFont(new Font("Georgia", Font.PLAIN, 20));
        tabbedPane.addTab("Overview", overviewPanel);
        tabbedPane.addTab("Status", statusPanel);
        tabbedPane.addTab("Configuration", configPanel);
        tabbedPane.addTab("Water Consumption", consumPanel);
        tabbedPane.addTab("Map", imagePanel);

        tabbedPane.addChangeListener(changeListener);

        contentPane = this.getContentPane();
        contentPane.add(tabbedPane);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("HummingBee Home Garden Sprinkler System");

        setVisible(true);

        mySystem = new SprinklerSystem();
    }

    private ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
            if (tabbedPane.getSelectedIndex() == 1) {
                statusPanel.showGroupStatus(mySystem.getGroupStatus());
                statusPanel.updateIndividualStatus(northGroup, mySystem.getSprinklerStatus(northGroup));
                statusPanel.updateIndividualStatus(southGroup, mySystem.getSprinklerStatus(southGroup));
                statusPanel.updateIndividualStatus(eastGroup, mySystem.getSprinklerStatus(eastGroup));
                statusPanel.updateIndividualStatus(westGroup, mySystem.getSprinklerStatus(westGroup));
                statusPanel.validate();
                statusPanel.repaint();
            }
            else if (tabbedPane.getSelectedIndex() == 3) {
                int[] sysWCArray = mySystem.getSysWCData();
                consumPanel.createBarChartByGroup("SYSTEM", sysWCArray);
                ((CardLayout)((JPanel)consumPanel.getComponent(1)).getLayout()).show((JPanel)consumPanel.getComponent(1), "SYSTEM");
                consumPanel.validate();
                consumPanel.repaint();
            }
            else if (tabbedPane.getSelectedIndex() == 4) {
                imagePanel.updateSprinklerStatusMap(northGroup, mySystem.getSprinklerStatus(northGroup));
                imagePanel.updateSprinklerStatusMap(southGroup, mySystem.getSprinklerStatus(southGroup));
                imagePanel.updateSprinklerStatusMap(eastGroup, mySystem.getSprinklerStatus(eastGroup));
                imagePanel.updateSprinklerStatusMap(westGroup, mySystem.getSprinklerStatus(westGroup));
                imagePanel.validate();
                imagePanel.repaint();
            }
        }
    };

    public void initialize() {
        overviewPanel.showSysStatus(mySystem.getSysStatus());
        overviewPanel.showSysTempValue(mySystem.getSysTemp());

        overviewPanel.addSysStatusListener(new SysStatusListener());
        overviewPanel.addSysTempChangeListener(new SysTempListener());

        statusPanel.addRefreshListener(new RefreshStatusListener());
        statusPanel.showGroupStatus(mySystem.getGroupStatus());
        statusPanel.createIndividualStatus(northGroup, mySystem.getSprinklerStatus(northGroup));
        statusPanel.createIndividualStatus(southGroup, mySystem.getSprinklerStatus(southGroup));
        statusPanel.createIndividualStatus(eastGroup, mySystem.getSprinklerStatus(eastGroup));
        statusPanel.createIndividualStatus(westGroup, mySystem.getSprinklerStatus(westGroup));

        statusPanel.addGroupStatusListener(northGroup, new GroupStatusListener());
        statusPanel.addGroupStatusListener(southGroup, new GroupStatusListener());
        statusPanel.addGroupStatusListener(eastGroup, new GroupStatusListener());
        statusPanel.addGroupStatusListener(westGroup, new GroupStatusListener());
        statusPanel.addIndividualStatusListener(northGroup, new IndividualStatusListener());
        statusPanel.addIndividualStatusListener(southGroup, new IndividualStatusListener());
        statusPanel.addIndividualStatusListener(eastGroup, new IndividualStatusListener());
        statusPanel.addIndividualStatusListener(westGroup, new IndividualStatusListener());

        configPanel.createEachScheduleShowPanel(northGroup, mySystem.getSchedule(northGroup));
        configPanel.createEachScheduleShowPanel(southGroup, mySystem.getSchedule(southGroup));
        configPanel.createEachScheduleShowPanel(eastGroup, mySystem.getSchedule(eastGroup));
        configPanel.createEachScheduleShowPanel(westGroup, mySystem.getSchedule(westGroup));

        configPanel.addAddConfigListener(new AddConfigListener());
        configPanel.addRefreshScheduleListener(northGroup, new RefreshScheduleListener());
        configPanel.addRefreshScheduleListener(southGroup, new RefreshScheduleListener());
        configPanel.addRefreshScheduleListener(eastGroup, new RefreshScheduleListener());
        configPanel.addRefreshScheduleListener(westGroup, new RefreshScheduleListener());
        configPanel.addSaveWaterVolumeListener(northGroup, new SaveGroupWaterVolumeListener());
        configPanel.addSaveWaterVolumeListener(southGroup, new SaveGroupWaterVolumeListener());
        configPanel.addSaveWaterVolumeListener(eastGroup, new SaveGroupWaterVolumeListener());
        configPanel.addSaveWaterVolumeListener(westGroup, new SaveGroupWaterVolumeListener());
        configPanel.addDeleteConfigListener(northGroup, new DeleteScheduleListener());
        configPanel.addDeleteConfigListener(southGroup, new DeleteScheduleListener());
        configPanel.addDeleteConfigListener(eastGroup, new DeleteScheduleListener());
        configPanel.addDeleteConfigListener(westGroup, new DeleteScheduleListener());
        configPanel.addUpdateTempLimitConfigListener(new UpdateTempLimitListener());

        consumPanel.createBarChartByGroup("SYSTEM", mySystem.getSysWCData());
        consumPanel.createBarChartByGroup(northGroup, mySystem.getGroupWCData(northGroup));
        consumPanel.createBarChartByGroup(southGroup, mySystem.getGroupWCData(southGroup));
        consumPanel.createBarChartByGroup(eastGroup, mySystem.getGroupWCData(eastGroup));
        consumPanel.createBarChartByGroup(westGroup, mySystem.getGroupWCData(westGroup));

        consumPanel.addGetSysConsumListener(new GetSysConsumptionListener());
        consumPanel.addGetGroupConsumListener(new GetGroupConsumptionListener());
        consumPanel.addGetGroupConsumListener(new GetGroupConsumptionListener());
        consumPanel.addGetGroupConsumListener(new GetGroupConsumptionListener());
        consumPanel.addGetGroupConsumListener(new GetGroupConsumptionListener());

        imagePanel.getSprinklerStatusMap(northGroup, mySystem.getSprinklerStatus(northGroup));
        imagePanel.getSprinklerStatusMap(southGroup, mySystem.getSprinklerStatus(southGroup));
        imagePanel.getSprinklerStatusMap(eastGroup, mySystem.getSprinklerStatus(eastGroup));
        imagePanel.getSprinklerStatusMap(westGroup, mySystem.getSprinklerStatus(westGroup));


    }

    class SysStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            String status = btn.getText();
            if (status.equals("TURN OFF")) {
                ((JLabel) btn.getParent().getComponent(1)).setText("OFF");
                btn.setText("TURN ON");
                mySystem.setSysStatus(false);
            } else {
                ((JLabel) btn.getParent().getComponent(1)).setText("ON");
                btn.setText("TURN OFF");
                mySystem.setSysStatus(true);
            }
        }
    }

    class SysTempListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            JLabel tempDisplay = (JLabel) btn.getParent().getComponent(1);
            int curTemp = Integer.valueOf(tempDisplay.getText());
            String btnString = btn.getText();
            if (btnString.equals("+")) {
                curTemp++;
            } else {
                curTemp--;
            }
            tempDisplay.setText("" + curTemp);
            mySystem.setCurrSysTemp(curTemp);
        }
    }

    class RefreshStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            statusPanel.showGroupStatus(mySystem.getGroupStatus());

            statusPanel.updateIndividualStatus(northGroup, mySystem.getSprinklerStatus(northGroup));
            statusPanel.updateIndividualStatus(southGroup, mySystem.getSprinklerStatus(southGroup));
            statusPanel.updateIndividualStatus(eastGroup, mySystem.getSprinklerStatus(eastGroup));
            statusPanel.updateIndividualStatus(westGroup, mySystem.getSprinklerStatus(westGroup));
            statusPanel.validate();
            statusPanel.repaint();
        }
    }

    class GroupStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            String status = btn.getText();
            String groupName = btn.getName();
            if (status.equals("ENABLE")) {
                ((JLabel) btn.getParent().getComponent(1)).setText("ON");
                btn.setText("DISABLE");
                mySystem.setGroupStatus(groupName, true);
            } else {
                ((JLabel) btn.getParent().getComponent(1)).setText("NOT ON");
                btn.setText("ENABLE");
                mySystem.setGroupStatus(groupName, false);
            }

        }
    }

    class IndividualStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            String status = btn.getText();
            String sprinklerID = btn.getName();
            if (status.equals("ENABLE")) {
                ((JLabel) btn.getParent().getComponent(1)).setText("ON");
                btn.setText("DISABLE");
                mySystem.setSprinklerStatus(sprinklerID, true);
            } else {
                ((JLabel) btn.getParent().getComponent(1)).setText("NOT ON");
                btn.setText("ENABLE");
                mySystem.setSprinklerStatus(sprinklerID, false);
            }
        }
    }

    class AddConfigListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            JPanel addPanel = (JPanel)btn.getParent();
            String groupName = ((JComboBox)addPanel.getComponent(1)).getSelectedItem().toString();
            int day = transferScheduleDayFromStringToInt(((JComboBox)addPanel.getComponent(4)).getSelectedItem().toString());
            int startHour = Integer.parseInt(((JComboBox)addPanel.getComponent(7)).getSelectedItem().toString());
            int startMin = Integer.parseInt(((JComboBox)addPanel.getComponent(9)).getSelectedItem().toString());
            int endHour = Integer.parseInt(((JComboBox)addPanel.getComponent(11)).getSelectedItem().toString());
            int endMin = Integer.parseInt(((JComboBox)addPanel.getComponent(13)).getSelectedItem().toString());

            mySystem.addSchedule(groupName, day, startHour, startMin, endHour, endMin);
            JOptionPane.showMessageDialog(null, "New schedule added");
        }
    }

    class RefreshScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton refreshBtn = (JButton)e.getSource();
            String groupName = refreshBtn.getParent().getParent().getName();
            configPanel.removeGroupScheduleShowPanel(groupName);
            configPanel.createEachScheduleShowPanel(groupName, mySystem.getSchedule(groupName));
            configPanel.addDeleteConfigListener(groupName, new DeleteScheduleListener());
            configPanel.validate();
            configPanel.repaint();

        }
    }

    class SaveGroupWaterVolumeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton saveBtn = (JButton)e.getSource();
            String groupName = saveBtn.getParent().getParent().getName();
            int volume = Integer.parseInt(((JComboBox)saveBtn.getParent().getComponent(1)).getSelectedItem().toString());
            mySystem.setWaterVolume(groupName, volume);
        }
    }

    class DeleteScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton deleteBtn = (JButton)e.getSource();
            JPanel parentPanel = (JPanel)deleteBtn.getParent();
//            parentPanel.
            JPanel masterPanel = (JPanel)parentPanel.getParent();
            String groupName = parentPanel.getParent().getName();
            String scheduleID = parentPanel.getComponent(0).getName();
            mySystem.deleteSchedule(groupName, scheduleID);

            masterPanel.remove(parentPanel);
            masterPanel.revalidate();
            masterPanel.repaint();
        }
    }

    class UpdateTempLimitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton updateBtn = (JButton)e.getSource();
            JPanel parentPanel = (JPanel)updateBtn.getParent();
            int upperTempLimit = Integer.parseInt(((JComboBox)parentPanel.getComponent(3)).getSelectedItem().toString());
            int lowerTempLimit = Integer.parseInt(((JComboBox)parentPanel.getComponent(6)).getSelectedItem().toString());
            mySystem.setMaxTemp(upperTempLimit);
            mySystem.setMinTemp(lowerTempLimit);

        }
    }

    class GetSysConsumptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sysNameBtn = (JButton)e.getSource();
            JPanel sysConsumPanel = consumPanel.getCardPanelByName(sysNameBtn.getName());
            int[] sysWCArray = mySystem.getSysWCData();
            // for test


            // end test

            consumPanel.createBarChartByGroup(sysNameBtn.getName(), sysWCArray);
            ((CardLayout)sysConsumPanel.getParent().getLayout()).show(sysConsumPanel.getParent(), sysNameBtn.getName());
            consumPanel.getComponent(1).validate();
            consumPanel.getComponent(1).repaint();

        }
    }

    class GetGroupConsumptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton groupNameBtn = (JButton)e.getSource();
            JPanel groupConsumPanel = consumPanel.getCardPanelByName(groupNameBtn.getName());
            int[] groupWCArray = mySystem.getGroupWCData(groupNameBtn.getName());
            consumPanel.createBarChartByGroup(groupNameBtn.getName(), groupWCArray);
            ((CardLayout)groupConsumPanel.getParent().getLayout()).show(groupConsumPanel.getParent(), groupNameBtn.getName());
            consumPanel.validate();
            consumPanel.repaint();
        }
    }

    private Integer transferScheduleDayFromStringToInt (String day) {
        switch (day) {
            case "Sunday":
                return 1;
            case "Monday":
                return 2;
            case "Tuesday":
                return 3;
            case "Wednesday":
                return 4;
            case "Thursday":
                return 5;
            case "Friday":
                return 6;
            case "Saturday":
                return 7;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        new UI().initialize();
    }


}
