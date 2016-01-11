/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

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
public class Main extends JFrame {

    public Main() {        
        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);
        final GridBagConstraints constraints = new GridBagConstraints();



        JLabel l2 = new JLabel("zero one:");
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        add(l2,constraints);

    
   
        JLabel l3 = new JLabel("drow");
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        add(l3,constraints);



      

//JPanel panel= new JPanel();
//panel.setBorder(new LineBorder(Color.RED));
//
//panel.add(templatePanel);
// getContentPane().add(panel, constraints);
//        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        scrollPane.setViewportView(templatePanel);

       


        //constraints.fill = GridBagConstraints.HORIZONTAL;
        

       
        JButton startButton = new JButton("Click Me To Start!");     
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx=0.1;
        add(startButton,constraints);

///////////////////////////////////Grid On//////////////////////////////////////
        JCheckBox gridOn = new JCheckBox("Grid On");
        gridOn.setSelected(false);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx=0.1;
        add(gridOn,constraints);


///////////////////////////////////////drowing panel////////////////////////////
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx=1;
        constraints.weighty=1;
        constraints.gridheight = 4;
        constraints.fill = GridBagConstraints.BOTH;
        final ModelJPanel templatePanel = new ModelJPanel();
      //  templatePanel.setPreferredSize(new Dimension(20 , 40));
        getContentPane().add(templatePanel, constraints);

        
        Border blackline;//titile and border
        TitledBorder title;
        blackline = (Border) BorderFactory.createLineBorder(Color.black);
        title = BorderFactory.createTitledBorder(blackline,"Draw");
        title.setTitleJustification(TitledBorder.LEFT);
        templatePanel.setBorder(blackline);
        templatePanel.setBorder(title);


////////////////////////////////////////////////////////////////////////////////


        setTitle("Modelling Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();                       // "this" JFrame packs its components
        setLocationRelativeTo(null); // center the application window
        setVisible(true);            // show it
///////////////////////set constraints default//////////////////////////////////
//        constraints.gridx = 1;
//        constraints.gridy = 1;
//        constraints.weightx=0;
//        constraints.weighty=0;
//        constraints.gridheight = 0;

////////////////////////////////////////////////////////////////////////////////
        startButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClicked();

            }
        });
////////////////////////////////////////////////////////////////////////////////
        gridOn.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GridOn(templatePanel,constraints);
               
            }
        });
    }

    /** The entry main() method */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                Main main = new Main();

            }
        });

    }

    private static void buttonClicked() {
        JOptionPane.showMessageDialog(null, "Button Clicked");
    }



     private static void GridOn(final ModelJPanel tPanel,final GridBagConstraints constraints) {
        //System.out.print("taniya");
        //SwingTemplateJPanel templatePanel = new SwingTemplateJPanel();

        


        

        //grid.draw(null);

         try {
             GridPanel grid = new GridPanel();

             grid.draw(tPanel, constraints);
             



                    
     
         } catch (Exception e) {
             System.out.print(ERROR);
             //System.out.print("/nError");
         }

        
    }

}
