package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.geom.*;

class BarChart extends JPanel {
    private List<Integer> bars = new ArrayList<Integer>();
    Font fontBig = new Font("Georgia", Font.PLAIN, 22);
    int volumeWarning;

    public BarChart(int volumeWarning) {
        this.volumeWarning = volumeWarning;
        setPreferredSize(new Dimension(800,800));
    }
    /**
     * Add new bar to chart
     * @param value size of bar
     */
    public void addBar(int value) {
        bars.add(value);
        // cannot call paintComponent() or paint() directly
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // determine longest bar
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < bars.size(); i++) {
            max = Math.max(max, bars.get(i));
        }
        max = max + 10;
        // paint bars

        int width = (getWidth() / bars.size()) - 10;
        int x = 20;

        for (int i = 0; i < bars.size(); i++) {
            int value = bars.get(i);
            String valueInfo = Integer.toString(value);
            int height = (int) ((getHeight() - 50) * ((double)value / max));
            if (value > volumeWarning) {
                g.setColor(Color.decode("#ff9595"));
            }
            else {
                g.setColor(Color.decode("#9595ff"));
            }
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            g.setFont(fontBig);

            g.drawString(valueInfo, x, getHeight() - height - 10);
//            g.drawRect(x, getHeight() - height, width, height);
            x += (width + 2);
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bars.size() + 2, 50);
    }

}