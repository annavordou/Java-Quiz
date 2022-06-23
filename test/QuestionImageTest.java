import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionImageTest {

    private QuestionImage qi;

    @BeforeEach
    void setUp() {
        qi = new QuestionImage("category1", "question1", "answer1", "answer2", "answer3", "answer4", "photo.jpg");
    }

    @Test
    void getImage() {
        String result = qi.getImage();
        assertEquals("photo.jpg", result);
    }
}