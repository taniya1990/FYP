/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphic;

/**
 *
 * @author Tani
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

// Swing Program Template
//@SuppressWarnings("serial")
public class ModelJPanel extends JPanel implements MouseListener {
    // Name-constants

    public static final int CANVAS_WIDTH = 700;
    public static final int CANVAS_HEIGHT = 500;
    public static final Dimension PREF_SIZE = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    public static final int GRID_SIZE = 10;
    public static int[] point;
    public static int OB_HEIGHT=200;
    public static int OB_WIDTH=400;
    public static int OB_POINT_X=200;
    public static int OB_POINT_Y=100;

    public static int ROUTE_POINT_X=250;//ROUTER POINT
    public static int ROUTE_POINT_Y=150;

    public double MAX_DISTANCE=0;
    public double DISTANCE;

    //RECEIVED_POWER=TRANSMITED_POWER*K_VALUE*(REFERENCE_DISTANCE/DISTANCE)^GAMMA_VALUE
    public double RECEIVED_POWER;
    public double RECEIVED_POWER_dBm;
    public double TRANSMITED_POWER=1;//1mW
    public double K_VALUE=7.03E-4;//for frquency=900MHz and REFERENCE_DISTANCE=1m
    public double GAMMA_VALUE=3.7;//for free space
    public double REFERENCE_DISTANCE=1;//im

    public double MAX_Rx_Power=0;

    //public int cols[] ;


    /** Constructor to setup the GUI components */
    public ModelJPanel() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        addMouseListener(this);

    }

    public static Color[] getDifferentColors(int n) {
        Color[] cols = new Color[n];
        for (int i = 0; i < n; i++)
            cols[i] = Color.getHSBColor((float) i / n, 1, 1);
        return cols;
    }

//    private Ellipse2D getEllipseFromCenter
//            (int x, int y, int width, int height){
//        double newX = x - width / 2.0;
//        double newY = y - height / 2.0;
//
//        Graphics2D router = (Graphics2D) g;
//        router.setColor(Color.darkGray);
//        router.drawOval(ROUTE_POINT_X, ROUTE_POINT_Y, 8, 8);
//
//        Ellipse2D ellipse = new Ellipse2D.Double(newX, newY, width, height);
//        return ellipse;
//    }


    public void paintComponent(Graphics g) {
      super.paintComponent(g);  // paint background
      setBackground(Color.white);
      Graphics2D g2d_ob1 = (Graphics2D) g;//
      g2d_ob1.setColor(Color.PINK);
      System.out.print("*****"+getSize()+"\n");
      for(int j =0;j<getSize().height;j=j+10){
          g2d_ob1.drawLine(0, j, getSize().width, j);
      }
      for(int i =0;i<getSize().width;i=i+10){
          g2d_ob1.drawLine(i, 0, i,getSize().height);
      }

      Graphics2D g2d_ob2 = (Graphics2D) g;//
      g2d_ob2.setColor(Color.BLACK);// wall color is Black
      g2d_ob2.drawRect(OB_POINT_X, OB_POINT_Y, OB_WIDTH, OB_HEIGHT);

      for(int y = OB_POINT_Y+1; y< OB_POINT_Y+OB_HEIGHT-1;y++){
        for(int x = OB_POINT_X+1;x<OB_POINT_X+OB_WIDTH-1;x++){
            DISTANCE =  Math.sqrt((x-ROUTE_POINT_X)*(x-ROUTE_POINT_X)
                            +(y-ROUTE_POINT_Y)*(y-ROUTE_POINT_Y));
           MAX_DISTANCE=max(DISTANCE, MAX_DISTANCE);
        }
      }



System.out.print("*****"+g2d_ob1.getColor()+"\n");

      for(int j=0;j<getSize().height;j++){
          for(int i=0;i<getSize().width;i++){
              if(g2d_ob1.getColor()==Color.BLACK){
                 // System.out.print("*****\n");
              }
          }
      }


///////Prediction with one slope model///////////////////////////////////////////
      Color[] cols = getDifferentColors((int) MAX_DISTANCE+1);
      Graphics2D pixel = (Graphics2D) g;
      for(int y = OB_POINT_Y+1; y< OB_POINT_Y+OB_HEIGHT-1;y++){
        for(int x = OB_POINT_X+1;x<OB_POINT_X+OB_WIDTH-1;x++){
            DISTANCE =  Math.sqrt((x-ROUTE_POINT_X)*(x-ROUTE_POINT_X)
                            +(y-ROUTE_POINT_Y)*(y-ROUTE_POINT_Y));
            if(DISTANCE!=0){
                RECEIVED_POWER=TRANSMITED_POWER*K_VALUE
                        *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);
                int c = (int) (MAX_DISTANCE - DISTANCE);
                pixel.setColor(cols[c]);
                pixel.drawRect(x, y, 1, 1);
            }

        }
      }

///////////////draw router point////////////////////////////////////////////////

    Graphics2D router = (Graphics2D) g;
    router.setColor(Color.darkGray);
    int r = 8;
    double newX = ROUTE_POINT_X - r / 2.0;
    double newY = ROUTE_POINT_Y - r / 2.0;
    router.drawOval((int)newX, (int)newY, 8, 8);
//    router.fillOval(ROUTE_POINT_X, ROUTE_POINT_Y, 5, 5);


    }


    public void mouseClicked(MouseEvent e) {
        System.out.println("x: "+e.getX()+", y: "+e.getY());
        DISTANCE =  Math.sqrt((e.getX()-ROUTE_POINT_X)*(e.getX()-ROUTE_POINT_X)
                            +(e.getY()-ROUTE_POINT_Y)*(e.getY()-ROUTE_POINT_Y));
        RECEIVED_POWER=TRANSMITED_POWER*K_VALUE
                *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);
        RECEIVED_POWER_dBm=10*Math.log10(RECEIVED_POWER);
        System.out.println("Received Power from a distance of "+DISTANCE
                +"m is "+RECEIVED_POWER+"mW (" +RECEIVED_POWER_dBm +"dBm)\n");

    }



    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse Entered");
    }

    public void mouseExited(MouseEvent e) {
//        System.out.println("Mouse Exited");

    }



    private double max(double distance, double distance_next) {
        if(distance>=distance_next){
            return distance;
        }
        else
            return distance_next;

    }


}
