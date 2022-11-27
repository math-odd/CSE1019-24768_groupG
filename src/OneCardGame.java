
public class OneCardGame implements OneCardGameInterface{
	Dealer dealer;
	HumanPlayer hand_player;
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	
	@Override
	public boolean isPlayerTurn() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void putCard(String suit, int rank) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Card topCard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Card[] playerCards() {
		return null;
	}
	
	public int[] numberOfCards() {
		return null;
	}
	
}
