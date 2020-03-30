/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mine
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer time;
    private int delay = 8;
    
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }
    
    public void paint(Graphics g) {
        //for setting background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        // for generating map
        map.draw((Graphics2D)g);
        
        // for scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
        //for borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        // for paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 9);
        
        // for ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if (totalbricks <= 0 ){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won!!!", 190, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to restart:", 230, 350);
        }
        
        if (ballposY > 570 ){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores:"+score, 190, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to restart:", 230, 350);
        }
        g.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 588) {
                playerX = 588;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX < 15) {
                playerX = 15;
            } else {
                moveLeft();
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                   score = 0;
                   totalbricks = 21; 
                   playerX = 310;
                   ballposX = 120;
                   ballposY = 350;
                   ballXdir = -1;
                   ballYdir = -2;
                   
                   map = new MapGenerator(3,7);
                   
                   repaint();
            }
        }
    }
    
    public void moveRight(){
        play =true;
        playerX+=20;
    }
    
    public void moveLeft(){
        play =true;
        playerX -= 20;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        time.start();
        if (play){
            if (new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }
            
            A: for (int i=0;i<map.map.length;i++) {
                for (int j=0;j<map.map[0].length;j++){
                    if (map.map[i][j] > 0) {
                        Rectangle brickRect = new Rectangle(j*map.brickWidth+80,i*map.brickHeigth+50,map.brickWidth,map.brickHeigth);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        
                        if (ballRect.intersects(brickRect)){
                            map.setBrickvalue(0,i,j);
                            totalbricks--;
                            score += 10;
                            
                            if (ballposX + 19 < brickRect.x || ballposX + 1 > brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            
                            break A;
                        }
                    }
                }
            }
            
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0){
                ballXdir = -ballXdir;
            }
            if (ballposY < 0){
                ballYdir = -ballYdir;
            }
            if (ballposX > 670){
                ballXdir = -ballXdir;
            }   
        } 
        repaint();
    }
    
    
}
