import java.util.Arrays;

public class CardDeckPut {
	
	Card[] thrown = new Card[54];
	int card_count;
	CardDeckStart start_deck;
	
	
	//start_deck을 인수로 받아 연결시킨다. 받은start_deck을 이용할 수 있다(추가/제거) 
	public CardDeckPut(CardDeckStart s) {
		start_deck = s;
	}
	
	//받은 카드를 덱에 추가한다
	public void receiveCard(Card c) {
		thrown[card_count] = c;
		card_count+=1;
	}
	
	//Card[] --> void로 수정 ?? 뭐지 수정이 필
	public void remove() {
		Card temp = thrown[card_count-1];
		start_deck.appendDeck(Arrays.copyOfRange(thrown, 0, card_count-2), card_count-1);
		Arrays.fill(thrown, null);
		thrown[0]= temp;
		}
	
	public Card cardOnTop() {return thrown[card_count];}
	
	public Card[] re() {return thrown;}
	
	/*public static void main(String[] args) {
		CardDeckStart s = new CardDeckStart();
		CardDeckPut p = new CardDeckPut(s);
		
		
		for (int i=0; i<54;i++)
			p.receiveCard(s.newCard());
		p.remove();
		System.out.println(p.card_count);
		
		for (Card x : p.re())
			System.out.println(x.suit() + " " + x.rank());
		
		
	}*/
}
