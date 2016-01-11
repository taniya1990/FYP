import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;

public class FlowLayoutComponentOrientationRIGHT_TO_LEFT extends JFrame {

  public FlowLayoutComponentOrientationRIGHT_TO_LEFT() {
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(new JButton("OK"));
    getContentPane().add(new JButton("Cancel"));

    applyOrientation(this, ComponentOrientation.RIGHT_TO_LEFT);
  }

  public static void main(String[] argv) {
    JFrame frame = new FlowLayoutComponentOrientationRIGHT_TO_LEFT();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void applyOrientation(Component c, ComponentOrientation o) {
    c.setComponentOrientation(o);

    if (c instanceof JMenu) {
      JMenu menu = (JMenu) c;
      int ncomponents = menu.getMenuComponentCount();
      for (int i = 0; i < ncomponents; ++i) {
        applyOrientation(menu.getMenuComponent(i), o);
      }
    } else if (c instanceof Container) {
      Container container = (Container) c;
      int ncomponents = container.getComponentCount();
      for (int i = 0; i < ncomponents; ++i) {
        applyOrientation(container.getComponent(i), o);
      }
    }
  }
}