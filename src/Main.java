/**
 *
 * @author Anna Vordou
 * @version 1
 */

public class Main{

    /**
     * Αυτή είναι η βασική μέθοδος του προγράμματος που δημιουργεί ένα αντικείμενο της
     * κλάσης Globals για να γεμίσουν οι πίνακες και ένα της κλάσης GameGUI
     * για να ξεκινήσει το παιχνίδι.
     *
     * @param args για να οριστεί η συνάρτηση
     */
    public static void main(String[] args) {
        Globals globals = new Globals();
        GameGUI game = new GameGUI();
    }
}