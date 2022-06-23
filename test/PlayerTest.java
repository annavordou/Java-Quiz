import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player anna;
    @BeforeEach
    void setUp() {
        anna = new Player("Anna");
    }

    @Test
    void increasePoints() {
        anna.increasePoints(500);
        assertEquals(500, anna.getPoints());
    }

    @Test
    void decreasePoints() {
        anna.decreasePoints(500);
        assertEquals(-500, anna.getPoints());
    }
}