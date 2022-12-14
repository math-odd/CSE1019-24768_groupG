# One Card Game
One Card Game is a card game made in Java with GUI.
원카드 게임은 자바로 작성한 카드게임입니다.

## Description
The goal was to create an interactive card game following the rules of [One-card](https://en.wikipedia.org/wiki/One-card). Currently, it allows a single game to be played with a total of four players including the human player.

![Screenshot of gameplay](game_screenshot.png)

## Gameplay 
One Card is currently able to play with three other computers. The game can be played by either (1) *playing a card* by clicking on the player's cards at the bottom, or (2) *skipping a turn* by pressing on the card deck at the center of the screen. If the player has no available cards to play, the game automatically pulls a card for the player.

One-card is a simpler form of UNO played with regular cards. The dealer deals 7 cards to each player at start. Each turn, the player can play a card that matches the rank or suit of the card last thrown. For detailed information about gameplay, refer to [game rules](#game-rules) below.

## Code Explanation
- OneCardGame
  - Dealer
    - CardDeckPut
    - CardDeckStart
  - CardPlayer
    - HumanPlayer
    - ComputerPlayer
- OneCardWriter
  - CardButton
  - ThrownDeckButton
  - CardImage


## Game Rules
The card last played is on the top of the thrown deck. Each turn, the player can place a card that matches the rank or suit of the card on the thrown deck. If the player has no cards to play, then they will pull a card from the deck. When a player has one card left, they have to say "One Card", signalling their win. If someone else says "One Card" before they do, then the player 
There are special cards that 

Attack cards are Joker cards, aces, or cards with rank 2. 

| Card Rank   | Description |
| ----------- | ----------- |
| Joker      | Deals 5 cards to the next player. *Can only be defended by a Joker.*   |
| Ace   | Deals  3 cards to the next player. *Can be defended by an Ace or a Joker.*   |
| 2 | Deals 2 cards to the next player. *Can be defnded by a 2 or a Joker.*|
| J | Skips a turn |
| Q | Changes direction |
| K | Play once more |


## Acknowledgements
* [alicek0](https://github.com/alicek0)
  * OneCardWriter, 
  * 
* [doyoon323](https://github.com/doyoon323)
  * Dealer, CardDeckStart, CardDeckPut
  * 오류수정
* 

### Images
* Card images from [Google Code Archive](https://code.google.com/archive/p/vector-playing-cards/downloads)
* Image of back of card from [Clipart Library](http://clipart-library.com/clipart/8cxrbGE6i.html) (personal use) 
* Images edited with [Pixlr](https://pixlr.com/x/#editor) and compressed with [I<3IMG](https://www.iloveimg.com/)

### External Code
* [Overlap Layout by Rob Camick](https://tips4java.wordpress.com/2009/07/26/overlap-layout/)
