import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
//import java.util.Timer;
import javax.swing.WindowConstants;

public class CardButton extends JButton implements ActionListener{
    private String suit; //hearts, spades, clubs, diamonds, blackjoker, redjoker
    private String rank;
    private int rank_int;
    private int width;
    private int height;
    private OneCardWriter oneCardWriter;
    private CardButton myself = this;
    
    public String suit() {
    	return suit;
    }
    public int rank() {
    	return rank_int;
    }

    public CardButton(String s, int r, OneCardWriter ocw) {
    	oneCardWriter = ocw;
    	suit = s;
    	rank_int = r;
    	setImage(suit, rank_int);
    	
    	// remove button border
    	setBorder(BorderFactory.createEmptyBorder());
    	setContentAreaFilled(false);
    	addActionListener(this);
    	setSize(width, height);
    	setToolTipText("SUIT: " + suit.toUpperCase() + " RANK: " + rank_int);
    }

	public void actionPerformed(ActionEvent e) {
		
		int x_velocity = 40;
		int y_velocity = 40;
		
		Timer timer2 = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myself.setAlignmentX(myself.getX()+x_velocity);
				myself.setAlignmentY(myself.getAlignmentY()+y_velocity);
				if (myself.getX() == 200 && myself.getY() == 200) {
					((Timer)e.getSource()).stop();
				}
				repaint();
			}
		});
		timer2.start();
		
		oneCardWriter.putCard(suit, rank_int);
		
	}
    
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(2,8));
		f.pack();
		//f.setSize(800,600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void setImage(String suit, int rank_int) {
		if (rank_int ==1) rank = "ace";
    	else if (rank_int==11) rank = "jack";
    	else if (rank_int== 12) rank = "queen";
    	else if (rank_int==13) rank = "king";
    	else rank = Integer.toString(rank_int);
		Image img = null;
    	// 
    	try {
    		if (suit.equals("blackjoker")) {
    			img = ImageIO.read(new FileInputStream("img/cards/black_joker.png"));
    		}
    		else if (suit.equals("colorjoker")) {
    			img = ImageIO.read(new FileInputStream("img/cards/red_joker.png"));
    		}
    		else {
    			img = ImageIO.read(new FileInputStream("img/cards/"+rank + "_of_" + suit+".png"));
    		}
    		int n = 6; // image scaler ratio int
			width = img.getWidth(getFocusCycleRootAncestor())/n;
			height = img.getHeight(getFocusCycleRootAncestor())/n;
			img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
		}
	}

	
	
}