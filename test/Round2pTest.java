import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Round2pTest {
    Globals globals = new Globals();
    Round2p round2p;
    Player p1, p2;
    boolean[] isCorrect;

    @BeforeEach
    void setUp() {
        p1 = new Player("Anna");
        p2 = new Player("Bill");
        isCorrect = new boolean[2];
    }

    @Test
    void runQuickAnswer() {
        round2p = new Round2p("quickAnswer", p1, p2);
        double[] times = new double[2];
        times[0] = 1000;
        times[1] = 1500;
        isCorrect[0] = true;
        isCorrect[1] = true;
        round2p.runQuickAnswer(isCorrect, times);
        int p1points = p1.getPoints();
        int p2points = p2.getPoints();
        assertEquals(1000, p1points);
        assertEquals(500, p2points);
    }

    @Test
    void runBestOfFive() {
        round2p = new Round2p("bestOfFive", p1, p2);
        round2p.runBestOfFive(1);
        int points = p1.getPoints();
        assertEquals(5000, points);
    }
}