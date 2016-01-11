/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphic3;

/**
 *
 * @author Tani
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.*;
import org.newdawn.slick.TrueTypeFont;

// Swing Program Template
//@SuppressWarnings("serial")
public class ModelJPanel extends JPanel implements MouseListener, MouseMotionListener {
    // Name-constants

    public static final int CANVAS_WIDTH = 200;
    public static final int CANVAS_HEIGHT = 200;
    public static final double SCALE = 0.05; //1m=20pixels
    //public static final Dimension PREF_SIZE = new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    public static final int GRID_SIZE = 10;
    //public static int[] point;
//    public static int ROOM_LENGTH = 200;
//    public static int ROOM_WIDTH = 400;
    public static int routePointX;
    public static int routePointY;
    //public static int ROUTE_POINT_X ;//ROUTER POINT
    //public static int ROUTE_POINT_Y ;
    public double MAX_DISTANCE = 0;
    public double DISTANCE;
    //RECEIVED_POWER=TRANSMITED_POWER*K_VALUE*(REFERENCE_DISTANCE/DISTANCE)^GAMMA_VALUE
    public double MAX_ABS_RECEIVED_POWER_dBm = 0;
    public double transmitted_power_dBm;//= 0;//1dBm
//    public double TRANSMITTED_POWER = Math.pow(10, transmitted_power_dBm / 10);//1mW
//    public double C = 3E8;
    public double freq;//= 9E8;//900MHz
    public double gamma_value;//= 3.7;//for free space of office---ASSUMPTION-------
    public double MIN_SIGNAL_STRENGTH = 100;
//    public double REFERENCE_DISTANCE = 1;//im
//    public double K_VALUE_dB = 20 * Math.log10(C / (4 * freq * REFERENCE_DISTANCE * Math.PI));
//    public double K_VALUE = Math.pow(10, K_VALUE_dB / 10);//for frquency=900MHz and REFERENCE_DISTANCE=1m
    public double RECEIVED_POWER_WITH_P_lOSS;// Received signal power with Partition Loss
    public double RECEIVED_POWER_WITH_P_lOSS_dBm = 10 * Math.log10(RECEIVED_POWER_WITH_P_lOSS);
    public double PARTITION_LOSS_WALL_dB = 8;//3.4; // partition loss on doublePlasterBoard Wall
    public double PARTITION_LOSS_WALL = Math.pow(10, PARTITION_LOSS_WALL_dB / 10);
    public int wallPoint[][];
    public int hWallPoint[][];
    public int vWallPoint[][];
    public int routePoint[][];
    public int heatMapPoint[][];
    public int canvasPoint[][];
    public boolean isLOS[][];
    public double RECEIVED_POWER[][];
    public double RECEIVED_POWER_dBm[][];
    public int routeX = 0;
    public int routeY = 0;
    public int heatMapReady = 0;
    public boolean paint;
    public boolean clean;
    public int wall = 0;

    //public int cols[] ;
    /** Constructor to setup the GUI components */
    public ModelJPanel() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        wallPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        hWallPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        vWallPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        routePoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        canvasPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        heatMapPoint = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        isLOS = new boolean[CANVAS_HEIGHT][CANVAS_WIDTH];
        RECEIVED_POWER = new double[CANVAS_HEIGHT][CANVAS_WIDTH];
        RECEIVED_POWER_dBm = new double[CANVAS_HEIGHT][CANVAS_WIDTH];
    }

    public static Color[] getDifferentColors(int n) {
        Color[] cols = new Color[n];
        for (int i = 0; i < n; i++) {
            cols[i] = Color.getHSBColor(((float) i / n), 1, 1);
        }
        return cols;
    }

    @Override
    public void repaint() {
        super.repaint();
//        System.out.println("repaint");

    }

    /////draw components in canvas//////////////////////////////////////////////////
    public void paintComponent(Graphics g) {

        System.out.println("paintComponent");

        super.paintComponent(g);  // paint background
        setBackground(Color.white);



///////////////////free Wall  Drawing////////////////////////////////////////////
        Graphics2D g2d = (Graphics2D) g;

        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                if (vWallPoint[j][i] == 1 || hWallPoint[j][i] == 1) {
                    g2d.drawRect(i, j, 1, 1);
                }
            }
        }

///////////////////////router drawing///////////////////////////////////////////
        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                switch (routePoint[j][i]) {
                    case 1:
                        g2d.drawOval(i - 2, j - 2, 4, 4);
                        routePointX = i;
                        routePointY = j;
                        break;
                }
            }
        }
////////////////////heat map drawing////////////////////////////////////////////
//        int color[][];
//        color = new int[CANVAS_HEIGHT][CANVAS_WIDTH];

//        System.out.println("CANVAS_HEIGHT: " + CANVAS_HEIGHT);
//        System.out.println("CANVAS_WIDTH: " + CANVAS_WIDTH);
        if (paint) {

            int numbersOfColors = 101;
            Color[] cols = getDifferentColors(numbersOfColors);

            int heatMapColor[][] = heatMapColor();

            for (int j = 0; j < CANVAS_HEIGHT; j++) {
                for (int i = 0; i < CANVAS_WIDTH; i++) {
                    g2d.setColor(cols[heatMapColor[j][i]]);
//                    g2d.setColor(Color.CYAN);
                    g2d.drawRect(i, j, 1, 1);
                    if (routePoint[j][i] == 1 || hWallPoint[j][i] == 1 || vWallPoint[j][i] == 1) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(i, j, 1, 1);
                    }

                }

            }
            paint = false;
        }


        if (clean) {
            g2d.setColor(Color.WHITE);
            for (int j = 0; j < CANVAS_HEIGHT; j++) {
                for (int i = 0; i < CANVAS_WIDTH; i++) {
                    g2d.drawRect(i, j, 1, 1);


                }
            }
            clean = false;
        }
    }
    private int x1, y1;

    public void mouseClicked(MouseEvent e) {
        System.out.println("x: " + e.getX() + ", y: " + e.getY());


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

//    private double max(double distance, double distance_next) {
//        if (distance >= distance_next) {
//            return distance;
//        } else {
//            return distance_next;
//        }
//
//    }
    //////////add wall points to the grid////////////////////////////////////////
    public void addWallToGrid(int x1, int y1, int x2, int y2) {

        if (x1 == x2) {
            for (int i = y1; i <= y2; i++) {
                wallPoint[i][x1] = 1;
                vWallPoint[i][x1] = 1;
            }
        } else if (y1 == y2) {
            for (int i = x1; i <= x2; i++) {
                wallPoint[y1][i] = 1;
                hWallPoint[y1][i] = 1;
            }
        }

        repaint();
    }

/////////add router points to the grid//////////////////////////////////////////
    public void addRouterToGrid(int x, int y) {
        routeX = x;
        routeY = y;
        routePoint[routeY][routeX] = 1;
        repaint();
    }

/////////add heatmap poits to grid/////////////////////////////////////////////
    public void addHeatMapToGrid() {
        paint = true;
        repaint();
    }

    public int[][] heatMapColor() {
        int heatMapColor[][];
        heatMapColor = new int[CANVAS_HEIGHT][CANVAS_WIDTH];
        double received_power_dBm[][] = getReceivedPower_dBm_array();
        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                heatMapColor[j][i] = (int) Math.round((Math.abs(received_power_dBm[j][i])));
                if (heatMapColor[j][i] == 0) {
                    heatMapColor[j][i] = 100;
                }
            }
        }
        return heatMapColor;
    }

    public double[][] getReceivedPower_dBm_array() {
        double received_power_dBm[][];
        received_power_dBm = new double[CANVAS_HEIGHT][CANVAS_WIDTH];
        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                received_power_dBm[j][i] = getReceivedPower_dBm(i, j);
//                if (received_power_dBm[j][i] < -100) {
//                    received_power_dBm[j][i] = 0;
//                }

            }
        }
        return received_power_dBm;
    }

    public double getReceivedPower_dBm(int x, int y) {
        double receivedPower_dBm = 0;
        double C = 3E8;
        double REFERENCE_DISTANCE = 1;//1m
        double K_VALUE_dB =
                20 * Math.log10(C / (4 * freq * REFERENCE_DISTANCE * Math.PI));
//        double K_VALUE = Math.pow(10, K_VALUE_dB / 10);//for frquency=900MHz and REFERENCE_DISTANCE=1m
//        double TRANSMITTED_POWER = Math.pow(10, transmitted_power_dBm / 10);//1mWTRANSMITTED_POWER = Math.pow(10, transmitted_power_dBm / 10);//1mW
//        double received_power = 0;
        double distance = takeDistance(x, y, routeX, routeY);
        if (distance != 0 && distance < 100) {
//            received_power = TRANSMITTED_POWER * K_VALUE
//                    * Math.pow((REFERENCE_DISTANCE / distance), gamma_value);
            receivedPower_dBm = transmitted_power_dBm + K_VALUE_dB - gamma_value * 10 * Math.log10(distance / REFERENCE_DISTANCE);

            if (isLOSnNumOfWalls(x, y) != 0) {
//                received_power = received_power / (PARTITION_LOSS_WALL*((double)isLOSnNumOfWalls(x, y)));
                receivedPower_dBm = receivedPower_dBm - PARTITION_LOSS_WALL_dB - (double) isLOSnNumOfWalls(x, y);
            }
        } else {
            receivedPower_dBm = -1000;
        }


        if (receivedPower_dBm < -MIN_SIGNAL_STRENGTH) {
            receivedPower_dBm = 0;
        }
        return receivedPower_dBm;

    }

    public void mouseDragged(MouseEvent e) {

        int x2 = e.getX();
        int y2 = e.getY();
        this.getGraphics().drawLine(x1, y1, x2, y2);

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        x1 = e.getX();

        y1 = e.getY();

        int wallX;
        int wallY;
        int numOfWalls = 0;
        boolean los = true;

        if (isLOSnNumOfWalls(x1, y1) != 0) {
            System.out.println(" NLOS  Signal strength is : " + getReceivedPower_dBm(x1, y1) + " in dBm : " + 10 * Math.log10(getReceivedPower_dBm(x1, y1)));
        } else if (isLOSnNumOfWalls(x1, y1) == 0) {
            System.out.println(" LOS Signal Strength is   : " + getReceivedPower_dBm(x1, y1) + " in dBm : " + 10 * Math.log10(getReceivedPower_dBm(x1, y1)));
        }

        System.out.println("\nnew function islos val: " + isLOSnNumOfWalls(x1, y1));



//System.out.print("wall start point "+getWallStartPoint());
    }

    public double takeDistance(int x1, int y1, int x2, int y2) {

        double dis = 0;
        dis = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        return dis;
    }

    public void addModelValues(double trPower, double frq, double gammaVal) {
        transmitted_power_dBm = trPower;
        freq = frq * 1000000.0;
        gamma_value = gammaVal;
        System.out.println(" \n call add model values\n" + transmitted_power_dBm + "\n" + freq + "\n" + gamma_value);

    }

    public int[] getNumOfwallPoint() {
        int numberOfHWallPoints = 0;
        int numberOfVWallPoints = 0;
        for (int j = 1; j < CANVAS_HEIGHT; j++) {
            for (int i = 1; i < CANVAS_WIDTH; i++) {
                if (hWallPoint[j][i] == 1) {
                    numberOfHWallPoints++;
                }
                if (vWallPoint[j][i] == 1) {
                    numberOfVWallPoints++;
                }
            }
        }
        int numberOfWallPoints[] = new int[2];
        numberOfWallPoints[0] = numberOfHWallPoints;
        numberOfWallPoints[1] = numberOfVWallPoints;
        return numberOfWallPoints;
    }

    public int[][][] wallArry() {
        int h = 0;
        int v = 0;
        int n[] = getNumOfwallPoint();
        int maxn = n[1];
        if (n[0] > n[1]) {
            maxn = n[0];
        }

        int hWallArray[][] = new int[maxn][2];
        int vWallArray[][] = new int[maxn][2];
        int wallArray[][][] = new int[2][maxn][2];
        for (int j = 1; j < CANVAS_HEIGHT; j++) {
            for (int i = 1; i < CANVAS_WIDTH; i++) {
                if (hWallPoint[j][i] == 1) {
                    hWallArray[h][0] = i;
                    hWallArray[h][1] = j;
                    h++;
                }
                if (vWallPoint[j][i] == 1) {
                    vWallArray[v][0] = i;
                    vWallArray[v][1] = j;
                    v++;
                }
            }
        }
        wallArray[0] = hWallArray;
        wallArray[1] = vWallArray;

        return wallArray;
    }

    public int isLOSnNumOfWalls(int x, int y) {
        int losnNumOfWalls[][] = new int[1][2];
        int wallArray[][][] = wallArry();
        int n[] = getNumOfwallPoint();
        double m = ((double) y - (double) routePointY) / ((double) x - (double) routePointX);
        double c = (double) routePointY - m * (double) routePointX;

        double yy;
        double xxx;
        int nlos = 0;

///////verticle walls check in////////////////////////////////////////////////
        if (x < routePointX) {
            for (int xx = x; xx <= routePointX; xx++) {

                yy = Math.round(m * (double) xx + c);
                for (int w = 0; w < n[1]; w++) {
                    if (xx == wallArray[1][w][0] && yy == wallArray[1][w][1]) {
                        nlos++;
                    }
                }
            }
        } else if (x > routePointX) {
            for (int xx = routePointX; xx <= x; xx++) {
                yy = Math.round(m * (double) xx + c);
                for (int w = 0; w < n[1]; w++) {
                    if (xx == wallArray[1][w][0] && yy == wallArray[1][w][1]) {
                        nlos++;
                    }
                }
            }
        }

/////////horizontal wall check in for verticle lines////////////////////////////
        if (x == routePointX && y < routePointY) {
            int xx = routePointX;
            for (yy = y; yy < routePointY; yy++) {
                for (int w = 0; w < n[0]; w++) {
                    if (xx == wallArray[0][w][0] && yy == wallArray[0][w][1]) {
                        nlos++;
                    }
                }
            }
        } else if (x == routePointX && y > routePointY) {
            int xx = routePointX;
            for (yy = routePointY; yy < y; yy++) {
                for (int w = 0; w < n[0]; w++) {
                    if (xx == wallArray[0][w][0] && yy == wallArray[0][w][1]) {
                        nlos++;
                    }
                }
            }
        }
///////horizontal walls check in////////////////////////////////////////////////
        if (y < routePointY) {
            for (int yyy = y; yyy <= routePointY; yyy++) {

                xxx = Math.round(((double) yyy - c) / m);
                for (int w = 0; w < n[0]; w++) {
                    if (xxx == wallArray[0][w][0] && yyy == wallArray[0][w][1]) {
                        nlos++;
                    }
                }
            }
        } else if (y > routePointY) {
            for (int yyy = routePointY; yyy <= y; yyy++) {
                xxx = Math.round(((double) yyy - c) / m);
                for (int w = 0; w < n[0]; w++) {
                    if (xxx == wallArray[0][w][0] && yyy == wallArray[0][w][1]) {
                        nlos++;
                    }
                }
            }
        }
        return nlos;
    }

    public void clear() {
        clean = true;
        for (int j = 0; j < CANVAS_HEIGHT; j++) {
            for (int i = 0; i < CANVAS_WIDTH; i++) {
                routePoint[j][i] = 0;
                hWallPoint[j][i] = 0;
                vWallPoint[j][i] = 0;
            }
        }
        repaint();
    }
}
