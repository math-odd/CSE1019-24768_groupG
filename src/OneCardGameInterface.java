
public interface OneCardGameInterface {
	
	/*
	 * checks if human_player's turn
	 */
	public boolean isPlayerTurn();
	/*
	 * put player's card (suit, rank) onto thrown deck
	 */
	public void putCard(String suit, int rank);
	public Card topCard();
	
	/*
	 * returns possible cards of the human player
	 */
	public Card[] possible();
	
	/*
	 * returns number of cards for all computers in this order: (com1, com2, com3)
	 */
	public int[] numberOfCards();
}
