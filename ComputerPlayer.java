
public class ComputerPlayer extends CardPlayer{
		public int computer_count;
	public ComputerPlayer(int max_cards, int id) {
		super(max_cards);
		computer_count = id;
	}

	/* 
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!얘 에이스 왜 계속 넣는거냐? 이해가 안되네.
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!그리고 닌 dealer한테 접근권한도 없으면서 왜!!! 카드를 뽑아? 
	public void takeCard(Card put) {
		Card[] arr = super.possible_cards(put);
		boolean att = false;
		if(arr != null) {
			if(put.rank() == 1) {
				for(int i = 0; i < hand().length; i++) {
					Card c = hand()[i];
					if(c.suit() =="JOKER" || c.suit() == "ACE" || (c.suit()==put.suit() && c.rank()==2))
					{
						super.putCard(c); ////////////////////카드내면 정렬을 안해?
						for(int j = i; j < hand().length-1; j++){
							hand()[j] = hand()[j+1];
						}
						att =true;
						break;
					}
				}
				if(att = false) {
					receiveCard(null, 3);//////////////이거봐 Card[] 자신도 안쓰면서 CardPlayer는 이렇게 써놨네.
				}
			}
			else if(put.rank() == 2)
			{
				for(int i = 0; i < hand().length; i++) {
					Card c = hand()[i];
					if(c.suit() =="JOKER" || c.suit() == "ACE" || (c.suit()==put.suit() && c.rank()==2))
					{
						super.putCard(c); ////////////////////카드내면 정렬을 안해?
						for(int j = i; j < hand().length-1; j++){
							hand()[j] = hand()[j+1];
						}
						att =true;
						break;
					}
				}
				if(att = false) {
					receiveCard(null, 2);
				}
			}
			else if(put.suit() == "JOKER") 
			{
				for(Card c: arr) {
					if(c.suit() =="JOKER")
					{
						super.putCard(c); ////////////////////카드내면 정렬을 안해?
						att =true;
						break;
					}
				}
				if(att = false) 
				{
					receiveCard(null, 5); /////////////////////공격 더 받았으면 먹을 카드가 늘어나지 않나?
				}
			}
		}
		else { 
	/////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!////////////////////////여기만 좀 뜯어서 쓰고 다시 써야겠다.
			idx = (int)(Math.random()* arr.length);
			putCard(arr[idx]);
		}
	}
	*/
}
