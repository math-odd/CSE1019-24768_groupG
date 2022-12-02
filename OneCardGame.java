import javax.swing.*;
public class OneCardGame implements OneCardGameInterface{
	Dealer dealer;
	HumanPlayer hand_player;
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	CardPlayer[] turn;
	int max_cards = 16;
	int nowTurn = 0;
	int cases; // 0 := 게임중 // 1 := player 승리 // 2 := player패배
	int stackedAttack; // 공격카드 겹쳐진 경우 얼마나 먹어야하는지.
	String name;
	Card topper;//맨 마지막으로 낸 카드
	Card throwedCard;// putCard로 받는 카드
	boolean attack;
/////////////////////!!!!!!!!!!!!!!!           7하고 J, Q, K 어떻게 하지?
	public OneCardGame(Dealer d) {
		dealer = d;
		name = JOptionPane.showInputDialog("What is your name?");
		hand_player = new HumanPlayer(max_cards, name);
		hand_com1 = new ComputerPlayer(max_cards, 1);
		hand_com2 = new ComputerPlayer(max_cards, 2);
		hand_com3 = new ComputerPlayer(max_cards, 3);
		turn[0] = hand_player;
		turn[1] = hand_com1;
		turn[2] = hand_com2;
		turn[3] = hand_com3;
	}

	public void StartGame() {
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
			nowTurn += 1;
			int PlayerNumber = turn.length;
			if (nowTurn >= PlayerNumber) {nowTurn -= PlayerNumber;}
			if (turn.length == 1) {cases = 1;}
			if (cases == 2) {
				JOptionPane.showInternalMessageDialog(null, "졌습니다. 현재 보유한 칩은 " +
						hand_player.chips() + "개 입니다.");
				break;
			}
			else if (cases == 1) {
				JOptionPane.showInternalMessageDialog(null, "이겼습니다. 현재 보유한 칩은 " +
						hand_player.chips() + "개 입니다.");
				break;
			}
		}
		
	}
	
	public void startCard() {
		for (int i = 0; i < turn.length; i++) {
			dealer.dealTo(turn[i], 7);
		dealer.dealToPut();
		}
	}
	
	public void PlayerPlay() {
		if (hand_player.possible(topper)) {
			while (true) {
				WaitForPut();
				if (possibleTOput()) {
					dealer.putCard(throwedCard);
					throwedCard = null;
					break;
				}
				else
					continue;
			}					
		}
		else {
			int playerhand = hand_player.hand().length;
			if (!attack)
				if(playerhand <= 15) {
					dealer.dealWantTo(hand_player);
				}
				else{
					cases = 2;
					hand_player.loose();
				}
			else {		
				if (playerhand + stackedAttack <= 16) {
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
		if (hand_player.hand().length == 0) {
			cases = 1;
			hand_player.win();
		}
	}

	public void WaitForPut() {
		while(throwedCard == null)
			continue;
	}

	public boolean possibleTOput(){
		if (throwedCard.suit() == topper.suit()){return true;}
		else if (throwedCard.rank() == topper.rank()){return true;}
		else if (throwedCard.suit() == "blackjoker" || throwedCard.suit() == "colorjoker") {return true;}
		else {return false;}
	}
	//지금까지 얼마나 먹어야하는지 알 방법이 필요한데... => putcard 쓰기.
	/////////////////////////////////////////밑에거 보니까 Card[]만들때 +1을 해주던, 그보다 -1이 최대여야함.
	/////////////////////////////////////////////아니고 hand[]랑 먹어야 할 카드의 개수가 16이 넘으면 죽음.
	public void ComPlay(CardPlayer cp) {
		boolean die = false;
		//근데 왜 죄다 Card put을 받는거지? => 지금 나와있는 카드랑 비교하려고
		if (cp.possible(topper)) {
			//com은 랜덤으로 낸다는데 그런 함수는 하나도 없네. 일단 랜덤으로 낸다 치자. => 찾음
			Card[] arr = cp.possible_cards(topper);
			int idx = (int)(Math.random()* arr.length);
			cp.receivedCard(arr[idx]);
		}
		else {
			int handLangth = cp.hand().length;
			if(!attack){
				if (handLangth <= 15){dealer.dealWantTo(cp);}
				else{die = true;}
			}
			else {
				if (handLangth + stackedAttack <= 16) {
				dealer.dealTo(cp, stackedAttack);
				stackedAttack = 0;attack = false;
				}
				else{die = true;stackedAttack = 0;attack = false;}
			}
		}
		if (die) {
			turn[nowTurn] = null;
			for (int nt = nowTurn; nt < turn.length - 1; nt++) {
				turn[nt] = turn[nt + 1];
				nowTurn -= 1;
			}
		}
		if (cp.hand().length == 0) {
			hand_player.loose();
			cases = 2;
		}
	}
	


	@Override
	public boolean isPlayerTurn() {
		if (nowTurn == 0)
			return true;
		else
			return false;
	}
	@Override // player가 낸 카드를 알려줌
	public void putCard(String suit, int rank) {
		Card c = new Card(suit, rank);
		if (rank == 0) {
			if (suit == "blackjoker"){stackedAttack += 5; attack = true;}
			else {stackedAttack += 10; attack = true;} //(suit == "colorjoker") 
		}
		else if(rank == 1){stackedAttack += 3; attack = true;}
		else if(rank == 2){stackedAttack += 2; attack = true;}
		throwedCard = c;
	}
	@Override
	public Card topCard() {
		Card top = dealer.topCard();
		return top;
	}
	
	public Card[] playerCards() {
		return hand_player.hand(); 
	}
	
	public int[] numberOfCards() {
		////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 얘 hand_com들이 죽으면 어떻게 해? 여기 좀 생각해봐야할듯
		int handArr[] = new int[3];
		handArr[0] = hand_com1.hand().length;
		handArr[1] = hand_com2.hand().length;
		handArr[2] = hand_com3.hand().length;
		return handArr;
	}
	
}
