import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MineField extends JPanel implements ActionListener{

    public static void main(String[] args) {
        MineField g = new MineField();
        JFrame frame = new JFrame("Mine Field");
        frame.add(g);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JButton squares[][];

    public MineField(){
        this.setSize(400,400);
        this.setLayout(new GridLayout(5,5));
        squares = new JButton[5][5];
        buildButtons();
    }

    int [][] num = new int [5][5];

    private void buildButtons(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                squares[i][j] = new JButton();
                squares[i][j].setSize(400,400);
                this.add(squares[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

}