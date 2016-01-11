/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic2;

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
public class Main_Grapic2 extends JFrame implements ActionListener{
private JTextField x2Field, y2Field, x1Field, y1Field;
    private JTextField xRoute, yRoute;
    private JButton add_router;
    private JButton button;
    private JButton model;
    private ModelJPanel templatePanel;
    public Main_Grapic2() {
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

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets=new Insets(10,5,5,5);

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("x1:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(x1Field, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("y1:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(y1Field, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("x2:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(x2Field, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("y2:"), constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(y2Field, constraints);

        button = new JButton("Add Wall");
        constraints.gridy = 4;
        constraints.gridx = 1;
        //constraints.gridwidth = 1;
        button.addActionListener(this);
        optionsPanel.add(button, constraints);


        constraints.gridy = 5;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("Router Point x :"),constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(xRoute, constraints);

        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.weightx = 0;
        optionsPanel.add(new JLabel("Router Point y :"),constraints);
        constraints.gridx = 1;
        constraints.weightx = 1;
        optionsPanel.add(yRoute, constraints);


        add_router = new JButton("Draw Router");
        constraints.gridx = 1;
        constraints.gridy = 7;
        add_router.addActionListener(this);
        optionsPanel.add(add_router,constraints);

        model = new JButton("Model OK");
        constraints.gridx = 1;
        constraints.gridy = 8;
        model.addActionListener(this);
        optionsPanel.add(model,constraints);

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
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                Main_Grapic2 main = new Main_Grapic2();

            }
        });

    }

    public void actionPerformed(ActionEvent e) {

    }

}
