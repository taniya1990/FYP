/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.geom.*;

public class Main extends StateBasedGame{

   public static final String gamename = "Interact Window";
   public static final int play = 0;
   public static final int xSize = 800;
   public static final int ySize = 300;
   public static final float x1 = 30;
   public static final float y1 = 60;
   public static final float x2 = 15;
   public static final float y2 = 40;

   public Main(String gamename){
      super(gamename);
      this.addState(new Play());

   }

   public void initStatesList(GameContainer gc) throws SlickException{
      this.getState(play).init(gc, this);
      this.enterState(play);
   }

   // more sophisticated control over geometry, coordinate transformations, color management, and text layout.
   public class Graphics2D extends Graphics{
       public void paint (Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //g.draw();
            g.drawLine(3, 50, 20, 7);
            g.drawString("abc",25,25);
           // g2.draw(new Line2D.Double(x1, y1, x2, y2));

        }
   }


   public static void main(String[] args) {
      AppGameContainer appgc;

      try{
         appgc = new AppGameContainer(new Main(gamename));
         appgc.setDisplayMode(xSize, ySize, false);
         appgc.setTargetFrameRate(60);
         appgc.start();





         //g.drawRect(float x1, float y1, float width, float height);
      }catch(SlickException e){
         e.printStackTrace();
      }
   }
}