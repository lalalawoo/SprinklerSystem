package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBPermission;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lexie on 12/2/16.
 */
class ImagePanel extends JPanel{

    Font fontSmall = new Font("Georgia", Font.PLAIN, 18);
    Font fontTiny = new Font("Georgia", Font.PLAIN, 14);
    JLabel picLabel;
    ImageIcon imageOn;
    ImageIcon imageOff;
    Map<String, Boolean[]> northSprinklerStatusMap;
    Map<String, Boolean[]> southSprinklerStatusMap;
    Map<String, Boolean[]> eastSprinklerStatusMap;
    Map<String, Boolean[]> westSprinklerStatusMap;

    JPanel northImagePanel;
    JPanel southImagePanel;
    JPanel eastImagePanel;
    JPanel westImagePanel;

    public ImagePanel() {

        super();
        setLayout(new GridLayout(3, 3));

//        northSprinklerStatusMap = getMapBasedOnGroupName("NORTH");
//        southSprinklerStatusMap = getMapBasedOnGroupName("SOUTH");
//        eastSprinklerStatusMap = getMapBasedOnGroupName("EAST");
//        westSprinklerStatusMap = getMapBasedOnGroupName("WEST");


//        imageOn = new ImageIcon(getClass().getResource("statusON.ico")); /Users/Lexie/GardenSprinkler/statusON.ico
//        imageOff = new ImageIcon(getClass().getResource("statusOFF.png"));
        imageOn = new ImageIcon("statusON.png");

        imageOff = new ImageIcon("statusOFF.png");

        // test



        // test

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBorder(new TitledBorder(new EtchedBorder(), "empty"));
        ((javax.swing.border.TitledBorder)emptyPanel1.getBorder()).setTitleFont(fontTiny);

        northImagePanel = new JPanel();
        northImagePanel.setBorder(new TitledBorder(new EtchedBorder(), "NORTH"));
        ((javax.swing.border.TitledBorder)northImagePanel.getBorder()).setTitleFont(fontTiny);

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBorder(new TitledBorder(new EtchedBorder(), "empty"));
        ((javax.swing.border.TitledBorder)emptyPanel2.getBorder()).setTitleFont(fontTiny);

        southImagePanel = new JPanel();
        southImagePanel.setBorder(new TitledBorder(new EtchedBorder(), "SOUTH"));
        ((javax.swing.border.TitledBorder)southImagePanel.getBorder()).setTitleFont(fontTiny);

        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setBorder(new TitledBorder(new EtchedBorder(), "empty"));
        ((javax.swing.border.TitledBorder)emptyPanel3.getBorder()).setTitleFont(fontTiny);

        eastImagePanel = new JPanel();
        eastImagePanel.setBorder(new TitledBorder(new EtchedBorder(), "EAST"));
        ((javax.swing.border.TitledBorder)eastImagePanel.getBorder()).setTitleFont(fontTiny);

        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setBorder(new TitledBorder(new EtchedBorder(), "empty"));
        ((javax.swing.border.TitledBorder)emptyPanel4.getBorder()).setTitleFont(fontTiny);

        westImagePanel = new JPanel();
        westImagePanel.setBorder(new TitledBorder(new EtchedBorder(), "WEST"));
        ((javax.swing.border.TitledBorder)westImagePanel.getBorder()).setTitleFont(fontTiny);

        JPanel emptyPanel5 = new JPanel();
        emptyPanel5.setBorder(new TitledBorder(new EtchedBorder(), "empty"));
        ((javax.swing.border.TitledBorder)emptyPanel5.getBorder()).setTitleFont(fontTiny);


        add(emptyPanel1);
        add(northImagePanel);
        add(emptyPanel2);
        add(eastImagePanel);
        add(emptyPanel3);
        add(westImagePanel);
        add(emptyPanel4);
        add(southImagePanel);
        add(emptyPanel5);
//        createImageBasedOnStatus();

    }

    public void getSprinklerStatusMap(String groupName, Map<String, Boolean[]> sprinklerStatusMap) {

        JPanel groupImagePanel = getGroupPanel(groupName);

        for (Map.Entry<String, Boolean[]> entry : sprinklerStatusMap.entrySet()) {
            JLabel jLabel = new JLabel();
            if (entry.getValue()[0]) {  // == true
                jLabel.setIcon(imageOn);
            }
            else {
                jLabel.setIcon(imageOff);
            }
//            jLabel.setPreferredSize(new Dimension(50, 50));

            jLabel.setText(entry.getKey());
            jLabel.setHorizontalTextPosition(JLabel.CENTER);
            jLabel.setVerticalTextPosition(JLabel.BOTTOM);
            groupImagePanel.add(jLabel);
            groupImagePanel.validate();
            groupImagePanel.repaint();
        }
    }

    public void updateSprinklerStatusMap(String groupName, Map<String, Boolean[]> sprinklerStatusMap) {

        JPanel groupImagePanel = getGroupPanel(groupName);

        for (Map.Entry<String, Boolean[]> entry : sprinklerStatusMap.entrySet()) {
            int count = 0;
            JLabel jLabel = (JLabel)groupImagePanel.getComponent(count++);
            if (entry.getValue()[0]) {  // == true
                jLabel.setIcon(imageOn);
            }
            else {
                jLabel.setIcon(imageOff);
            }
//            jLabel.setPreferredSize(new Dimension(50, 50));

            jLabel.setText(entry.getKey());
            jLabel.setHorizontalTextPosition(JLabel.CENTER);
            jLabel.setVerticalTextPosition(JLabel.BOTTOM);
            groupImagePanel.add(jLabel);

        }

    }

    private JPanel getGroupPanel(String groupName) {
        switch (groupName) {
            case "NORTH":
                return northImagePanel;
            case "SOUTH":
                return southImagePanel;
            case "EAST":
                return eastImagePanel;
            case "WEST":
                return westImagePanel;
            default:
                return null;
        }
    }

}
