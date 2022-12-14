import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OneCardWriter extends JPanel implements ActionListener{
	
	private OneCardGame cardGame;
	private static int window_width = 1500;
	private static int window_height = 800;
	private String username = "USER";
	// contains number of cards for each computer 
	private int[] com_cards = new int[3];
	// player card hand
	private Card[] player_cards = new Card[16];
	private CardButton[] player_cards_buttons = new CardButton[16];
	
	private int player_card_count = 0;
	private int turn_count = 0;
	private final Color transparent = new Color(0,0,0,0);
	
	private JPanel playerPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel com1Panel = new JPanel();
	private JPanel tempPanel2 = new JPanel(new GridBagLayout());
	private JPanel com2Panel = new JPanel();
	private JPanel tempPanel3 = new JPanel(new GridBagLayout());
	private JPanel com3Panel = new JPanel();
	private JPanel tempPanel1;
	private JPanel statusPane;
	
	
	private Card putCard;
	private CardImage putCardImg;
	private JLabel putCardLabel;
	private JLabel turnLabel;
	private JLabel deadLabel = new JLabel("DEAD");
	private JLabel statusLabel = new JLabel("Player turn");
	
	private Image card_back_rotated = null;
	private Image card_back = null;
	
	private int state = 0; // import from case in onecardgame
	
	
	public OneCardWriter(OneCardGame cg) {
		deadLabel.setFont(getFont().deriveFont(36f)); // currently unable to use
		setLayout(new BorderLayout());
		JPanel gamePanel = new JPanel(new BorderLayout());
		
		// f.setTitle("OneCard");
		cardGame = cg;
		state = cardGame.state();
		com_cards = cardGame.numberOfComCards();
		//player_cards = cg.playerCards();
		for (int i =0; i<16; i++) {
			player_cards[i] = cardGame.playerCards()[i];
		}
		putCard = cardGame.topCard();
		// putCard = new Card("diamonds", 12); // debug line
		player_card_count = cardGame.playerCardCount();
		username = cardGame.username();
		
		///// IMPORT IMAGES
		// import image card_back
		try { card_back = ImageIO.read(new FileInputStream("img/back.png"));
		} catch (Exception e) { e.printStackTrace(); } 
		
		//resize image
		int n = 6; // image scaler ratio int
		int width = card_back.getWidth(getFocusCycleRootAncestor())/n;
		int height = card_back.getHeight(getFocusCycleRootAncestor())/n;
		card_back = card_back.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		// import rotated image
		try { card_back_rotated = ImageIO.read(new FileInputStream("img/back_rotated.png"));
		} catch (IOException e) { e.printStackTrace(); }
		
		// resize image
		width = card_back_rotated.getWidth(getFocusCycleRootAncestor())/n;
		height = card_back_rotated.getHeight(getFocusCycleRootAncestor())/n;
		card_back_rotated = card_back_rotated.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		// initialize player_cards_buttons
		for (int i =0; i<player_card_count; i++) {
			player_cards_buttons[i]=new CardButton(player_cards[i].suit(), player_cards[i].rank(), this);
		}
		
		// Player Panel
		gamePanel.add(new JLabel("PlayerCards", SwingConstants.CENTER), BorderLayout.SOUTH); // placeholder
		playerPanel.setLayout(new FlowLayout());
		//f.pack();
		for (int i =0; i<player_card_count; i++) {
			playerPanel.add(player_cards_buttons[i]);
			player_cards_buttons[i].addActionListener(this); // currently editing 
		}
		playerPanel.setBackground(transparent); // sets background transparent
		//playerPanel.setPreferredSize(new Dimension(0,250));
//		else playerPanel.setPreferredSize(new Dimension(0,150));
		gamePanel.add(playerPanel, BorderLayout.SOUTH);
		
		// Center Deck Panel
		gamePanel.add(new JLabel("Test", SwingConstants.CENTER), BorderLayout.CENTER); // placeholder
		deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.X_AXIS));
		deckPanel.add(Box.createHorizontalGlue());
		// JLabel backCard = new JLabel(new ImageIcon(card_back)); //save image to jlabel
		//deckPanel.add(backCard);
		putCardImg = new CardImage(putCard.suit(), putCard.rank());
		putCardLabel = new JLabel(new ImageIcon(putCardImg.img()));
		deckPanel.add(putCardLabel); // add jlabel to panel
		deckPanel.add(Box.createRigidArea(new Dimension(8,0)));
		//deckPanel.add(new JLabel(new ImageIcon(card_back)));
		deckPanel.add(new CardDeckButton(this));
		deckPanel.add(Box.createHorizontalGlue());
		deckPanel.setBackground(transparent); // sets background transparent
		gamePanel.add(deckPanel, BorderLayout.CENTER);
		
		// Computer1 Panel
		gamePanel.add(new JLabel("Computer1"), BorderLayout.WEST);
		//tempPanel1 = new JPanel(new GridBagLayout());
		tempPanel1 = new JPanel(new BorderLayout());
		tempPanel1.setBackground(new Color(0,0,0,0));
		com1Panel.setLayout(new OverlapLayout(new Point(0,20)));
		// add cards by number of cards of com1
		for (int i=0; i<com_cards[0]; i++) {
			com1Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com1Panel.setBackground(transparent); // sets background transparent
		// add(com1Panel, BorderLayout.WEST);
		//tempPanel1.add(com1Panel, new GridBagConstraints());
		tempPanel1.add(com1Panel, BorderLayout.CENTER);
		gamePanel.add(tempPanel1, BorderLayout.WEST);
		
		// Computer2 Panel
		gamePanel.add(new JLabel("Computer2", SwingConstants.CENTER), BorderLayout.NORTH);
		tempPanel2.setBackground(new Color(0,0,0,0));
		tempPanel2.setLayout(new FlowLayout());
		com2Panel.setLayout(new OverlapLayout(new Point(20, 0)));
		for (int i=0; i<com_cards[1]; i++) {
			com2Panel.add(new JLabel(new ImageIcon(card_back)));
		}
		com2Panel.setBackground(new Color(0,0,0,0)); // sets background transparent
		//add(com2Panel, BorderLayout.NORTH);
		tempPanel2.add(com2Panel);
		gamePanel.add(tempPanel2, BorderLayout.NORTH);
		
		// Computer3 Panel
		gamePanel.add(new JLabel("Computer3"), BorderLayout.EAST);
		// if solved cards cut off issue & panel not readjusting size issue
		// change to GridBagLayout() to center cards.
		tempPanel3.setLayout(new BorderLayout()); 
		tempPanel3.setBackground(transparent);
		com3Panel.setLayout(new OverlapLayout(new Point(0,20)));
		for (int i=0; i<com_cards[2]; i++) {
			JLabel l = new JLabel(new ImageIcon(card_back_rotated));
			com3Panel.add(l);
			//com3Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com3Panel.setBackground(transparent); // sets background transparent
		tempPanel3.add(com3Panel);
		gamePanel.add(tempPanel3, BorderLayout.EAST);
		
		// Status Pane
		JPanel bufferPane = new JPanel(new BorderLayout());
		statusPane = new JPanel();
		statusPane.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JLabel userLabel = new JLabel(username);
		statusPane.add(userLabel);
		turnLabel = new JLabel("TURN: "+ turn_count);
		statusPane.add(turnLabel);
		int chips_count = cardGame.chips(); 
		JLabel chips = new JLabel("CHIPS: " + chips_count);
		statusPane.add(chips);
		//statusPane.setBackground(new Color(235, 235, 235));
		bufferPane.add(statusPane, BorderLayout.EAST);
		JPanel statusPane2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		statusPane2.add(statusLabel);
		bufferPane.add(statusPane2, BorderLayout.WEST);
		bufferPane.setBackground(new Color(235, 235, 235));
		
		add(bufferPane, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);
		gamePanel.setBackground(transparent);
		
		
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
		// is it possible that it readjusts according to the panel?
		// do i have to make a specified panel and set graphics for it? 
		// are coordinates specific to a panel? 
		
		
		
		
		// cases = lose/win
		if (state != 0) { // player win 
			g.setColor(new Color(0, 0, 0, 135));
			g.fillRect(0, 0, getWidth(), getHeight());
			int w = 500;
			int h = 200;
			g.setColor(Color.white);
			g.fillRoundRect(getWidth()/2-(w/2), getHeight()/2-(h/2)-20, w, h, 50, 50);
			g.setColor(Color.black);
			g.drawRoundRect(getWidth()/2-(w/2), getHeight()/2-(h/2)-20, w, h, 50, 50);
			g.setFont(getFont().deriveFont(72f));
			Timer timer2 = new Timer(5000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					regame();
					((Timer)e.getSource()).stop();
				}
			});
	        if (state == 2) {   
	            g.drawString("YOU LOSE", getWidth()/2-170, getHeight()/2);
	            //1. g.drawString을 5초 가량 띄운 후 게임창을 닫는다. 
	            //2. regame 하겠냐는 물음을 담은 패널창을 띄운다 (with button yes or no)
	            //3. 만약 대답이 yes라면, 게임의 내용을 초기화한 후 new onecardgamewriter을 만들어서 실행한다.
	            // 패널은 무슨 JOptionPane 이용함 
	            playerPanel.removeAll();
		        deckPanel.removeAll();
	            timer2.start();
	        }
	        else if (state ==1){ 
	            g.drawString("YOU WIN", getWidth()/2-150, getHeight()/2);
	            playerPanel.removeAll();
		        deckPanel.removeAll();
				timer2.start();
	        }
		}
	}
	
	public void regame(){
		setVisible(false);
		Object[] options1 = {"YES", "NO"};
		int input =  JOptionPane.showOptionDialog(null,"Do you want to continue?",
    			"Regame",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null, options1,null);

		
		
		if (input == 0) {  //input == yes */
        	OneCardGame cardGame = new OneCardGame(new Dealer());
        	OneCardWriter gameWriter = new OneCardWriter(cardGame);
        	JFrame f = new JFrame("OneCard");
        	f.setContentPane(gameWriter);
        	f.setSize(window_width, window_height);
        	f.setLocationRelativeTo(null);
        	f.setVisible(true);
        	f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
	
	// WIP : 어느 카드를 x1 y1 에서 x2 y2로 애니매이션 
	public void animate(String s, int r, int x1, int y1, int x2, int y2) {
		new CardImage(s, r);
//		int n = 100;
//		int x_coord = x1;
//		int y_coord = y1;
//		int x_velocity = (x2-x1)/n;
//		int y_velocity = (y2-y1)/n;
		
//		Timer timer2 = new Timer(100, new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				myself.setAlignmentX(myself.getX()+x_velocity);
//				myself.setAlignmentY(myself.getAlignmentY()+y_velocity);
//				if (myself.getX() == x2 && myself.getY() == y2) {
//					((Timer)e.getSource()).stop();
//				}
//				repaint();
//			}
//		});
//		timer2.start();
		
	}
	
	// CARD BUTTON PRESSED
	public void putCard(String s, int r) {
		// System.out.println(s+ " " + Integer.toString(r)); debug line
		
		if (cardGame.putCard(s, r)) {
			for (int i =0; i<player_card_count; i++) {
				if (player_cards_buttons[i] != null && player_cards_buttons[i].suit().equals(s) && player_cards_buttons[i].rank() == r) {
					// 애니매이션 WIP
//					final int num = i;
//					int x2 = window_width/2; int y2 = window_height/2;
//					int x_increment = (x2 - player_cards_buttons[num].getX())/50;
//					
//					int y_increment = Math.abs(y2 - player_cards_buttons[num].getY())/50;
//					System.out.println(x_increment + " " + y_increment);
//					Timer timer2 = new Timer(100, new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							player_cards_buttons[num].setLocation(player_cards_buttons[num].getX()+x_increment, player_cards_buttons[num].getY()-y_increment);
//							if (player_cards_buttons[num].getY() <= y2) ((Timer)e.getSource()).stop();
//							repaint();
//						}
//					});
//					timer2.start();
					
					//playerPanel.remove(player_cards_buttons[i]);
					//animate(s,r, player_cards_buttons[i].getX(), player_cards_buttons[i].getY(), putCardLabel.getX(), putCardLabel.getY());
					for (int j = i ; j<player_card_count; j++) {
						if (j == player_card_count-1) player_cards_buttons[j] = null;
						else player_cards_buttons[j] = player_cards_buttons[j+1];
					}
				}
			}
			// decrement player's card count
			player_card_count -=1;
			
			playerPanel.validate();
			repaint();
			refresh();
		}
		//debug line
		// state =2; deckPanel.removeAll();
		repaint();
	}
	
	// end turn and pass turn to next player
	public void endTurn() {
		// 카드를 뽑는다 
		// 턴을 다음 턴으로 넘긴다 
		cardGame.endPlayerTurn();
		refresh();
	}

	public static void main(String[] args) {
		OneCardGame cardGame = new OneCardGame(new Dealer());
		OneCardWriter gameWriter = new OneCardWriter(cardGame);
		JFrame f = new JFrame("OneCard");
		f.setContentPane(gameWriter);
		f.setSize(window_width, window_height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//gameWriter.refresh();
	}

	
	private void updateComp(JPanel panel, int actual, int n) {
		int current = com_cards[n];
		Image temp = card_back_rotated;
		if (n == 1) temp = card_back;
		
		if (current != actual && actual < 17) {
			panel.removeAll(); 
			for (int i = 0; i < actual; i++) {
				panel.add(new JLabel(new ImageIcon(temp)));
			}
			panel.validate();
			if (panel == com2Panel) {
				panel.setSize(400, panel.getHeight());
				tempPanel2.add(panel);
			}
			
		}
		
		// if comp died
		if (actual >= 17) {
			panel.removeAll();
			
			panel.add(deadLabel);
		}
		
		putCard = cardGame.topCard();
		repaint();
	}
	
	private void updatePlayer(JPanel panel, Card[] player_cards, int player_card_count) {
		panel.removeAll();
		//playerPanel.setLayout(new FlowLayout());
		
		for (int i =0; i<player_card_count; i++) {
			player_cards_buttons[i]=new CardButton(player_cards[i].suit(), player_cards[i].rank(), this);
			panel.add(player_cards_buttons[i]);
		}
		
		// 2 rows?
		//if (player_card_count>11) panel.setPreferredSize(new Dimension(this.getWidth(), 280));
		//else panel.setPreferredSize(new Dimension(this.getWidth(), 180));
//		else panel.setSize(new Dimension(this.getWidth(),150));
		panel.validate();
		repaint();
	}
	
	private void refresh(){
		// computer cards를 하나씩 숫자에 맞게 맞추기
		// updateComp(com#Panel, updated num of com cards, com number (0/1/2))
		/*
			for (int i=now_turn; i<=turn_count; i++) {
				cp 누가, take/attacktake/play , cardnum
				boolean play = true;
				if (!play) playComp(com1/2/3, numberofcards); //animation of take
				updateComp(com1/2/3, ...);
				now_turn++;
			}
			플레이어가 얼마나 받았나 
		*/
		updateComp(com1Panel, cardGame.numberOfComCards()[0], 0);
		updateComp(com2Panel, cardGame.numberOfComCards()[1], 1);
		updateComp(com3Panel, cardGame.numberOfComCards()[2], 2);
		setTopCard();

		// player cards array를 비우고 새로 채운다 
		Arrays.fill(player_cards, null);
		player_card_count = cardGame.playerCardCount();
		System.arraycopy(cardGame.playerCards(), 0, player_cards, 0, player_card_count);
		updatePlayer(playerPanel, player_cards, player_card_count);
		
		// increment turn count at the end of player turn 
		// will be useful if dynamic number of players 
		turn_count = cardGame.turnCount(); // increase by the number of players
		turnLabel.setText("TURN: " + turn_count);
		
		// update status label
		statusLabel.setText(cardGame.gameLog().get(turn_count-1));
		
		// check if player lose/win
	    state = cardGame.state();
		repaint();
	}
	
	// sets the top card of the thrown deck.
	private void setTopCard() {
		putCard = cardGame.topCard();
		// putCard = new Card("spades", 1); // for debugging
		putCardImg = new CardImage(putCard.suit(), putCard.rank());
		putCardLabel.setIcon(new ImageIcon(putCardImg.img()));
	}
	
	public void setUsername(String text) {
		// cardGame.setUsername(text); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("test");
		
		
	}
}

/// delay / wait / timer 하는 방법?
//try {
//System.out.println("sleeping");
//for (int i =0; i<10; i++) {
//Thread.sleep(100);
//this.repaint();}
//System.out.println("done sleeping");
//} catch(InterruptedException e) {}