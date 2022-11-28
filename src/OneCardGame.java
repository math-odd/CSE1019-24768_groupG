
public class OneCardGame implements OneCardGameInterface{
	Dealer dealer;
	HumanPlayer hand_player = new HumanPlayer();
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	
	public boolean isPlayerTurn() {
		return true;
	}

	public boolean putCard(String suit, int rank) {
		
		return true;
	}
	
	public Card topCard() {
		return null;
	}
	
	public Card[] playerCards() {
		//test 용도, 지워도 됌
		Card[] hand2 = {new Card("diamonds", 1), new Card("diamonds", 3), 
				new Card("hearts", 5)};
		return hand2;
		//return hand_player.hand();
	}
	
	public int[] numberOfCards() {
		//test 용도 
		int[] comCards = {4, 8, 10};
		return comCards;
	}
	
}
