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
	String name;

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
	
	
////////////////////////////////////////player가 게임을 이겼는지 졌는지 알만한 무언가 하나 더 필요함
	public void StartGame() {
		startCard();
		while(true) {
			if(isPlayerTurn()) {
				PlayerPlay();
			}
			else {
				ComPlay(turn[nowTurn]);
			}
			nowTurn += 1;
			if (nowTurn > 3) {nowTurn -= 4;}
			if (진 경우) {
				JOptionPane.showInternalMessageDialog(null, "졌습니다. 현재 보유한 칩은 " +
						hand_player.chips() + "개 입니다.");
				break;
			}
			else if (이긴 경우) {
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
		if (hand_player.possible(null)) {
			boolean beforput = true;
			while (beforput) {
				//카드 정한 것 받기
				if (hand_player.possible_cards(null)) {// 그 카드가 가능한지 보여줘야하는데...
					dealer.putCard(null);
					beforput = false;
				}
				else
					continue;
			}					
		}
		else {
			if (공격아님)
				dealer.dealWantTo(hand_player);
			else
				dealer.dealTo(hand_player, 먹어야 하는 만큼);
		
		int playerhand = hand_player.hand().length;
		if (length > 16) {hand_player.loose;}
		else if (length == 0) {hand_player.win;}
		}
	}
	
	//지금까지 얼마나 먹어야하는지 알 방법이 필요한데...
	/////////////////////////////////////////밑에거 보니까 Card[]만들때 +1을 해주던, 그보다 -1이 최대여야함.
	public void ComPlay(CardPlayer cp) {
		Card[] thishand = cp.hand();
		//근데 왜 죄다 Card put을 받는거지?
		if (cp.possible(null)) {
			//com은 랜덤으로 낸다는데 그런 함수는 하나도 없네. 일단 랜덤으로 낸다 치자.
			if (cp.hand().length == 0) {hand_player.loose();}
		}
		else if(공격아님){dealer.dealWantTo(cp);}
		else {dealer.dealTo(cp, 먹어야 하는 카드개수);}
		if (cp.cardCount() >= max_cards) {
			turn[nowTurn] = null;
			for (int nt = nowTurn; nt < turn.length - 1; nt++) {
				turn[nt] = turn[nt + 1];
			}
		}
	}
	
	@Override
	public boolean isPlayerTurn() {
		if (nowTurn == 0)
			return true;
		else
			return false;
	}
	@Override
	public void putCard(String suit, int rank) {
		Card c = new Card(suit, rank);
		dealer.putCard(c);
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
		int handArr[] = new int[3];
		handArr[0] = hand_com1.hand().length;
		handArr[1] = hand_com2.hand().length;
		handArr[2] = hand_com3.hand().length;
		return handArr;
	}
	
}
