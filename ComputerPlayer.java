public class ComputerPlayer extends CardPlayer{
		
	public int computer_id; //컴퓨터 플레이어의 수? 
		
	public ComputerPlayer(Dealer d,int max_cards, int id) {
		super(d, max_cards);
		computer_id = id;
	}
	
	
	//!!!!!///		이거 공격이 끝나도 공격으로 인식할 가능성 있어서 고쳤음
	public void takeCard(Card put, boolean attack) {
		//놓여진 카드 put에 맞는 possible_cards 를 얻는다.
		Card[] arr = super.possible_cards(put);
		if(attack) {arr = super.possible_attack_cards(put);}
		
		boolean att = false; //내 패가 att있냐없냐 
		
		if(arr != null) {
			if(attack){
				if(put.rank() == 1) {
					for (Card c: arr) {
						if(c.rank() == 0 || c.rank() == 1 || (c.suit()==put.suit() && c.rank()==2))
						{	
							super.putCard(c);
							att = true;  
							break;
						}
					}
					
					if(att = false) {
						dealer.dealTo(this, 3); //deck
					}
				}
				else if(put.rank() == 2)
				{
					for(Card c: arr) {
						if(c.suit() =="JOKER" || c.rank()==2 || (c.suit()==put.suit() && c.rank()==1))
						{
							super.putCard(c) ;
							att =true;
							break;
						}
					}
					if(att = false) {
						dealer.dealTo(this, 2); // received
					}
				}
				else if(put.rank() == 0) 
				{
					for(Card c: arr) {
						if(c.rank() == 0 )
						{
							super.putCard(c); 
							att =true;
							break;
						}
					}
					if(att = false) 
					{
						dealer.dealTo(this, 5);
					}
				}
			}
			else { 
				idx = (int)(Math.random()* arr.length);
				putCard(arr[idx]);
			}
		}
		else {
			dealer.dealTo(this, 1);
		}
	}	
}