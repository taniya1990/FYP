/*
 * File:HeatMap.java
 *
 * Create a HeatMap based on Dow Jones Industrial
 * Average and various stock data
 *
 *  CS324E students fill in the following data:
 *
 * Assignment Number: 3
 * Name 1: --your name--
 * EID 1: --your eid--
 * Turnin ID 1: -- your turnin account ID --
 * Email 1: -- your email --
 *
 * Name 2: --your name--
 * EID 2: --your eid--
 * Turnin ID 2: -- your turnin account ID --
 * Email 2: -- your email --
 *
 * Course Name: CS CS324e
 *
 * Slip days used this assignment:
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class HeatMap {
    public static void main(String[] args) {
        boolean useFile = true; // set to false to use data from web
        HeatMapFrame f = new HeatMapFrame(useFile);
        f.start();
    }
}

class HeatMapFrame extends JFrame {

    // Titles of fundamental data itmes.
    // These will become the menu items for the heat map.
    public static final String[] dataItems = {"Percent Change",
        "Percent Volume", "Price to Earnings Ratio",
        "Market Capitalization", "Dividend Yield"};

    private HeatMapPanel panel;
    private String currentMenuSelection;

    public HeatMapFrame(boolean useFile) {
        setTitle("Stock Heat Map");
        setLocation(60, 60); // x, y
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMenu();
        currentMenuSelection = dataItems[0];

        // add a panel, boolean is to use file or get data from web
        panel = new HeatMapPanel(useFile, currentMenuSelection);
        add(panel);

        // adds button at bottom of frame
        add(createButton(), "South");

        this.setResizable(false);
        // make frame just big enough to hold children
        pack();
    }


    // create a menu of data items
    // return the label first item in the menu
    private void addMenu(){
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        JMenu menu = new JMenu("Stock Data Choice");
        mb.add(menu);

        for(String s : dataItems){
            JMenuItem mi = new JMenuItem(s);
            mi.addActionListener(getActionListener());
            menu.add(mi);
        }
    }

    private ActionListener getActionListener(){
        return new ActionListener(){
            public void actionPerformed(ActionEvent event){
                currentMenuSelection = event.getActionCommand();
                panel.setStockData(currentMenuSelection);
                panel.repaint();
            }
        };
    }

    private JButton createButton(){
        JButton result = new JButton("Update Values");
        result.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        result.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.updateStocks(currentMenuSelection);
                panel.repaint();
            }
        });
        return result;
    }

    public void start() {
        setVisible(true);
    }
}

class HeatMapPanel extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;
    private static final int LABEL_HEIGHT = 50;

    private static final int STOCKS_PER_ROW = 10;
    private static final int ROWS = 3;

    // CS324E students, add other constants here:

    // the "database" of stocks
    private Stocks stocks;

    // elements contain the symbol and data to display in each
    // cell. The size will equal STOCKS_PER_ROW * ROWS.
    // The elements will be in sorted order from best to worst.
    // This is the List that contains data that will be used
    // to determine what is rendered in paintComponent
    private List<SimpleStockData> currentStockData;

    // CS324E students, add appropriate instance variables here


    // useFile is passed on to Stocks
    // initialData is the initial value to use
    public HeatMapPanel(boolean useFile, String initialData) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        stocks = new Stocks(useFile);
        setStockData(initialData);
    }


    // Called when a menu item is selected.
    // Must pick the correct list of sorted data from the
    // stocks instance variable and decide if this is an absolute
    // data field (variable number of colors used)
    // or a relative data field (all colors used).
    // If absolute data field set the range.
    public void setStockData(String actionCommand) {
        currentStockData = stocks.getSortedData(actionCommand);

        // CS324E students, add requried actions here

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        // CS324E students had code to draw cells
        // (with symbols and data) and title here


    }

    // CS324E student add methods here


    public void updateStocks(String currentDataSelection) {
        stocks.updateStockData();
        setStockData(currentDataSelection);
    }
}