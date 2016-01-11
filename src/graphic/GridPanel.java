/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphic;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Tani
 */

//public class GridPanel extends JPanel{
//    private int GRID_SIZE = 10;
//
//    public GridPanel(){
//
//    }
//     public void paintgrid(Graphics g) {
//
//        System.out.print("chek box clicked");
//
//        super.paintComponent(g);  // paint background
//        setBackground(Color.white);
//
//        Graphics2D g2d_ob3 = (Graphics2D) g;//paint grid
//        g2d_ob3.setColor(Color.gray);
//
//        for (int i = 0; i <= this.getWidth(); i = i + GRID_SIZE) {
//            g2d_ob3.drawLine(i, 0, i, this.getHeight());
//        }
//        for (int j = 0; j <= this.getHeight(); j = j + GRID_SIZE) {
//            g2d_ob3.drawLine(0, j, this.getWidth() , j);
//        }
//
//
//
//    }
//
//
//}

public class GridPanel extends JPanel {
 private BufferedImage canvasImage;

    public void innerdraw(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.add(panel);

        Graphics g = panel.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 100, 100);

        System.out.print("called inner draw");

            }


    void draw(ModelJPanel tPanel, GridBagConstraints constraints) {
        
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridheight=2;

        //Graphics2D g = this.canvasImage.createGraphics();
        //g.drawOval(100, 100, 40, 40);


         // paint background
        

        tPanel.add(new JLabel("Nisini"),constraints);
        System.out.print("gridpannel on  ");
        GridPanel gp = new GridPanel();

        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.add(panel);

        Graphics g = panel.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 100, 100);



    }



}
