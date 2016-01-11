/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphic3;

/**
 *
 * @author Tani
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Tani
 */
public class Main_Grapic4 extends JFrame implements ActionListener {

    private JTextField x2Field, y2Field, x1Field, y1Field;
    private JTextField xRoute, yRoute;
    private JButton add_router;
    private JButton button;
    private JButton model;
    private JButton clear;
    private ModelJPanel templatePanel;
    private JMenuBar menuBar;
    private JMenu menuFile, menuOption, menuEdit;
    private JMenuItem item1, menuOpProp;
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    JTextField field5 = new JTextField();

    public Main_Grapic4() {
        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);


        GridBagConstraints constraints = new GridBagConstraints();

        x1Field = new JTextField();
        y1Field = new JTextField();
        x2Field = new JTextField();
        y2Field = new JTextField();

        xRoute = new JTextField();
        yRoute = new JTextField();






        JPanel optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setPreferredSize(new Dimension(250, 200));

        menuBar = new JMenuBar();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0;
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        item1 = new JMenuItem("save");
        menuFile.add(item1);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        menuOption = new JMenu("Options");
        menuBar.add(menuOption);
        menuOpProp = new JMenuItem("properties");
        menuOption.add(menuOpProp);
        menuOpProp.addActionListener(this);

        setJMenuBar(menuBar);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 5, 5, 5);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("x1:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(x1Field, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("y1:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(y1Field, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("x2:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(x2Field, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("y2:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(y2Field, constraints);

        button = new JButton("Add Wall");
        constraints.gridy = 5;
        constraints.gridx = 1;
        //constraints.gridwidth = 1;
        button.addActionListener(this);
        optionsPanel.add(button, constraints);


        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("Router Point x :"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(xRoute, constraints);

        constraints.gridy = 7;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("Router Point y :"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(yRoute, constraints);


        add_router = new JButton("Draw Router");
        constraints.gridx = 1;
        constraints.gridy = 8;
        add_router.addActionListener(this);
        optionsPanel.add(add_router, constraints);

        model = new JButton("Model OK");
        constraints.gridx = 1;
        constraints.gridy = 9;
        model.addActionListener(this);
        optionsPanel.add(model, constraints);

        clear = new JButton("Clear");
        constraints.gridx = 1;
        constraints.gridy = 10;
        clear.addActionListener(this);
        optionsPanel.add(clear, constraints);


        //empty label
        constraints.gridx = 1;
        constraints.gridy = 11;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        optionsPanel.add(new JLabel(), constraints);


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.gridwidth = 1;

        getContentPane().add(optionsPanel, constraints);

        templatePanel = new ModelJPanel();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setViewportView(templatePanel);

        x1Field.setText("50");

        x2Field.setText("100");

        y1Field.setText("50");

        y2Field.setText("50");

        xRoute.setText("55");
        yRoute.setText("70");

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;

        getContentPane().add(scrollPane, constraints);

        setTitle("Modelling Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    /** The entry main() method */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety

//        System.out.println(4 *  f* 1 * Math.PI);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Main_Grapic4 main = new Main_Grapic4();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {

            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());
            int y2 = Integer.parseInt(y2Field.getText());
            templatePanel.addWallToGrid(x1, y1, x2, y2);
        }

        if (e.getSource() == add_router) {
            int x = Integer.parseInt(xRoute.getText());
            int y = Integer.parseInt(yRoute.getText());
            templatePanel.addRouterToGrid(x, y);
        }

        if (e.getSource() == model) {
            System.out.println("model started");
            templatePanel.paint = true;
            templatePanel.addHeatMapToGrid();
            System.out.println("model ended");


        }


        if (e.getSource() == clear) {
            templatePanel.clear();
        }

        field1.setText("0");
        field2.setText("900");
        field3.setText("3.7");

        Object[] message = {
            "Transmitted Power (dBm):", field1,
            "Frequency (MHz):", field2,
            "Gamma Value:", field3,};


        if (e.getSource() == menuOpProp) {
            int option = JOptionPane.showConfirmDialog(menuOpProp, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                double trPower = Double.parseDouble(field1.getText());
                double frq = Double.parseDouble(field2.getText());
                double gammaVal = Double.parseDouble(field3.getText());

                templatePanel.addModelValues(trPower, frq, gammaVal);

            }
        }
    }
}
