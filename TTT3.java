package Projects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Write a description of class TTT2 here.
 * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TTT3 extends JFrame implements ActionListener{
    JPanel boardPanel = new JPanel();
    JPanel textPanel = new JPanel();
    JButton[] cells = new JButton[9];
    JLabel textLabel = new JLabel("Player X's turn");
    String turn = "x";
    mmBoard board;
    boolean keepGoing = true;
    
    public TTT3(){
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
        for(int i = 0;i<9;i++){
            cells[i] = new JButton();
            cells[i].addActionListener(this);
            cells[i].putClientProperty("owner", "-"); //empty
            cells[i].putClientProperty("index", i);

            boardPanel.add(cells[i]);
        }
        board = new mmBoard(cells, turn);
        //adding stuff 
        add(textPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        textPanel.setBackground(new Color(215,215,255) );
        boardPanel.setBackground(new Color(230,220,255) );
        setVisible(true);
    }
    public void actionPerformed(ActionEvent a){
        if(!keepGoing) return; //game already won
        
        JButton b = (JButton)a.getSource();
        if(!b.getClientProperty("owner").equals("-")){
            //spot already taken
            return;
        }
        
        board = board.move((int)b.getClientProperty("index"));
        
        b.setIcon(new ImageIcon("Projects/"+turn + ".png"));
        b.putClientProperty("owner", turn);
        
        if(board.isWin()||board.isDraw()){
            textLabel.setText("Player " + turn.toUpperCase() + " wins!");
            keepGoing = false;
            return;
        }
        
        switchTurn();
        //AI goes
        Integer index = board.bestMove();
        board = board.move(index);
        cells[index].putClientProperty("owner", turn);
        cells[index].setIcon(new ImageIcon("Projects/"+turn + ".png"));
        
        if(board.isWin()||board.isDraw()){
            textLabel.setText("Player " + turn.toUpperCase() + " wins!");
            keepGoing = false;
            return;
        }
        
        switchTurn();
        //Human again
        
    }

    public void switchTurn(){
        if(turn.equals("x")) {
            turn = "o";
            textLabel.setForeground( new Color(0,0,255) );
        }
        else {
            turn = "x";
            textLabel.setForeground( new Color(255,0,0) );
        }
        textLabel.setText("Player " + turn.toUpperCase() +"'s turn");
    }
    
}
//AI brain
class mmBoard{
    private String[] state;
    private String turn;
    public mmBoard(JButton[] arr, String turn){
        state = new String[9];
        for(int i = 0;i<9;i++){
            state[i] = ""+arr[i].getClientProperty("owner")      ;     
        }
        this.turn = opposite(turn);
        
    }
    public mmBoard(String[] state, String turn){
        this.state = state;
        this.turn = turn;
    }
    public Integer bestMove(){
        Integer bestMove = null;
        double max = Integer.MIN_VALUE;
        for(Integer move : getLegalMoves()){
            double result = minimax(move(move), turn, false, 9);
            if(result>max){
                max = result;
                bestMove = move;
            }
        }       
     
        return bestMove;
    }
    public double minimax(mmBoard state, String originalPlayer, boolean isMaximizing, int maxDepth) {
        if(state.isWin()||state.isDraw()||maxDepth==0){
            return state.evaluate(originalPlayer);
        }
        if(isMaximizing){
            double max = Double.NEGATIVE_INFINITY;
            for(Integer move : state.getLegalMoves()){
                double mm = minimax(state.move(move), originalPlayer, false, maxDepth-1);
                max = Math.max(max, mm);
            }
            return max;
        }
        else {
            double min = Double.POSITIVE_INFINITY;
            for(Integer move : state.getLegalMoves()){
                double mm = minimax(state.move(move), originalPlayer, true, maxDepth-1);
                min = Math.min(min, mm);
            }
            return min;
        }
    }
    
    public mmBoard move(int move) {
        String[] temp = Arrays.copyOf(state, 9);
        temp[move] = turn;
        return new mmBoard(temp, opposite(turn));
    }
    
    public int evaluate(String player) {
        //if board is winning, that means the last turn won. if current turn is player, 
        //then ai won. Then if originalplayer is the ai, it should return 1. 
        if(isWin() && turn.equals(player))
            
            return -1;
        if(isWin() && !turn.equals(player))
            return 1;
        return 0;
    }
    public String opposite(String turn){
        return turn.equals("x")?"o":"x";
    }
    public ArrayList<Integer> getLegalMoves() {
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0;i<9;i++){
            if(state[i].equals("-"))
                list.add(i);
        }
        return list;
    }
    public boolean isWin() {
        return (checkWin(0,1,2) || checkWin(3,4,5) || checkWin(6,7,8) || 
        checkWin(0,3,6) || checkWin(1,4,7) || checkWin(2,5,8) ||
        checkWin(0,4,8) || checkWin(2, 4, 6)); 
    }
    public boolean checkWin(int i1, int i2, int i3){
        if(state[i1].equals(state[i2]) && state[i1].equals(state[i3]) && !state[i1].equals("-")){
            return true;
        }
        return false;
    }
    public boolean isDraw(){
        return getLegalMoves().isEmpty() && !isWin();
    }
}