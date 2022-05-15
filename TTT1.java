package Projects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class TTT2 here.
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TTT2 extends JFrame implements ActionListener{
    JPanel boardPanel = new JPanel();
    JPanel textPanel = new JPanel();
    JButton[][] cells = new JButton[3][3];
    JLabel textLabel = new JLabel("Player x's turn");
    String turn = "x";
    
    public TTT2(){
        //window settings
        setTitle("TicTacToe");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750,825);
        //text
        textLabel.setForeground( new Color(255,0,0) ); //RED
        textLabel.setFont( new Font("Roboto", Font.BOLD,60) );
        textPanel.add(textLabel);
        //board
        boardPanel.setLayout(new GridLayout(3,3));
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                cells[i][j] = new JButton();
                cells[i][j].addActionListener(this);
                cells[i][j].putClientProperty("owner", "-"); //empty
                cells[i][j].putClientProperty("row", i);
                cells[i][j].putClientProperty("col", j);
                boardPanel.add(cells[i][j]);
            }
        }
        //adding stuff 
        add(textPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        textPanel.setBackground(new Color(215,215,255) );
        boardPanel.setBackground(new Color(230,220,255) );
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent a){
        
        JButton b = (JButton)a.getSource();
        
        if(!b.getClientProperty("owner").equals("-")){
            //spot already taken
            return;
        }
        
        b.setIcon(new ImageIcon("Projects/"+turn + ".png"));
        b.putClientProperty("owner", turn);
        
        if(isWinning(b)) {
            textLabel.setText("Player " + turn + " wins!");
            //RESET POPUP
            return;
        }
        
        if(turn.equals("x")) {
            turn = "o";
            textLabel.setForeground( new Color(0,0,255) );
        }
        else {
            turn = "x";
            textLabel.setForeground( new Color(255,0,0) );
        }
        textLabel.setText("Player " + turn +"'s turn");

    }
    
    public boolean isWinning(JButton b){
        int i;
        boolean winning = false;
        int row = (int)b.getClientProperty("row");
        int col = (int)b.getClientProperty("col");
        for(i = 0;i<3;i++){
            String owner = ""+cells[row][i].getClientProperty("owner");
            if(!owner.equals(turn)) break;
        }
        if(i==3){
            for(i = 0;i<3;i++)
                cells[row][i].setIcon(new ImageIcon("Projects/"+turn+"filled.png"));
            winning = true;
        }
        for(i = 0;i<3;i++){
            String owner = "" + cells[i][col].getClientProperty("owner");
            if(!owner.equals(turn)) break;
        }
        if(i==3){
            for(i = 0;i<3;i++)
                cells[i][col].setIcon(new ImageIcon("Projects/"+turn+"filled.png"));
            winning = true;
        }
        for(i = 0;i<3;i++){
            String owner = "" + cells[i][i].getClientProperty("owner");
            if(!owner.equals(turn)) break;
        }
        if(i==3){
            for(i = 0;i<3;i++)
                cells[i][i].setIcon(new ImageIcon("Projects/"+turn+"filled.png"));
            winning = true;
        }
        for(i = 0;i<3;i++){
            String owner = ""+ cells[i][2-i].getClientProperty("owner");
            if(!owner.equals(turn)) break;
        }
        if(i==3){
            for(i = 0;i<3;i++)
                cells[i][2-i].setIcon(new ImageIcon("Projects/"+turn+"filled.png"));
            winning = true;
        }
        return winning;
    }
}
