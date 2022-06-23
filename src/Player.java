

/**
 * Class that represents a player. It saves the player's name and the points he/she has collected so far.
 * @author Kitsios Vasileios
 * @version 0.0.1
 */

public class Player {

    private String name;
    private int points;

    /**
     * Constructor that initializes object with the player's name and sets the points to zero.
     * @param n the name of the player object.
     */
    public Player(String n) {
        this.name = n;
        this.points = 0;
    }

    /**
     *
     * @return player's points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Method that increases the points the object has.
     * @param p amount of points to be added to object's current points.
     */
    public void increasePoints(int p) {
        this.points += p;
    }

    /**
     * Method that decreases the points the object has.
     * @param p amount of points to be decreased to object's current points.
     */
    public void decreasePoints(int p) {
        this.points -= p;
    }
}