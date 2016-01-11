/**
  *  ======================================================================
  *  DemoSimpleGrid.java. Create simple GUI with panel ...
  *
  *  Written By : Mark Austin                                  October 2011
  *  ======================================================================
  */

import java.lang.Math.*;
import java.applet.*;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.font.*;
import java.awt.image.*;
import java.awt.geom.*;   // Needed for affine transformation....
import java.net.URL;

public class DemoSimpleGrid extends JPanel{
    public static void main( String args[] ) {

       // Create graphics screen ....

       SimpleScreen canvas = new SimpleScreen();

       canvas.setBackground( Color.white );

       // Create buttons along panel ...

       final int NoButtons = 2;
       JButton buttons[] = new JButton [ NoButtons ];

       buttons[0] = new JButton ("Clear");
       buttons[0].addActionListener( new ButtonAction( buttons[0], canvas ));
       buttons[1] = new JButton ("Switch Grid");
       buttons[1].addActionListener( new ButtonAction( buttons[1], canvas ));

       // Create panel. Add buttons to panel.

       JPanel p1 = new JPanel();
       for(int ii = 1; ii <= NoButtons; ii++ )
           p1.add( buttons[ii-1] );

       JPanel panel = new JPanel();
       panel.setLayout( new BorderLayout() );
       panel.add(   canvas, BorderLayout.CENTER );
       panel.add(       p1, BorderLayout.SOUTH );

       JFrame frame = new JFrame("Draw Simple Grid");
       frame.getContentPane().setLayout( new BorderLayout() );
       frame.getContentPane().add( panel );
       frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
       frame.setSize( 700, 500);
       frame.setVisible(true);
    }
}

/*
 *  =================================================================
 *  This class listens for action events associated with the buttons.
 *  =================================================================
 */

class ButtonAction implements ActionListener {
      private JButton b;
      private SimpleScreen gs;

      public ButtonAction ( JButton b, SimpleScreen gs ) {
         this.b  = b;
         this.gs = gs;
      }

      public void actionPerformed ( ActionEvent e ) {
         String s = new String( e.getActionCommand() );

         // Clear Screen ....

         if( s.compareTo("Clear") == 0 ) { gs.clearScreen(); }

         // Draw Grid ....

         if( s.compareTo("Switch Grid") == 0 ) {
             if ( gs.grop.getGrid() == true ) {
                  gs.grop.setGrid( false );
                  gs.clearScreen();
             } else {
                  gs.drawGrid();
             }
         }
      }
}