import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OneCardWriter extends JPanel {

	private OneCardGame cardGame;
	private int window_width = 1100;
	private int window_height = 800;
	// contains number of cards for each computer 
	private int[] com_cards = new int[3];
	// player card hand
	private Card[] player_cards = new Card[16];
	private CardButton[] player_cards_buttons = new CardButton[16];
	
	private JPanel playerPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel com1Panel = new JPanel();
	private JPanel tempPanel2 = new JPanel(new GridBagLayout());
	private JPanel com2Panel = new JPanel();
	private JPanel tempPanel3 = new JPanel(new GridBagLayout());
	private JPanel com3Panel = new JPanel();
	
	public OneCardWriter(OneCardGame cg) {
		JFrame f = new JFrame();
		f.setTitle("OneCard");
		cardGame = cg;
		com_cards = cg.numberOfCards();
		player_cards = cg.playerCards();
		setLayout(new BorderLayout());
		// initialize player_cards_buttons
		for (int i =0; i<player_cards.length; i++) {
			player_cards_buttons[i]=new CardButton(player_cards[i].suit(), player_cards[i].rank(), this);
		}
		
		// Player Panel
		add(new JLabel("PlayerCards", SwingConstants.CENTER), BorderLayout.SOUTH); // placeholder
		playerPanel.setLayout(new FlowLayout());
		for (int i =0; i<player_cards.length; i++) {
			playerPanel.add(player_cards_buttons[i]);
		}
		playerPanel.setBackground(new Color(0,0,0,0)); // sets background transparent
		if (player_cards.length>11) playerPanel.setPreferredSize(new Dimension(0,280));
		else playerPanel.setPreferredSize(new Dimension(0,150));
		add(playerPanel, BorderLayout.SOUTH);
		
		// Center Deck Panel
		add(new JLabel("Test", SwingConstants.CENTER), BorderLayout.CENTER); // placeholder
		deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.X_AXIS));
		deckPanel.add(Box.createHorizontalGlue());
		// back of card image
		Image card_back = null;
		// load image file
		try {
			card_back = ImageIO.read(new FileInputStream("img/back.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//resize image
		int n = 6; // image scaler ratio int
		int width = card_back.getWidth(getFocusCycleRootAncestor())/n;
		int height = card_back.getHeight(getFocusCycleRootAncestor())/n;
		card_back = card_back.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		JLabel backCard = new JLabel(new ImageIcon(card_back)); //save image to jlabel
		deckPanel.add(backCard); // add jlabel to panel
		deckPanel.add(Box.createRigidArea(new Dimension(8,0)));
		deckPanel.add(new JLabel(new ImageIcon(card_back)));
		deckPanel.add(Box.createHorizontalGlue());
		deckPanel.setBackground(new Color(0,0,0,0)); // sets background transparent
		add(deckPanel, BorderLayout.CENTER);
		
		// Computer1 Panel
		add(new JLabel("Computer1"), BorderLayout.WEST);
		JPanel tempPanel1 = new JPanel(new GridBagLayout());
		tempPanel1.setBackground(new Color(0,0,0,0));
		//com1Panel.setBounds(61, 11, 81, 140);
		com1Panel.setLayout(new BoxLayout(com1Panel, BoxLayout.Y_AXIS));
		// rotate backcard image
		Image card_back_rotated = null;
		try {
			card_back_rotated = ImageIO.read(new FileInputStream("img/back_rotated.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		// resize image
		width = card_back_rotated.getWidth(getFocusCycleRootAncestor())/n;
		height = card_back_rotated.getHeight(getFocusCycleRootAncestor())/n;
		card_back_rotated = card_back_rotated.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		// set image to JLabel
		// JLabel backCard_r = new JLabel(new ImageIcon(card_back_rotated));
		// add cards by number of cards of com1
		for (int i=0; i<com_cards[0]; i++) {
			com1Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com1Panel.setBackground(new Color(0,0,0,0)); // sets background transparent
		// add(com1Panel, BorderLayout.WEST);
		tempPanel1.add(com1Panel, new GridBagConstraints());
		add(tempPanel1, BorderLayout.WEST);
		
		// Computer2 Panel
		add(new JLabel("Computer2", SwingConstants.CENTER), BorderLayout.NORTH);
		tempPanel2.setBackground(new Color(0,0,0,0));
		com2Panel.setLayout(new OverlapLayout(new Point(20, 0)));
		for (int i=0; i<com_cards[1]; i++) {
			com2Panel.add(new JLabel(new ImageIcon(card_back)));
		}
		com2Panel.setBackground(new Color(0,0,0,0)); // sets background transparent
		//add(com2Panel, BorderLayout.NORTH);
		tempPanel2.add(com2Panel, new GridBagConstraints());
		add(tempPanel2, BorderLayout.NORTH);
		
		// Computer3 Panel
		add(new JLabel("Computer3"), BorderLayout.EAST);
		tempPanel3.setBackground(new Color(0,0,0,0));
		com3Panel.setLayout(new OverlapLayout(new Point(0,20)));
		for (int i=0; i<com_cards[2]; i++) {
			com3Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com3Panel.setBackground(new Color(0,0,0,0)); // sets background transparent
		//add(com3Panel, BorderLayout.EAST);
		tempPanel3.add(com3Panel, new GridBagConstraints());
		add(tempPanel3, BorderLayout.EAST);
		
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
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// draw grid lines (as background)
		g.setColor(new Color(200, 200, 200)); // light grey + a bit darker
		
		// thicken line
		//Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(3));
		
		// vertical lines
		int vertical_line_count = 7;
		for (int i =window_width/vertical_line_count; i < window_width+300; i+=window_width/vertical_line_count) {
			g.drawLine(i, 0, i, window_height*2);
		}
		// horizontal lines
		int horizontal_line_count = 4;
		for (int i =window_height/horizontal_line_count; i < window_height+300; i+=window_height/horizontal_line_count) {
			g.drawLine(0, i, window_width*2, i);
		}
		
		// rounded rectangle background for player hand
		// g.fillRoundRect(150, window_height/2+150, player_card_bg_w, 100, 15, 15);		
	}

	
	public void putCard(String s, int r) {
		System.out.println(s+ " " + Integer.toString(r));
		if (cardGame.putCard(s, r)) {
			for (int i =0; i<player_cards.length; i++) {
				
			}
			//playerPanel.remove(com1Panel);
			refresh();
		}
	}

	public static void main(String[] args) {
		OneCardGame cardGame = new OneCardGame();
		OneCardWriter gameWriter = new OneCardWriter(cardGame);
	}

	private void refresh(){
		
	}
}

