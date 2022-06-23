/**
 * Class that represents a round for two players and extends
 * class Round.
 *
 * @author Kitsios Vasileios
 * @version 0.0.1
 */
public class Round2p extends Round{

    private Player player2;
    private int p1Answer, p2Answer;

    /**
     * This is class's constructor.
     *
     * @param type Represents the type of this round (bet, correct answer etc)
     * @param p1 Class Player object that represents the 1st player that participates in this round.
     * @param p2 Class Player object that represents the 2nd player that participates in this round.
     */
    public Round2p(String type, Player p1, Player p2)
    {
        super(type, p1);
        player2 = p2;
    }

    @Override
    protected void runCorrectAnswer(boolean[] isCorrect) {
        super.runCorrectAnswer(isCorrect);
        if(isCorrect[1])
        { player2.increasePoints(1000); }
    }

    @Override
    protected void runBet(boolean[] isCorrect, int[] betAmount) {
        super.runBet(isCorrect, betAmount);
        if(isCorrect[1])
        { player2.increasePoints(betAmount[1]); }
        else
        { player2.decreasePoints(betAmount[1]); }
    }

    @Override
    protected void runClock(boolean[] isCorrect, double[] millisec) {
        super.runClock(isCorrect, millisec);
        if(isCorrect[1])
        { player2.increasePoints((int) (0.2*millisec[1])); }
    }


    /**
     *
     * @param isCorrect list of booleans corresponding to whether the answer(s) from the player(s) is/are correct or not.
     * @param timeUntilAnswer Amount of time it took for the players to answer.
     */
    protected void runQuickAnswer(boolean[] isCorrect, double[] timeUntilAnswer)
    {
        if(isCorrect[0] && isCorrect[1])
        {
            if(timeUntilAnswer[0]<timeUntilAnswer[1])
            {
                player1.increasePoints(1000);
                player2.increasePoints(500);
            }
        }
        else if(isCorrect[0])
        {
            player1.increasePoints(1000);
        }
        else if(isCorrect[1])
        {
            player2.increasePoints(1000);
        }
    }

    /**
     *
     * @param player 1 if the first player won, 2 of the second player won.
     */
    protected void runBestOfFive(int player)
    {
        if(player==1) { player1.increasePoints(5000); }
        else if(player==2) { player2.increasePoints(5000); }
    }
}
