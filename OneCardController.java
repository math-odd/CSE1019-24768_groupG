
public class OneCardController {
	OneCardGame ocg;
    OneCardWriter ocw;

    OneCardController(){
        Dealer d = new Dealer();
        ocg = new OneCardGame(d);
        ocw = new OneCardWriter(ocg);
    }
    public void StratGame() {}
}
