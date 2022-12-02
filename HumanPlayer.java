
public class HumanPlayer extends CardPlayer{
private String name;
private int chips = 0;

	public HumanPlayer(int max_cards, String n) {
		super(max_cards);
		n = name;
	}

	/*              !!!!!!!!!!!!!!!!!!!!!!!!!!얘는 권한이 없어!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public Card[] choice_card(Card[] possible_cards, Card put) {
		return super.possible_cards(put);
	}
	 */
	public void win() {chips += 3;}
	public void loose() {chips -= 1;}
	public int chips() {return chips;}
}
