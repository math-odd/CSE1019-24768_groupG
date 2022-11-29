import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CardButton extends JButton implements ActionListener{
    private String suit; //hearts, spades, clubs, diamonds
    private String rank;
    private int width;
    private int height;

    public CardButton(String s, int r) {
    	suit = s;
    	if (r ==1) rank = "ace";
    	else if (r==11) rank = "jack";
    	else if (r== 12) rank = "queen";
    	else if (r==13) rank = "king";
    	else rank = Integer.toString(r);
    	
    	// 
    	try {
    		Image img = ImageIO.read(new FileInputStream("img/cards/"+rank + "_of_" + suit+".png"));
    		int n = 6; // image scaler ratio int
			width = img.getWidth(getFocusCycleRootAncestor())/n;
			height = img.getHeight(getFocusCycleRootAncestor())/n;
			img = img.getScaledInstance(width, height, r);
			setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
				}
    	
    	// remove button border
    	setBorder(BorderFactory.createEmptyBorder());
    	setContentAreaFilled(false);
    	
    	setSize(width, height);
    }

	public void actionPerformed(ActionEvent e) {
		System.out.println("c");
	}
    
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(2,8));
		f.add(new CardButton("diamonds", 1));
		f.add(new CardButton("diamonds", 2));
		f.add(new CardButton("diamonds", 5));
		f.add(new CardButton("diamonds", 7));
		f.add(new CardButton("diamonds", 12));
		f.add(new CardButton("hearts", 1));
		f.add(new CardButton("hearts", 4));
		f.add(new CardButton("hearts", 5));
		f.add(new CardButton("hearts", 6));
		f.add(new CardButton("hearts", 7));
		f.add(new CardButton("clubs", 10));
		f.add(new CardButton("spades", 7));
		f.add(new CardButton("spades", 8));
		f.add(new CardButton("spades", 10));
		f.add(new CardButton("spades", 12));
		f.add(new CardButton("spades", 13));
		f.pack();
		//f.setSize(800,600);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
}