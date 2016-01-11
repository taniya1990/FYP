/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JFrameTester;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Tani
 */
public class paintComponent extends JPanel{
    @Override
      public void paintComponent(Graphics g) {
      super.paintComponents(g);

     // setBackground(Color.white);
      Graphics2D g2d_ob1 = (Graphics2D) g.create();


      g2d_ob1.drawRect(20, 50, 50, 40);
      g2d_ob1.setColor(Color.black);

      Graphics2D g2d_ob3 = (Graphics2D) g;
      g2d_ob3.setColor(Color.gray);
      g2d_ob3.drawLine(0, 0, 700, 0);
      g2d_ob3.drawLine(0, 10, 700, 10);
      g2d_ob3.drawLine(0, 0, 0, 500);
      g2d_ob3.drawLine(10, 0, 10, 500);

    }
}
