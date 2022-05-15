package Projects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class TicTacToe here.
 * 
 * @author Dillon Shelton
 * @version 5/8/22
 */
public class TTT1 extends JFrame implements ActionListener
{ 
    JButton buttons[][];
    String turn = "x";
    public TTT1(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setLocationRelativeTo(null); 
        
       
        buttons = new JButton[3][3];
        setLayout(new GridLayout(3,3));
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                //Able to retrieve information about the button 
                buttons[i][j].putClientProperty("ROW",i);
                buttons[i][j].putClientProperty("COL",j);
                buttons[i][j].putClientProperty("OWNER",i*3+j+1); //gotta fill it with unique not-null data to prevent null exception
                add(buttons[i][j]);
            }
        }
 
        setVisible(true);
    }
    public void actionPerformed(ActionEvent click){
        JButton b = (JButton)click.getSource();
        //if NOT filled yet...
        if(b.getIcon()!=null) return;
        //picture files are called x.png and o.png
        String path = turn + ".png";
        ImageIcon picture = new ImageIcon(getClass().getResource(path));
        b.setIcon(picture);
        b.putClientProperty("OWNER", turn);
        //check if winning
        int row = (int)b.getClientProperty("ROW");
        int col = (int)b.getClientProperty("COL");
        //check row, column, and 2 diagonals
        if(buttons[row][0].getClientProperty("OWNER").equals(buttons[row][1].getClientProperty("OWNER")) && buttons[row][1].getClientProperty("OWNER").equals(buttons[row][2].getClientProperty("OWNER"))
        || buttons[0][col].getClientProperty("OWNER").equals(buttons[1][col].getClientProperty("OWNER")) && buttons[1][col].getClientProperty("OWNER").equals(buttons[2][col].getClientProperty("OWNER"))
        || buttons[0][0].getClientProperty("OWNER").equals(buttons[1][1].getClientProperty("OWNER")) && buttons[1][1].getClientProperty("OWNER").equals(buttons[2][2].getClientProperty("OWNER"))
        || buttons[2][0].getClientProperty("OWNER").equals(buttons[1][1].getClientProperty("OWNER")) && buttons[1][1].getClientProperty("OWNER").equals(buttons[0][2].getClientProperty("OWNER"))){
            System.out.println("GG!");
        }
        //change turn
        turn = (turn.equals("x"))?"o":"x";
        
    }
   
}
