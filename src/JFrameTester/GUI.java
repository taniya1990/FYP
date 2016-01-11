
import graphic.ModelJPanel;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;

public class GUI extends JFrame
{
    private JPanel Checks; //Panel to Hold Checks
    private JPanel Transactions;
    private JPanel History;
    private JPanel Graphics;
    private JLabel CLabel;


    public GUI()
    {
        super ( "UTB Check-In");
        JPanel Checks = new JPanel(); //set up panel
        CLabel = new JLabel("Label with text");
        Checks.setBackground(Color.red);
        Checks.setLayout( new BoxLayout(Checks,BoxLayout.LINE_AXIS));
        add(Checks);


      
    }

    public static void main(String[] args) {
        
        JFrame f = new JFrame();
        f.setContentPane(new ModelJPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();             // "this" JFrame packs its components
        f.setLocationRelativeTo(null); // center the application window
        f.setVisible(true);            // show it

    }
}