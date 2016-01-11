/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphic2;

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
    public static int ROOM_LENGTH=200;
    public static int ROOM_WIDTH=400;
    public static int ROOM_POINT_X=200;
    public static int ROOM_POINT_Y=100;

    public static int ROUTE_POINT_X=250;//ROUTER POINT
    public static int ROUTE_POINT_Y=150;

    public double MAX_DISTANCE=0;
    public double DISTANCE;

    //RECEIVED_POWER=TRANSMITED_POWER*K_VALUE*(REFERENCE_DISTANCE/DISTANCE)^GAMMA_VALUE
    public double RECEIVED_POWER;
    public double RECEIVED_POWER_dBm;
    public double MAX_ABS_RECEIVED_POWER_dBm=0;
    public double TRANSMITTED_POWER_dBm=0;//1dBm
    public double TRANSMITTED_POWER=Math.pow(10, TRANSMITTED_POWER_dBm/10);//1mW
    public double C=3E8;
    public double freq=9E8;//900MHz
    public double GAMMA_VALUE=3.7;//for free space of office---ASSUMPTION-------
    public double REFERENCE_DISTANCE=1;//im

    public double K_VALUE_dB=20*Math.log10(C/(4*freq*REFERENCE_DISTANCE*Math.PI));
    public double K_VALUE=Math.pow(10, K_VALUE_dB/10);//for frquency=900MHz and REFERENCE_DISTANCE=1m

    public double RECEIVED_POWER_WITH_P_lOSS;// Received signal power with Partition Loss
    public double RECEIVED_POWER_WITH_P_lOSS_dBm;

    public double PARTITION_LOSS_WALL_dB = 3.4; // partition loss on doublePlasterBoard Wall
    public double PARTITION_LOSS_WALL = Math.pow(10, PARTITION_LOSS_WALL_dB/10);

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

////////////////////////Draw grid///////////////////////////////////////////////
      Graphics2D grid = (Graphics2D) g;//
      grid.setColor(Color.PINK);
      for(int j =0;j<getSize().height;j=j+10){
          grid.drawLine(0, j, getSize().width, j);
      }
      for(int i =0;i<getSize().width;i=i+10){
          grid.drawLine(i, 0, i,getSize().height);
      }



//System.out.print("*****"+g2d_ob1.getColor()+"\n");
//
//      for(int j=0;j<getSize().height;j++){
//          for(int i=0;i<getSize().width;i++){
//              if(g2d_ob1.getColor()==Color.BLACK){
//                 // System.out.print("*****\n");
//              }
//          }
//      }


///////////take maximum absolute signal power //////////////////////////////////
      for(int y = 0; y< getSize().height;y++){
        for(int x = 0+1;x<getSize().width;x++){
            DISTANCE =  Math.sqrt((x-ROUTE_POINT_X)*(x-ROUTE_POINT_X)
                            +(y-ROUTE_POINT_Y)*(y-ROUTE_POINT_Y));
            //if(DISTANCE!=0){
                RECEIVED_POWER=TRANSMITTED_POWER*K_VALUE
                        *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);

                ////
                if(y<ROOM_POINT_Y || y>ROOM_POINT_Y+ROOM_LENGTH || x<ROOM_POINT_X || x>ROOM_POINT_X+ROOM_WIDTH){
                    RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER/PARTITION_LOSS_WALL;
                }
                else if(DISTANCE!=0){
                    RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER;
                }
                else{
                    RECEIVED_POWER_WITH_P_lOSS=TRANSMITTED_POWER;
                }
                RECEIVED_POWER_WITH_P_lOSS_dBm=10*Math.log10(RECEIVED_POWER_WITH_P_lOSS);
                ////


            //}
//            else{
//                RECEIVED_POWER=TRANSMITTED_POWER;
//            }
            RECEIVED_POWER_dBm=10*Math.log10(RECEIVED_POWER);
            //System.out.print(Math.abs(RECEIVED_POWER_dBm)+"\n");
            MAX_ABS_RECEIVED_POWER_dBm = max(Math.abs(RECEIVED_POWER_WITH_P_lOSS_dBm), MAX_ABS_RECEIVED_POWER_dBm);

        }
      }

System.out.print("ttttttttttttt"+MAX_ABS_RECEIVED_POWER_dBm+"tttttttttttt\n");


///////Prediction with one slope model///////////////////////////////////////////
      int numbersOfColors = (int) ((int) MAX_ABS_RECEIVED_POWER_dBm * 1.05);
      Color[] cols = getDifferentColors(numbersOfColors);
      Graphics2D pixel = (Graphics2D) g;
      for(int y = 0; y< getSize().height;y++){
        for(int x = 0+1;x<getSize().width;x++){
            DISTANCE =  Math.sqrt((x-ROUTE_POINT_X)*(x-ROUTE_POINT_X)
                            +(y-ROUTE_POINT_Y)*(y-ROUTE_POINT_Y));
            if(DISTANCE!=0){
                RECEIVED_POWER=TRANSMITTED_POWER*K_VALUE
                        *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);

                RECEIVED_POWER_dBm=10*Math.log10(RECEIVED_POWER);

                ////
                if(y<ROOM_POINT_Y || y>ROOM_POINT_Y+ROOM_LENGTH || x<ROOM_POINT_X || x>ROOM_POINT_X+ROOM_WIDTH){
                    RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER/PARTITION_LOSS_WALL;
                }
                else{
                    RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER;
                }
                RECEIVED_POWER_WITH_P_lOSS_dBm=10*Math.log10(RECEIVED_POWER_WITH_P_lOSS);
                ////

                int c = (int) (numbersOfColors-Math.abs(RECEIVED_POWER_WITH_P_lOSS_dBm));
                pixel.setColor(cols[c]);
                pixel.drawRect(x, y, 1, 1);
            }
            else{
                RECEIVED_POWER=TRANSMITTED_POWER;
            }


        }
      }




//      Color[] cols = getDifferentColors((int) MAX_DISTANCE+1);
//      Graphics2D pixel = (Graphics2D) g;
//      for(int y = ROOM_POINT_Y+1; y< ROOM_POINT_Y+ROOM_LENGTH-1;y++){
//        for(int x = ROOM_POINT_X+1;x<ROOM_POINT_X+ROOM_WIDTH-1;x++){
//            DISTANCE =  Math.sqrt((x-ROUTE_POINT_X)*(x-ROUTE_POINT_X)
//                            +(y-ROUTE_POINT_Y)*(y-ROUTE_POINT_Y));
//            if(DISTANCE!=0){
//                RECEIVED_POWER=TRANSMITTED_POWER*K_VALUE
//                        *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);
//                int c = (int) (MAX_DISTANCE - DISTANCE);
//                pixel.setColor(cols[c]);
//                pixel.drawRect(x, y, 1, 1);
//            }
//            else{
//                RECEIVED_POWER=TRANSMITTED_POWER;
//            }
//
//
//        }
//      }

////////////////////Draw Room///////////////////////////////////////////////////
      Graphics2D room = (Graphics2D) g;//
      room.setColor(Color.BLACK);// wall color is Black
      room.drawRect(ROOM_POINT_X, ROOM_POINT_Y, ROOM_WIDTH, ROOM_LENGTH);




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

        if(DISTANCE!=0){
            RECEIVED_POWER=TRANSMITTED_POWER*K_VALUE
                *Math.pow((REFERENCE_DISTANCE/DISTANCE),GAMMA_VALUE);
        }
        else{
            RECEIVED_POWER=TRANSMITTED_POWER;
        }
        RECEIVED_POWER_dBm=10*Math.log10(RECEIVED_POWER);
        System.out.println("Received Power from a distance of "+DISTANCE
                +"m is "+RECEIVED_POWER+"mW (" +RECEIVED_POWER_dBm +"dBm)\n");

        ///////received signal power considerig patition loss///////////////////
//        for(int j=0;j<getSize().height;j++){
//          for(int i=0;i<getSize().width;i++){
//              if(j<OB_POINT_Y || j>OB_POINT_Y+OB_HEIGHT || i<OB_POINT_X || i>OB_POINT_X+OB_WIDTH){
//                  RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER/PARTITION_LOSS_WALL;
//              }
//          }
//        }

        if(e.getY()<ROOM_POINT_Y || e.getY()>ROOM_POINT_Y+ROOM_LENGTH || e.getX()<ROOM_POINT_X || e.getX()>ROOM_POINT_X+ROOM_WIDTH){
                  RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER/PARTITION_LOSS_WALL;
              }
        else{
            RECEIVED_POWER_WITH_P_lOSS=RECEIVED_POWER;
        }

        RECEIVED_POWER_WITH_P_lOSS_dBm=10*Math.log10(RECEIVED_POWER_WITH_P_lOSS);
        System.out.println("Received Power considering Patition Loss from a distance of "+DISTANCE
                +"m is "+RECEIVED_POWER_WITH_P_lOSS+"mW ("+RECEIVED_POWER_WITH_P_lOSS_dBm  +"dBm)\n");



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
