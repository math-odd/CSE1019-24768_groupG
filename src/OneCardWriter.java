import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

public class OneCardWriter extends JPanel implements ActionListener{

	private OneCardGame cardGame;
	private int window_width = 1100;
	private int window_height = 800;
	// contains number of cards for each computer 
	private int[] com_cards = new int[3];
	//playercard background rounded rectangle's width
	private int player_card_bg_w = window_width-(150*2);
	// player card hand
	private Card[] player_cards;
	
	
	public OneCardWriter(OneCardGame cg) {
		JFrame f = new JFrame();
		f.setTitle("OneCard");
		cardGame = cg;
		com_cards = cg.numberOfCards();
		player_cards = cg.playerCards();
		
		JPanel playerPanel = new JPanel();
		playerPanel.add(new CardButton("Diamond", 13));
		
		setLayout(new BorderLayout());
		add(new JLabel("Test", SwingConstants.CENTER), BorderLayout.CENTER);
		add(new JLabel("PlayerCards", SwingConstants.CENTER), BorderLayout.SOUTH);
		// add(playerPanel, BorderLayout.SOUTH);
		add(new JLabel("Computer1"), BorderLayout.WEST);
		add(new JLabel("Computer2", SwingConstants.CENTER), BorderLayout.NORTH);
		add(new JLabel("Computer3"), BorderLayout.EAST);
		
		
		
		
		f.getContentPane().add(this);
		

		f.setSize(window_width, window_height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	// BACKGROUND
	public void paintComponent(Graphics g) {
		// fill background with white
		g.setColor(new Color(235, 235, 235)); // light grey
		g.fillRect(0, 0, window_width, window_height);
		
		// draw grid lines (as background)
		g.setColor(new Color(200, 200, 200)); // light grey + a bit darker
		
		// thicken line
		//Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(3));
		
		// vertical lines
		int vertical_line_count = 7;
		for (int i =window_width/vertical_line_count; i < window_width; i+=window_width/vertical_line_count) {
			g.drawLine(i, 0, i, window_height);
		}
		// horizontal lines
		int horizontal_line_count = 4;
		for (int i =window_height/horizontal_line_count; i < window_height; i+=window_height/horizontal_line_count) {
			g.drawLine(0, i, window_width, i);
		}
		
		// rounded rectangle background for player hand
		g.fillRoundRect(150, window_height/2+150, player_card_bg_w, 100, 15, 15);
	}

	public void actionPerformed(ActionEvent e) {
		if (cardGame.isPlayerTurn()) {
			// enable pressing buttons
		}
	}

	public static void main(String[] args) {
		OneCardGame cardGame = new OneCardGame();
		OneCardWriter gameWriter = new OneCardWriter(cardGame);
	}
}


// custom panel for player cards
class PlayerCards extends JPanel implements ActionListener{

	private int total_cards;
	
	
	public void PlayerCards(Card[] cards) {
		total_cards = cards.length;
		setLayout(new GridLayout(1,total_cards));
		
		for (int i =0; i<cards.length; i++) {
			this.add(new JLabel(cards[i].suit()));
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	}
	
}





/* mouse listener is for deeper, detailed mouse actions such as whether the mouse is touching the object
addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent me) {
        super.mouseClicked(me);
        for (Shape s : shapes) {
            
            if (s.contains(me.getPoint())) {//check if mouse is clicked within shape
                
                //we can either just print out the object class name
                System.out.println("Clicked a "+s.getClass().getName());
                
                //or check the shape class we are dealing with using instance of with nested if
                if (s instanceof Rectangle2D) {
                    System.out.println("Clicked a rectangle");
                } else if (s instanceof Ellipse2D) {
                    System.out.println("Clicked a circle");
                }
                
            }
        }
    }
});*/
//Graphics2D g2d = (Graphics2D) grphcs;
//for (Shape s : shapes) {
//  g2d.draw(s);
//}