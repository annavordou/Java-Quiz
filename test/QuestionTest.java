import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Η κλάση αυτή υλοποιεί τα απαραίτητα τεστ για την κλάση Question.
 *
 * @author Anna Vordou
 * @version 1
 */

class QuestionTest {

    private Question question1;

    @BeforeEach
    void setUp() {
        question1 = new Question("category1", "question1", "answer1", "answer2", "answer3", "answer4");
    }

    /*
    @Test
    void displayQuestion() {
    }*/

    @Test
    void getCorrectAnswerIndex() {
        int result = question1.getCorrectAnswerIndex();
        assertEquals(0, result);
    }

    @Test
    void getCorrectAnswer() {
        String result = question1.getCorrectAnswer();
        assertEquals("answer1", result);
    }

    @Test
    void getQuestionCategory() {
        String result = question1.getQuestionCategory();
        assertEquals("category1", result);
    }

    @Test
    void getQuestion() {
        String result = question1.getQuestion();
        assertEquals("question1", result);
    }

    @Test
    void getAnswer0() {
        String result = question1.getAnswer(0);
        assertEquals("answer1", result);
    }

    @Test
    void getAnswer1() {
        String result = question1.getAnswer(1);
        assertEquals("answer2", result);
    }

    @Test
    void getAnswer2() {
        String result = question1.getAnswer(2);
        assertEquals("answer3", result);
    }

    @Test
    void getAnswer3() {
        String result = question1.getAnswer(3);
        assertEquals("answer4", result);
    }

    @Test
    void getAnswerBelowBounds() {
        String result = question1.getAnswer(-1);
        assertNull(result);
    }

    @Test
    void getAnswerAboveBounds() {
        String result = question1.getAnswer(4);
        assertNull(result);
    }
}