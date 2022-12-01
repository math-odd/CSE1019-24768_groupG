public abstract class CardPlayer implements CardPlayerBehavior {

    private Card[] hand;
    //private int card_count;
    public int idx = 0;
    
    public CardPlayer(int max_cards) {
        hand = new Card[max_cards];
        //card_count = 0;
    }

    public void receivedCard(Card d) {
        hand[hand.length] = d;
    }
   
    public boolean possible(Card put) {
		if(possible_cards(put).length > 0)
			return true;
		else
			return false;
    }
    public Card[] hand() {
    	return hand;
    }
    /* 
    public int cardCount() {
    	return card_count;
    }*/
    
    public Card[] possible_cards(Card put) { // 얘를 공격카드인지까지 보고 판단해야지 // 내가 다시 쓰고만다.

        Card[] cards = new Card[hand.length];
        int cc = 0;
        if (put.rank() != 0 && put.rank() != 1 && put.rank() != 2){
            for(int i=0; i< hand.length; i++) {
                if(hand[i].rank() == put.rank() || hand[i].suit() == put.suit())
                {	cards[cc] = hand[i];
                    cc++;
                }	
            }
        }
        else if(put.rank() == 2) {
            for(int i=0; i< hand.length; i++) {
                if(hand[i].rank() == put.rank() || 
                    (hand[i].suit() == put.suit() && put.rank() == 1)
                    && hand[i].rank() == 0)
                {	cards[cc] = hand[i];
                    cc++;
                }
            }
        }
        else if(put.rank() == 1) {
            for(int i=0; i< hand.length; i++) {
                if(hand[i].rank() == put.rank() || hand[i].suit() == put.suit())
                {	cards[cc] = hand[i];
                    cc++;
                }
            }
        }
        else if(put.suit() =="blackjoker"){
            for(int i=0; i< hand.length; i++) {
                if(hand[i].suit() == "colorjoker")
                {	cards[cc] = hand[i];
                    cc++;
                }
            }
        }
        /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!개인적으로 스페이드A 룰
        else {
            for(int i=0; i< hand.length; i++) {
               if(hand[i].suit() == "spades" && hand[i].rank() == 1) =======>>> 이거 적용되면 요기줄 다 추가해야함
               {	cards[cc] = hand[i];
                   cc++;
               }
        }
         */
        ///////////////////          !!!!!!!!!!!      나머지 특수카드만 예외 필요하면 넣기.
        return cards;
    }
    /* 
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
    }*/

}