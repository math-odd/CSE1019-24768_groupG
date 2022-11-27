import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OneCardWriter extends JPanel implements ActionListener{

	private OneCardGame cardGame;
	private int window_width = 800;
	private int window_height = 600;
	
	public OneCardWriter(OneCardGame cg) {
		JFrame f = new JFrame();
		f.setTitle("OneCard");
		cardGame = cg;
		
		setLayout(new BorderLayout());
		add(new JLabel("Test", SwingConstants.CENTER), BorderLayout.CENTER);
		add(new JLabel("PlayerCards", SwingConstants.CENTER), BorderLayout.SOUTH);
		add(new JLabel("Computer1"), BorderLayout.WEST);
		add(new JLabel("Computer2", SwingConstants.CENTER), BorderLayout.NORTH);
		add(new JLabel("Computer3"), BorderLayout.EAST);
		
		
		f.getContentPane().add(this);
		//f.add(gamePanel);
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
