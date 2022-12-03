import javax.swing.*;
public class OneCardGame implements OneCardGameInterface{
	
	Dealer dealer;
	HumanPlayer hand_player;
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	CardPlayer[] turn ;
	int max_cards = 16;
	int nowTurn = 0;
	int cases; // 0 := 게임중 // 1 := player 승리 // 2 := player패배
	int stackedAttack; // 공격카드 겹쳐진 경우 얼마나 먹어야하는지.
	
	int direction; //턴의 순서 
	int total_players;

	
	String name;
	Card topper;//맨 마지막으로 낸 카드
	Card throwedCard;// putCard로 받는 카드
	boolean attack;
	
////////////////!!!!!!!!!!!!!!! 게임 끝나면 덱과 손패 정리해야함.
//////////!!!!!!!!! 7 어떻게 할까? =>> possible_cards에서 맡자.

	
	
	//complete
	public OneCardGame(Dealer d) {
		dealer = d;
		name = JOptionPane.showInputDialog("What is your name?");
		hand_player = new HumanPlayer(d,max_cards, name);
		hand_com1 = new ComputerPlayer(d,max_cards, 1);
		hand_com2 = new ComputerPlayer(d,max_cards, 2);
		hand_com3 = new ComputerPlayer(d,max_cards, 3);
	}

	//complete
	public void StartGame() {
		total_players = 4;
		//추가 turn new Card
		turn = new CardPlayer[total_players];
	//	turn[] = {hand_player,hand_com1, hand_com2,hand_com3};
		turn[0] = hand_player;
		turn[1] = hand_com1;
		turn[2] = hand_com2;
		turn[3] = hand_com3;
		
		//랜덤으로 첫 순서 뽑기
		//nowTurn = (int)(Math.random() * (total_players-1));
		direction=1;
		startCard();
		cases = 0;
		stackedAttack = 0;
		attack = false;
		
		
		while(true) {
			topper = dealer.topCard();
			if(isPlayerTurn()) {
				PlayerPlay();
			}
			else {
				ComPlay(turn[nowTurn]);
			}
			
			//nowTurn += 1;
			nowTurn = (nowTurn+direction) % (total_players);
			
			if (total_players == 1) {cases = 1;}
			if (cases == 2) {
				JOptionPane.showInternalMessageDialog(null, "졌습니다. 현재 보유한 칩은 " +
						hand_player.chips() + "개 입니다.");
				nowTurn = (int)(Math.random() * 4 + 1 - 1) + 1;
				break;
			}
			else if (cases == 1) {
				JOptionPane.showInternalMessageDialog(null, "이겼습니다. 현재 보유한 칩은 " +
						hand_player.chips() + "개 입니다.");
				nowTurn = 0;
				break;
			}
		}
		
	}
	
	
	//complete
	public void startCard() {
		for (int i = 0; i < turn.length; i++) {
			dealer.dealTo(turn[i], 7);
			dealer.dealToPut();
		}
	}
	
	//progress
	public void PlayerPlay() {
		if(!attack){
			if (hand_player.possible(topper)) {
				while (true) {
					WaitForPut();
					if (possibleTOput()) {
						hand_player.putCard(throwedCard);
						dealer.putCard(throwedCard);
						throwedCard = null;
						break;
					}
					else
					throwedCard = null;
						continue;
				}
				int nowHand = hand_player.cardCount();
				if (nowHand == 1) {
					System.out.println("OneCard!");
				}
				else if (nowHand == 0) {
					cases = 1;
					hand_player.win();
				}
			}
			else{
				if(hand_player.cardCount() <= 15) {
					dealer.dealTo(hand_player, 1);
				}
				else{
					cases = 2;
					hand_player.loose();
				}
			}
		}
		else {
			if (hand_player.possible_attack(topper)) {
				while (true) {
					WaitForPut();
					if (possibleTOattack()) {
						hand_player.putCard(throwedCard);
						dealer.putCard(throwedCard);
						throwedCard = null;
						break;
					}
					else
					throwedCard = null;
						continue;
				}
				int nowHand = hand_player.cardCount();
				if (nowHand == 1) {
					System.out.println("OneCard!");
				}
				else if (nowHand == 0) {
					cases = 1;
					hand_player.win();
				}
			}
			else {
				if (hand_player.cardCount() + stackedAttack <= 16) {
					dealer.dealTo(hand_player, stackedAttack);
					stackedAttack = 0;
					attack = false;
				}
				else {
					cases = 2;
					hand_player.loose();
				}
			}
		}
	}

	
	//complete
	public void WaitForPut() {
		while(throwedCard == null)
			continue;
	}

	//progress
	public boolean possibleTOput(){
		if (throwedCard.suit() == topper.suit()){return true;}
		else if (throwedCard.rank() == topper.rank()){return true;}
		else if (topper.rank() == 0
			|| topper.rank() == 7) {return true;}
		else {return false;}
	}
	public boolean possibleTOattack(){
		if (topper.rank() == 2 
			&&(throwedCard.rank() == 0
			|| throwedCard.rank() == 1
			|| throwedCard.rank() == 2)){return true;}
		else if (topper.rank() == 1
			&&(throwedCard.rank() == 0
			|| throwedCard.rank() == 1)){return true;}
		else if (topper.rank() == 0
			&& throwedCard.rank() == 0){return true;}
		else {return false;}
	}
	
	
	//지금까지 얼마나 먹어야하는지 알 방법이 필요한데... => putcard 쓰기.
	/////////////////////////////////////////밑에거 보니까 Card[]만들때 +1을 해주던, 그보다 -1이 최대여야함.
	/////////////////////////////////////////////아니고 hand[]랑 먹어야 할 카드의 개수가 16이 넘으면 죽음.
		
	
	//cp = turn[now_turn]
	public void ComPlay(CardPlayer cp) {
		boolean die = false;
		
		//카드를 먹는 순간 die를 결정하기 때문에 그 외의 순간에서 카드의 수를 고려하지 않는다. 
		if (!attack){
			if (cp.possible(topper)) {//낼 수 있는 카드가 존재하면 그 중에서 (규칙에 맞게) 카드를 낸다 
				//카드를 냈음 
				((ComputerPlayer)cp).takeCard(topper, attack);
				//takeCard가,즉, 내서 놓인덱의 제일 위에 있는 카드가 attack이라면 attack = true;
				Card take = dealer.topCard();
			
				//내가 놓은 카드가 attack 카드인 경우 
				if (take.rank() == 0 || take.rank() == 1 || take.rank() ==2) {
					attack = true;  
					if (take.rank() == 0)  
						stackedAttack+=5;
					else if (take.rank() == 1)
						stackedAttack+= 3;
					else if (take.rank() == 2)
						stackedAttack +=2;
				}

				//내가 놓 카드가 특 카드인 경우  j=11,q=12,k=13
				if (take.rank() == 11)
					//n = ( ( n+1) + direction) % (total_players);
					nowTurn +=1;
				else if (take.rank() == 12) {
					direction *= -1; //directiom *= -1어때? -1이면 다시 1이 되어야 하니까?
				}
				else if (take.rank() == 13) {
					ComPlay(cp); 
				}	
				
				/***/
				if(cp.cardCount() == 1)  
					System.out.println("OneCard!!!");
				
			}
			else {//낼 수 있는 카드가 없다면 카드를 먹는다
				//놓인 카드가 공격카드가 아닌 경우. 즉 1장만을 먹어야하는 경우 
				if (cp.cardCount() < 16) {dealer.dealTo(cp,1);}
				else {
					dealer.dealTo(cp,1); 
					die = true;
					total_players -=1;
				}
			}
		}
		else{
			if (cp.possible_attack(topper)){
				((ComputerPlayer)cp).takeCard(topper, attack);
				//takeCard가,즉, 내서 놓인덱의 제일 위에 있는 카드가 attack이라면 attack = true;
				Card take = dealer.topCard();
			
				//내가 놓은 카드인 attack 카드
				if (take.rank() == 0 || take.rank() == 1 || take.rank() ==2) {
					attack = true; 
					if (take.rank() == 0)  
						stackedAttack+=5;
					else if (take.rank() == 1)
						stackedAttack+= 3;
					else if (take.rank() == 2)
						stackedAttack +=2;
				}
			}
			else {//카드를 먹어야하는 상황일 때 놓여 카드가 공격카드인 경우 
				if (cp.cardCount() + stackedAttack <= 16) {
					dealer.dealTo(cp, stackedAttack);
					stackedAttack = 0; //먹었으므로 누적된 카드는 0으로 초기화 
					attack = false;    //attack이 종료되었으므로 0으로 초기화 
				}
				else{//먹은 순간 16장을 초과한 경우 
					die = true;
					stackedAttack = 0;
					attack = false;
					total_players-=1;
					}
			}
		}
	
		// 추가:죽은 플레이어의 패를 딜러에게 모두 넘겨줌 
		//die 한 플레이어의 칸을 비우고 당겨준
		if (die) {
			turn[nowTurn] = null;  // { 1,2,null,4} --> {1,2,4,4}  
			for (int nt = nowTurn; nt < total_players ; nt++) {
				turn[nt] = turn[nt + 1];	
			}
		}
		
		if (cp.cardCount()== 0) {//승리!!!
			hand_player.loose();
			cases = 2;
		}
	}
	

	//complete
	public boolean isPlayerTurn() {
		if (turn[nowTurn] == hand_player)
			return true;
		else
			return false;
	}
	
	//complete
	@Override // player가 낸 카드를 알려줌
	public void putCard(String suit, int rank) {
		Card c = new Card(suit, rank);
		if (rank < 3) {
			if (rank == 0) {
				if (suit == "blackjoker"){stackedAttack += 5; attack = true;}
				else {stackedAttack += 10; attack = true;} //(suit == "colorjoker") 
			}
			else if(rank == 1){stackedAttack += 3; attack = true;}
			else if(rank == 2){stackedAttack += 2; attack = true;}
		}
		else if (rank > 10) {
			if (rank == 11){nowTurn +=1;}
			else if (rank == 12) {direction *= -1;}
			else if (rank == 13) {PlayerPlay();}
		}
		throwedCard = c;
	}
	
	//complete
	@Override
	public Card topCard() {
		Card top = dealer.topCard();
		return top;
	}
	
	//complete
	public Card[] playerCards() {
		return hand_player.hand(); 
	}
	
	//complete
	public int[] numberOfCards() {
		////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 얘 hand_com들이 죽으면 어떻게 해? 여기 좀 생각해봐야할듯
		int handArr[] = new int[3];
		handArr[0] = hand_com1.hand().length;
		handArr[1] = hand_com2.hand().length;
		handArr[2] = hand_com3.hand().length;
		return handArr;
	}
	
}