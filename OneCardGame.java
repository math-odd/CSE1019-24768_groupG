import javax.swing.*;
public class OneCardGame implements OneCardGameInterface{
	Dealer dealer;
	HumanPlayer hand_player;
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	CardPlayer[] turn;
	int max_cards = 16;
	int nowTurn = 0;

	public OneCardGame(Dealer d) {
		dealer = d;
		String name = JOptionPane.showInputDialog("What is your name?");
		hand_player = new HumanPlayer(max_cards, name);
		hand_com1 = new ComputerPlayer(max_cards);
		hand_com2 = new ComputerPlayer(max_cards);
		hand_com3 = new ComputerPlayer(max_cards);
		turn[0] = hand_player;
		turn[1] = hand_com1;
		turn[2] = hand_com2;
		turn[3] = hand_com3;
	}
	
	@Override
	public boolean isPlayerTurn() {
		if (nowTurn == 0)
			return true;
		else
			return false;
	}
	@Override
	public void putCard(String suit, int rank) {
		Card c = new Card(suit, rank);
		dealer.Thrown_Card(c);
		
	}
	@Override
	public Card topCard() {
		Card top = dealer.topCard();
		return top;
	}
	
	public Card[] playerCards() {
		return hand_player.hand(); 
	}
	
	public int[] numberOfCards() {
		int handArr[] = new int[3];
		handArr[0] = (hand_com1.hand()).length();
		handArr[1] = (hand_com2.hand()).length();
		handArr[2] = (hand_com3.hand()).length();
		return handArr;
	}
	
}
