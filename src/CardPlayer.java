public class CardPlayer implements Dealer {

    private Card[] hand;
    private int card_count;
    public int idx = 0;
    Dealer dealer; 
    
    
    /*
     * <Controller>
     * 
     * Dealer d = new Dealer();
     * 
     * 
     * CardPlayer com1 = new CardPlayer(d,16)
     * CardPlayer com2 = new CardPlayer(d,16)
     * CardPlayer com3 = new CardPlayer(d,16)
     * 
     * 
     * */
    
    public CardPlayer(Dealer d,int max_cards) {
        hand = new Card[max_cards];
        card_count = 0;
        dealer = d; 
    }

    public void receiveCard(Card d) {
            hand[card_count] = d;
            card_count += 1;
    }
    
    public Card [] hand() {
    	return hand;
    }
    
    public int cardCount() {
    	return card_count;
    }
    
    
    // 놓여진 카드(put) 과 hand 중 규칙에 맞게 낼 수 있는 카드 array를 return한다. 
    //joker는 흑색이든 컬러든 모든 카드 낼 수 있다고 가정하겠습니다!!
    public Card[] possible_cards(Card put) {
        Card[] cards = new Card[card_count]; 
        int cc = 0;
        for(int i=0; i< card_count; i++) {
    		if(hand[i].rank() == put.rank() || hand[i].suit() == put.suit() || hand[i].rank() == 0)
    		{	cards[cc] = hand[i];
    			cc++;
    		}
    	}
        return cards;
    }
    
    // thrown에 낸 카드 c를 인수로 받아서 
    public void putCard(Card c) {
    	for (int i = 0; i < card_count; i++)
    	{
    		if(hand[i].suit() == c.suit() && hand[i].rank() == c.rank())
    			hand[i] = null;
    	}
    	for(int i = 0; i < card_count; i++) {
    		if(hand[i] == null)
    		{	
    			hand[i] = hand[i+1];
    			hand[i+1] = null;
    		}
    	}
    	card_count--;
    }
}
