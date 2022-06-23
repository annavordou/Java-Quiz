import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    private Round round;
    private Player p1;
    boolean[] isCorrect;

    @BeforeEach
    void setUp() {
        Globals globals = new Globals();
        p1 = new Player("player1");
        isCorrect = new boolean[1];
    }

    @Test
    void runCorrectAnswer() {
        round = new Round("correctAnswer", p1);
        isCorrect[0] = true;
        round.runCorrectAnswer(isCorrect);
        int points = p1.getPoints();
        assertEquals(1000,points);
    }

    @Test
    void runBet() {
        round = new Round("bet", p1);
        int[] bet = new int[1];
        bet[0] = 500;
        isCorrect[0] = true;
        round.runBet(isCorrect, bet);
        int points = p1.getPoints();
        assertEquals(500, points);
        bet[0] = 1000;
        isCorrect[0] = false;
        round.runBet(isCorrect, bet);
        points = p1.getPoints();
        assertEquals(-500, points);
    }

    @Test
    void runClock() {
        round = new Round("clock", p1);
        isCorrect[0] = true;
        double[] millis = new double[1];
        millis[0] = 1000;
        round.runClock(isCorrect, millis);
        int points = p1.getPoints();
        assertEquals(200,points);
    }
}