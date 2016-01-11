/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Trial;

/**
 *
 * @author Tani
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ModelPanel extends JPanel{
    public static final int Frame_width = 700;
    public static final int Frame_HeightT = 500;
    public static final Dimension Panel_Size = new Dimension(Frame_width, Frame_HeightT);
    public static final String TITLE = "Modeling";
   

    private JPanel User_Interface;
    private JPanel Grid_Plane;
    private JPanel controler;

    public ModelPanel(){
        setPreferredSize(Panel_Size);
        
        //setPlane();
    }


    
    private final void setPanel(){
        User_Interface = new JPanel(new BorderLayout(5,5));
        User_Interface.setBackground(Color.blue);
        User_Interface.setBorder(new EmptyBorder(5,5,5,5));
        User_Interface.add(Grid_Plane);

        JPanel controlsConstrain = new JPanel(new BorderLayout(4, 4));
        User_Interface.add(controlsConstrain, BorderLayout.LINE_END);
        controlsConstrain.setBackground(Color.white);

        controler = new JPanel(new GridLayout(0,1,0,25));
        controlsConstrain.add(controler, BorderLayout.PAGE_START);
        controler.setBackground(Color.CYAN);
        controler.setBorder(new EmptyBorder(40, 20, 20, 20));
        controler.add(new JButton("OK"));


    }

    private static void createAndShow(){
        JFrame frame= new JFrame();
        frame.getContentPane().add(new ModelPanel().getUi());
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new ModelPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                createAndShow();


            }

            
        });
    }

        public JPanel getUi() {
        return User_Interface;
    }
}

