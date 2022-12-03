public class CardPlayer {

    private Card[] hand;
    private int card_count;
    public int idx = 0;
    Dealer dealer; 
    
    private Card[] possible;
    private int possible_count;
    
    
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

    //!!//공격카드는 어떻게 하고, 공격이 끝난 다음은 어떻게 할 것인가? 7일때는?

    public Card[] possible_cards(Card put) {
    //!!!!!!///// 7일때 모튼 카드를 내는 것이 가능한것으로 바꿨다!!!
        possible = new Card[card_count]; 
        possible_count = 0;
        if(put.rank() != 7){
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == put.rank() || hand[i].suit() == put.suit() || hand[i].rank() == 0)
                {	possible[possible_count] = hand[i];
                    possible_count++;
                }
            }
        }
        else{
            possible = hand;
            possible_count = card_count;
        }
        
        return possible;
    }
    
    public boolean possible(Card put) {
    	this.possible_cards(put);
    	
    	if(possible_count > 0)  
			return true;
		else
			return false;
    }
/////////#############여기부터 추가부분############//////////
/////////////////공격카드일때만 쓰는 possible.
    public Card[] possible_attack_cards(Card put) {
        possible = new Card[card_count]; 
        possible_count = 0;
        if (put.rank() == 2){
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0 || hand[i].rank() ==  1 
                    || hand[i].rank() == 2){
                    possible[possible_count] = hand[i];
                    possible_count++;
                }
            }
        }
        else if (put.rank() == 1){
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0 || hand[i].rank() == 1){
                    possible[possible_count] = hand[i];
                    possible_count++;
                }
            }
        }
        else{ //put.rank() == 0
            for(int i=0; i<card_count; i++) {
                if(hand[i].rank() == 0){
                    possible[possible_count] = hand[i];
                    possible_count++;
                }
            }
        }

        return possible;
    }

    public boolean possible_attack(Card put) {
    	this.possible_attack_cards(put);
    	
    	if(possible_count > 0)  
			return true;
		else
			return false;
    }
/////////#############여기까지 추가부분############//////////
    
    
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