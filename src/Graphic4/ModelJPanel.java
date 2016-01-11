/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphic4;

/**
 *
 * @author Tani
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import org.newdawn.slick.TrueTypeFont;

// Swing Program Template
//@SuppressWarnings("serial")
public class ModelJPanel extends JPanel implements MouseListener, MouseMotionListener {
    // Name-constants

    public static final int CANVAS_WIDTH = 1500;
    public static final int CANVAS_HEIGHT = 1000;
    public static final double SCALE = 0.05; //1m=20pixels
    //public static final Dimension PREF_SIZE = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    public static final int GRID_SIZE = 10;
    //public static int[] point;
//    public static int ROOM_LENGTH = 200;
//    public static int ROOM_WIDTH = 400;
//    public static int ROOM_POINT_X = 200;
//    public static int ROOM_POINT_Y = 100;
    public static int ROUTE_POINT_X ;//ROUTER POINT
    public static int ROUTE_POINT_Y ;
    public double MAX_DISTANCE = 0;
//    public double DISTANCE;
    //RECEIVED_POWER=TRANSMITED_POWER*K_VALUE*(REFERENCE_DISTANCE/DISTANCE)^GAMMA_VALUE
    
    public double MAX_ABS_RECEIVED_POWER_dBm = 0;
    public double TRANSMITTED_POWER_dBm = 0;//1dBm
    public double TRANSMITTED_POWER = Math.pow(10, TRANSMITTED_POWER_dBm / 10);//1mW
    public double C = 3E8;
    public double freq = 9E8;//900MHz
    public double GAMMA_VALUE = 3.7;//for free space of office---ASSUMPTION-------
    public double REFERENCE_DISTANCE = 1;//im
    public double K_VALUE_dB = 20 * Math.log10(C / (4 * freq * REFERENCE_DISTANCE * Math.PI));
    public double K_VALUE = Math.pow(10, K_VALUE_dB / 10);//for frquency=900MHz and REFERENCE_DISTANCE=1m 
    public double PARTITION_LOSS_WALL_dB = 3.4; // partition loss on doublePlasterBoard Wall
    public double PARTITION_LOSS_WALL = Math.pow(10, PARTITION_LOSS_WALL_dB / 10);

    public double RECEIVED_POWER[][];
    public double RECEIVED_POWER_dBm[][];
    public double RECEIVED_POWER_WITH_P_lOSS[][];// Received signal power with Partition Loss
    public double RECEIVED_POWER_WITH_P_lOSS_dBm[][];
    public int wallPoint[][];
    public int routePoint[][];
    public double signalStrenth[][];
    public double distance[];
    public int routeX = 0;
    public int routeY = 0;

    //public int cols[] ;
    /** Constructor to setup the GUI components */
    public ModelJPanel() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        wallPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        routePoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        signalStrenth = new double[CANVAS_HEIGHT][CANVAS_WIDTH];
        distance = new double[CANVAS_HEIGHT*CANVAS_WIDTH];

    }

    public static Color[] getDifferentColors(int n) {
        Color[] cols = new Color[n];
        for (int i = 0; i < n; i++) {
            cols[i] = Color.getHSBColor((float) i / n, 1, 1);
        }
        return cols;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // paint background
        setBackground(Color.white);

///////////////////free Wall  Drawing////////////////////////////////////////////
        Graphics2D g2d = (Graphics2D) g;

        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {

                switch (wallPoint[j][i] ) {
                    case 1:

                        g2d.drawRect(i, j, 1, 1);
                        break;
                }

            }
        }

        //Graphics2D router = (Graphics2D) g;
        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {

                switch (routePoint[j][i] ) {
                    case 1:
                        g2d.drawOval(i-2, j-2, 4, 4);
                        ROUTE_POINT_X = i;
                        ROUTE_POINT_Y = j;
                        break;
                }

            }
        }

        //Graphics2D pixel = (Graphics2D) g;
        int numbersOfColors = (int) ((int) MAX_ABS_RECEIVED_POWER_dBm * 1.05);
        Color[] cols = getDifferentColors(numbersOfColors);
        for (int j = 0; j < CANVAS_HEIGHT ; j++) {
            for (int i = 0; i < CANVAS_WIDTH ; i++) {
                if(addPropagationModelToGrid()== false){
                    g2d.setColor(Color.PINK);
                    g2d.drawRect(i, j, 1, 1);
                }
                //int c = (int) (numbersOfColors - Math.abs(RECEIVED_POWER_dBm[j][i]));
                
               // repaint();


            }

        }

        

    }

    public boolean isLOS(int x, int y){
        double m1=(y-routeY)/(x-routeX);
        for(int i = 0; i < wallPoint.length;i++){
            double m2 = (y-wallPoint[i][0])/(x-wallPoint[i][1]);
            if(m1==m2){
                return false;
            }
            else{
                return true;
            }

        }


        return true;
    }


    private int x1, y1;

    public void mouseClicked(MouseEvent e) {
        System.out.println("x: " + e.getX() + ", y: " + e.getY());

//     
    }

    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    public void mouseReleased(MouseEvent e) {

        int x2 = e.getX();
        int y2 = e.getY();

        this.getGraphics().drawLine(x1, y1, x2, y2);

    }

    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse Entered");
    }

    public void mouseExited(MouseEvent e) {
//        System.out.println("Mouse Exited");
    }

    private double max(double distance, double distance_next) {
        if (distance >= distance_next) {
            return distance;
        } else {
            return distance_next;
        }



    }

    //////////add wall points to the grid////////////////////////////////////////
    public void addWallToGrid(int x1, int y1, int x2, int y2) {

        if (x1 == x2) {
            for (int i = y1; i <= y2; i++) {
                wallPoint[i][x1] = 1;
            }
        } else if (y1 == y2) {
            for (int i = x1; i <= x2; i++) {
                wallPoint[y1][i] = 1;
            }
        }

        repaint();
    }
///////////get route point//////////////////////////////////////////////////////

/////////add router points to the grid//////////////////////////////////////////
       public void addRouterToGrid(int x, int y) {
            routeX = x;
            routeY = y;
            routePoint[x][y] = 1;
            repaint();
        }


///////////////add propagation model to grid///////////////////////////////////
       public boolean addPropagationModelToGrid(){
           int count=0;
           double dis = 0;
           for (int j = 0; j < CANVAS_HEIGHT; j++) {
               for (int i = 0; i <CANVAS_WIDTH; i++) {
                   distance[count] = Math.sqrt(Math.pow(i-routeX,2)+Math.pow(j-routeY,2));
                   dis = Math.sqrt(Math.pow(i-routeX,2)+Math.pow(j-routeY,2));
                   count++;
                   RECEIVED_POWER[j][i]= TRANSMITTED_POWER * K_VALUE * Math.pow((REFERENCE_DISTANCE / dis), GAMMA_VALUE);
                   RECEIVED_POWER_dBm[j][i] = 10 * Math.log10(RECEIVED_POWER[j][i]);

               }

           }
           System.out.print("sssssssssssss"+distance[500]);
           System.out.print("transmitted power"+TRANSMITTED_POWER+"k value"+K_VALUE+"Reference distance"+REFERENCE_DISTANCE+" Gamma val"+GAMMA_VALUE);
           System.out.print("sssssssssssss"+RECEIVED_POWER[0][500]);
           return false;

       }


    public void mouseDragged(MouseEvent e) {

        int x2 = e.getX();
        int y2 = e.getY();
        this.getGraphics().drawLine(x1, y1, x2, y2);

    }

    public void mouseMoved(MouseEvent e) {
    }
}
