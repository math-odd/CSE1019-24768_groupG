import javax.swing.*;
public class OneCardController {
	OneCardGame ocg;
    OneCardWriter ocw;

    OneCardController(){
        Dealer d = new Dealer();
        ocg = new OneCardGame(d);
        ocw = new OneCardWriter(ocg);
        while(true) {
        	ocg.StartGame();
        	if (ReGame()) {continue;}
        	else {break;}
        }
    }
    
    
    public boolean ReGame() {
    	String re = JOptionPane.showInputDialog("Regame? (Y/N)");
    	if (re == "Y" || re == "Y") {return true;}
    	else if (re == "n" || re == "N") {return false;}
    	else {return ReGame();}
    }
}
