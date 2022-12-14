import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class OneCardGame implements OneCardGameInterface {

	private Dealer dealer;
	private HumanPlayer hand_player;
	private ComputerPlayer hand_com1;
	private ComputerPlayer hand_com2;
	private ComputerPlayer hand_com3;

	private int total_players = 4;
	private CardPlayer[] turn = new CardPlayer[total_players]; // {Humanplayer, complayer, complayer, cplmaer}
	private int max_cards = 16;
	private int nowTurn = 0; // 플레이어 턴부터 시작 turn[nowTurn] nowTurn = ((nowTurn + direction) % total_players)
	private int cases; // 0 := 게임중 // 1 := player 승리 // 2 := player패배
	private int stackedAttack = 0; // 누적된 attack cards. 공격카드 겹쳐진 경우 얼마나 먹어야하는지.
	private int total_turns = 0; // 몇번째 턴인지 확인용.
	private int direction = 1; // 턴의 순서
	private String name = "";
	private Card topper;// 맨 마지막으로 낸 카드
	List<String> list = new ArrayList<String>();
	List<String> csv = new ArrayList<String>();
	Path output = Paths.get("game_log.csv");
	Path out = Paths.get("game_log.txt");
	private boolean attack = false; // 공격 카드가 활성화

	public OneCardGame(Dealer d) {
		dealer = d;
		// name = JOptionPane.showInputDialog("What is your name?");
		// 모든 플레이어 활성화
		hand_player = new HumanPlayer(max_cards, name);
		hand_com1 = new ComputerPlayer(max_cards, 1);
		hand_com2 = new ComputerPlayer(max_cards, 2);
		hand_com3 = new ComputerPlayer(max_cards, 3);

		turn[0] = hand_player;
		turn[1] = hand_com1;
		turn[2] = hand_com2;
		turn[3] = hand_com3;

		StartGame();
	}

	public void StartGame() {
		for (int i = 0; i < total_players; i++) {
			dealer.dealTo(turn[i], 7);
		}
		dealer.dealToPut();
		cases = 0;
		topper = dealer.topCard();
		System.out.println("game ready to start"); // debug line
	}

	// Computer's Turn
	public void ComPlay(ComputerPlayer cp) {
		boolean die = false;
		Card take = cp.takeCard(topper, attack);

		if (!attack) { // attack = false 인 상태라면, 즉 앞사람의 카드가 공격카드가 아니거나 앞사람이 stackAttacked를 이미 먹음
			if (take != null) {// 카드를 낼 수 있는 경우 put == 0
				dealer.putCard(take);
				topper = take;
				System.out.print("[" + total_turns + "] Computer" + cp.name() + " played " + topper.suit() + " "
						+ topper.rank() + ". ");
				System.out.println("Computer" + cp.name() + " has " + cp.cardCount() + " cards.");
				list.add("[" + total_turns + "] Computer" + cp.name() + " played " + topper.suit() + " " + topper.rank()
						+ ". ");
				csv.add(total_turns + "," + cp.name() + "," + 0 + "," + 0 + "," + cp.cardCount());
				//total_turns++;

				// 내가 놓은 카드가 attack 카드인 경우
				int takeRank = take.rank();
				switch (takeRank) {
				case 0:
					stackedAttack += 5;
					attack = true;
					break;
				case 1:
					stackedAttack += 3;
					attack = true;
					break;
				case 2:
					stackedAttack += 2;
					attack = true;
					break;
				case 11:
					nowTurn += direction;
					break;
				case 12:
					direction *= -1;
					break;
				case 13:
					nowTurn -= direction;
					break;
				default:
					break;
				}

				// 50% 확률로 컴퓨터가 원카드를 외치고 이김
				Random ran = new Random();

				if (cp.cardCount() == 1) {
					int random_number = ran.nextInt(2);
					if (random_number == 0)
						JOptionPane.showMessageDialog(null, "Computer" + cp.name() + ": OneCard!", null,
								JOptionPane.PLAIN_MESSAGE);
					else {
						JOptionPane.showMessageDialog(null, "Computer" + cp.name() + " fails and takes 1 card.", null,
								JOptionPane.PLAIN_MESSAGE); // 실패하면 카드 뽑아야지!
						dealer.dealTo(cp, 1);
						System.out.println("[" + total_turns + "] Computer" + cp.name() + " fails and takes 1 card.");

						csv.add(total_turns + "," + cp.name() + "," + 1 + "," + 1 + "," + cp.cardCount());//????

					}

				}
			} else {// take == null, 카드를 먹는다 take == 1
					// attack == false
				if (cp.cardCount() < 16) {
					dealer.dealTo(cp, 1);
					System.out.print("Computer" + cp.name() + " takes " + 1 + " card. ");
					System.out.println("Computer" + cp.name() + " has " + cp.cardCount() + " cards.");
					list.add("[" + total_turns + "] Computer" + cp.name() + " played " + topper.suit() + " "
							+ topper.rank() + ". ");
					csv.add(total_turns + "," + cp.name() + "," + 1 + "," + 1 + "," + cp.cardCount());
				} else {
					// dealer.dealTo(cp, 1);
					die = true;
					System.out.println("Computer" + cp.name() + " dies.");
					list.add("[" + total_turns + "] Computer" + cp.name() + " dies.");
					csv.add(total_turns + "," + cp.name() + "," + 1 + "," + 1 + "," + cp.cardCount() + 1);
					total_players -= 1;
				}
			}
		} else {// attack == true , 즉 바로 앞사람이 공격카드를 낸 상태
			if (take != null) {
				dealer.putCard(take);
				topper = take;
				System.out.print("Computer" + cp.name() + " played " + topper.suit() + " " + topper.rank() + ". ");
				System.out.println("Computer" + cp.name() + " has " + cp.cardCount() + " cards.");
				list.add("[" + total_turns + "] Computer" + cp.name() + " played " + topper.suit() + " " + topper.rank()
						+ ". ");
				csv.add(total_turns + "," + cp.name() + "," + 0 + "," + 0 + "," + cp.cardCount());

				if (take.rank() == 0 || take.rank() == 1 || take.rank() == 2) {
					attack = true;
					if (take.rank() == 0)
						stackedAttack += 5;
					else if (take.rank() == 1)
						stackedAttack += 3;
					else if (take.rank() == 2)
						stackedAttack += 2;
				}
			} else {// attack 상황에서 카드를 먹어야하는 경우
				if (cp.cardCount() + stackedAttack <= 16) {
					dealer.dealTo(cp, stackedAttack);
					System.out.print("Computer" + cp.name() + " takes " + stackedAttack + " cards. ");
					System.out.println("Computer" + cp.name() + " has " + cp.cardCount() + " cards.");
					list.add("[" + total_turns + "] Computer" + cp.name() + " takes " + stackedAttack + ". ");
					csv.add(total_turns + "," + cp.name() + "," + 1 + "," + stackedAttack + "," + cp.cardCount());

					stackedAttack = 0; // 먹었으므로 누적된 카드는 0으로 초기화
					attack = false; // attack이 종료되었으므로 0으로 초기화

				} else {// 먹은 순간 16장을 초과한 경우
					die = true;
					System.out.println("Computer" + cp.name() + " dies. ---> attack: " + attack);
					list.add("[" + total_turns + "] Computer" + cp.name() + " dies. ---> attack: " + attack);
					csv.add(total_turns + "," + cp.name() + "," + 1 + "," + stackedAttack + ","
							+ (cp.cardCount() + stackedAttack));

					stackedAttack = 0;
					attack = false;
					total_players -= 1;
				}
			}
		}

		if (die) {
			turn[nowTurn] = null; // { 1,2,null,4} --> {1,2,4,4}   /nt =2 
			for (int nt = nowTurn; nt < total_players; nt++) { // nt < 3
				turn[nt] = turn[nt + 1];
			}
		}

		if (cp.cardCount() == 0) {// 승리!!!
			hand_player.lose();
			cases = 2;
		}
	}
	
	public List<String> gameLog() {return list;}

	public boolean isPlayerTurn() {
		return (turn[nowTurn] == hand_player);
	}

	public boolean putCard(String suit, int rank) {
		// 플레이어 차례이고 낼 수 있는 카드가 있다면 (null 방지문
		if (isPlayerTurn()) { // 플레이어의 턴인 경우 attack이면 공격카드만,!attack이라면 공격+일반카드
			if (!attack) { // 클릭을 받았을 때 앞의 카드가 attack이 아닌 경우(일반카드)
				if (hand_player.possible(topper)) {// 낼 수 있는 카드가 존재
					// 가능한 카드를 Card array p로 받는다
					Card[] p = hand_player.possible_cards(topper);
					// 낼 수 있는 카드 중에 만약 누른 카드와 일치하는 카드가 있다면
					for (int i = 0; i < p.length; i++) {
						if (p[i].rank() == rank && p[i].suit().equals(suit)) {
							System.out.println("rank: " + rank + " suit: " + suit + " was played!");
							list.add("["+total_turns+"]"+" Player plays." + " rank: " + rank + " suit: " + suit );
							switch (rank) {
							case 0:
								stackedAttack += 5;
								attack = true;
								break; // black||color both 5
							case 1:
								stackedAttack += 3;
								attack = true;
								break; // ace
							case 2:
								stackedAttack += 2;
								attack = true;
								break; // rank 2
							case 11:
								nowTurn += 1;
								break; // J skips turn
							case 12:
								direction *= -1;
								break; // Q changes direction
							// case 13: PlayerPlay(); break; // 재귀 K one more turn
							case 13:
								nowTurn -= direction;
								break; // 한칸 이전으로 돌려야 +해도 내 차례다.
							default:
								break;
							}

							// 여기 hand_player가 카드를 put하고 dealer가 받는다
							hand_player.putCard(p[i]);
							dealer.putCard(p[i]);
							total_turns++;
							topper = p[i];

							if (attack)
								System.out.println("attack = " + attack + " stackedattack = " + stackedAttack);

							if (hand_player.cardCount() < 1) {
								cases = 1;
								return true;
							}

							else if (hand_player.cardCount() == 1) {
								Object[] options1 = { "ONECARD!" };
								int onecard = 0;
								final JOptionPane pane = new JOptionPane();
								Thread t1 = new Thread(new Runnable() {
									public void run() {
										try {
											Thread.sleep(3000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										pane.getRootFrame().dispose();
									}
								});
								t1.start();
								onecard = pane.showOptionDialog(null, "Say OneCard!", "Click the button in 5 seconds",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1,
										null);
								// 시간 내에 눌렀다
								if (onecard != 0) {
									dealer.dealTo(hand_player, 1);
								}
							}

							// player가 카드를 성공적으로 놨다. 턴을 끝낸다.
							nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
							// computerPlayer1,2,3이 모두 시행된다.
							computerProcess();
							return true; // 낼 수 있는 카드다
						}
					} // for문 끝
				}
			} else {// attack인 상태
				if (hand_player.possible_attack(topper)) { // possible_attack 이 있는 경우
					Card[] p = hand_player.possible_attack_cards(topper);
					// 낼 수 있는 카드 중에 만약 누른 카드와 일치하는 카드가 있다면
					for (int i = 0; i < p.length; i++) {
						if (p[i].rank() == rank && p[i].suit().equals(suit)) {
							System.out.println("rank: " + rank + " suit: " + suit + " was played!");
							list.add("["+total_turns+"]"+" Player plays."+ " rank: " + rank + " suit: " + suit );
							switch (rank) {
							case 0: // joker
								stackedAttack += 5;
								attack = true;
								break; // black||color both 5
							case 1: // ace
								stackedAttack += 3;
								attack = true;
								break; // ace
							case 2:
								stackedAttack += 2;
								attack = true;
								break; // rank 2
							default:
								break;
							}

							hand_player.putCard(p[i]);
							dealer.putCard(p[i]);
							total_turns++; // 턴 바뀔때마다 총 턴 증가
							topper = p[i]; // topper 매번 재설정 해야 되나요?
							
							if (attack)
								System.out.println("attack = " + attack + " stackedattack = " + stackedAttack);

							// 여기서 (카드를 낸 상태) 만약 player의 카드 수가 0이면 플레이어 승리다
							// 1이면 원카드 (아직 구현하지 않음)
							if (hand_player.cardCount() < 1)
								cases = 1;

							// player가 카드를 성공적으로 놨다. 턴을 끝낸다.
							nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
							// 플레이어 차례일 때까지 컴퓨터가 플레이한다.
							// System.out.println("NOWTURN=" + turn[nowTurn]);
							computerProcess();
							return true;
						}
					}
				}
			}
		}
// 플레이어 차례가 아니거나 낼 수 없는 카드인 경우  카드 어디서 먹어? 
		System.out.println("couldn't play suit: " + dealer.topCard().suit() + " rank: " + dealer.topCard().rank());
		return false;
	}

	// 컴퓨터들을 플레이시켜준 후 attack이 true && 플레이어가 카드를 낼 수 없다면 -> 플레이어에게 카드를 먹이고 attack이
	// false 이거나 플레이어가 카드를 낼 수 있을 때까지
	public void computerProcess() {
		while (!isPlayerTurn() && cases == 0) {
			// ComputerPlayer cp = (ComputerPlayer) turn[nowTurn];
			ComPlay((ComputerPlayer) turn[nowTurn]);
			if (attack)
				System.out.println("attack = " + attack + " stackedattack = " + stackedAttack);

			// list.add("Computer"+cp.name()+" Put :" + topper.suit()+ " " + topper.rank());
			// list.add("Computer" + cp.name()+ "Card count :" + cp.cardCount());

			//if (nowTurn == null)
			//	nowTurn++; 
			
			//total_player =3  {1,null,3,4}  --> {1,3,4,4} nowTurn--  nowTurn=0  nowTunr=(1%3) = 1 
			nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
			total_turns++;
		}

		// 플레이어가 낼 수 있을 때에만 putcard로 다시 돌아가는데, endplayer를 여기서 넣었으면 좋겠다
		System.out.println("Player turn");
		
		try {
			Files.write(out, list);
			System.out.println(output.toFile().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Files.write(output, csv);
			System.out.println(output.toFile().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
/////////////////////////////////////////////////////////////////		
	}

	// Player decides to take a card ( despite possible )
	public void endPlayerTurn() {
		if (isPlayerTurn()) { // 내가 카드를 내지 않겠다고 했을 때 카드를 한장 먹고 내 턴이 돌아올때까지 코드를 돌린다
			list.add("["+total_turns+"]"+" Player takes.");
			total_turns++;
			if (attack && !hand_player.possible_attack(topper)) {
				// 방어 카드가 없어서 먹어야하는 경우
				if (hand_player.cardCount() + stackedAttack < 16) {
					System.out.println("Player couldn't defend and takes " + stackedAttack + " cards.");
					dealer.dealTo(hand_player, stackedAttack);
					System.out.println("Player card count: " + hand_player.cardCount());
					stackedAttack = 0;
					attack = false;
					nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
					// 나는 카드를 내지 못하고 먹었으므로 다시 컴퓨터의 턴
					computerProcess();
				}
				// 먹은 순간 16장을 초과한 경우
				else
					cases = 2; // 컴퓨터 승리
			}
			// (없어서) 카드를 먹는데 공격이 쌓이지 않은 경우
			else if (!attack && !hand_player.possible(topper)) {
				if (hand_player.cardCount() + 1 <= 16) {
					System.out.println("Player had no possible cards and takes a card.");
					System.out.println("Player card count: " + hand_player.cardCount());
					dealer.dealTo(hand_player, 1);
					nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
					computerProcess();
				} else
					cases = 2;
			}
			// 있지만 카드를 내기 싫어서 먹기 && problems:: 앞에 공격은 있는데 내가 카드 내기싫어서 먹는경우는 고려 안함.
			else if (hand_player.possible(topper))
				if (hand_player.cardCount() < 16) {
					dealer.dealTo(hand_player, 1); // 카드 받음
					nowTurn = ((((nowTurn + direction) % total_players) + total_players) % total_players);
					computerProcess(); // 내 턴이 돌아올때까지 코드를 돌림
				} else {
					cases = 2;
					System.out.println("///// PLAYER LOSES /////");
				}
			
		}
	}

	public Card topCard() {
		Card top = dealer.topCard();
		return top;
	}

	public Card[] playerCards() {
		return hand_player.hand();
	}

	public int playerCardCount() {
		return hand_player.cardCount();
	}

	public int[] numberOfComCards() {
		int handArr[] = new int[3];
		handArr[0] = hand_com1.cardCount();
		handArr[1] = hand_com2.cardCount();
		handArr[2] = hand_com3.cardCount();
		return handArr;
	}

	public static void main(String[] args) {
		OneCardGame ocg = new OneCardGame(new Dealer());
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("game state: " + ocg.state());
			System.out.println("PUT CARD SUIT: " + ocg.topCard().suit() + " RANK: " + ocg.topCard().rank());
			System.out.println("MY CARDS(" + ocg.playerCardCount() + "): ");

			Card[] arr = ocg.playerCards();
			for (int i = 0; i < ocg.playerCardCount(); i++) {
				System.out.println(arr[i].suit() + " " + arr[i].rank());
			}

			System.out.println("SUIT: ");
			String suit = sc.nextLine();
			while (suit.isBlank())
				suit = sc.nextLine();

			System.out.println("RANK: ");
			String rank = sc.nextLine();
			while (rank.isBlank())
				rank = sc.nextLine();

			String end = "";
			if (!ocg.putCard(suit, Integer.parseInt(rank))) {
				System.out.println("end turn? 0 for no, 1 for yes: ");
				end = sc.nextLine();
				while (end.isBlank() || end.isEmpty() || end.equals(""))
					end = sc.nextLine();
				if (Integer.parseInt(end) == 1) {
					ocg.endPlayerTurn();
				}
			}
			if (ocg.state() != 0) {
				break;
			}
		}
		sc.close();
	}

	public int turnCount() {
		return total_turns;
	}

	public int chips() {
		return hand_player.chips();
	}

	public void setUsername(String str) {
		name = str;
		System.out.println("name set as " + str); // debug line
	}

	public String username() {
		return name;
	}

	public int state() {
		return cases;
	}

}
