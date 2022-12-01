
public abstract class HumanPlayer extends CardPlayer{
private String name;
private int chips;

	public HumanPlayer(Dealer d,int max_cards, String n) {
		super(d, max_cards);
		n = name;
	}
	public void win() {
		chips = chips + 3;
	}
	public void loose() {
		chips = chips - 1;
	}
	public int chips() {
		return chips;
	}
	public Card[] hand() {
		return super.hand();
	}
	public String name() {
		return name;
	}
	public boolean possible(Card put) {
		if(possible_cards(put).length > 0)
			return true;
		else
			return false;
	}
	public Card[] choice_card(Card[] possible_cards, Card put) {
		return super.possible_cards(put);
	}
}		
