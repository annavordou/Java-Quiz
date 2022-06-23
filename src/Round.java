import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.ArrayList;



/** Class that represents a round. Each round has a type, a number of questions
 * that will be displayed in that round and the category the questions will belong to.
 * @author Kitsios Vasileios
 * @version 0.0.1
 */

public class Round {

    protected String roundType;
    protected int numOfQuestions;
    protected Question[] questions;
    protected Player player1;
    protected ArrayList<String> categoriesAsked;
    protected static boolean[] questionsAsked = new boolean[100];

    /**
     * Constructor that initializes object with the type and category and sets the number of questions to five.
     * @param type Represents the type of this round (bet, correct answer etc)
     * @param p1 Class Player object that represents the player that participates in this round.
     */

    public Round(String type, Player p1) {

        this.roundType = type;
        categoriesAsked = new ArrayList<>();
        player1 = p1;
        importQuestions(roundType);
    }


    /**
     * Fills the questions[] array with random questions
     * @param t type of round
     */
    private void importQuestions(@NotNull String t) {

        String category, question, answer1, answer2, answer3, answer4, img;
        // In every round type we have 5 questions except from round type "bestOfFive" in which
        // the players keep answering questions until someone or both have 5 correct answers.
        // In that case we want to make sure we have enough amount of questions to ask the players.
        if(!(t.equals("bestOfFive"))) {
            this.numOfQuestions = 5;
        }
        else {
            this.numOfQuestions = 10;
        }
        this.questions = new Question[numOfQuestions];

        int i = 0;
        int randomNum;
        Random ran = new Random();

        /*
         * Fill the <questions> array with 5 random questions.
         * Note that we cant take a question more than once in a single game
         * and every round can't have a question from the same category more than once.
         */
        while(i<numOfQuestions)
        {
            randomNum = ran.nextInt(100);
            category = Globals.categoriesArray[randomNum];
            question = Globals.questionsArray[randomNum];
            answer1 = Globals.answersArray[randomNum][0];
            answer2 = Globals.answersArray[randomNum][1];
            answer3 = Globals.answersArray[randomNum][2];
            answer4 = Globals.answersArray[randomNum][3];
            img = Globals.imagesArray[randomNum];
            if(!(questionsAsked[randomNum]) && !(categoriesAsked.contains(category)))
            {
                if(img.equals("-"))
                    questions[i] = new Question(category, question, answer1, answer2, answer3, answer4);
                else
                    questions[i] = new QuestionImage(category, question, answer1, answer2, answer3, answer4, "photos/" + img);
                categoriesAsked.add(Globals.categoriesArray[randomNum]);
                questionsAsked[randomNum] = true;
                i++;
            }
        }
    }

    /**
     *
     * @param isCorrect list of booleans corresponding to whether the answer(s) from the player(s) is/are correct or not.
     */
    protected void runCorrectAnswer(boolean[] isCorrect)
    {
        if(isCorrect[0])
        { player1.increasePoints(1000); }
    }

    /**
     *
     * @param isCorrect list of booleans corresponding to whether the answer(s) from the player(s) is/are correct or not.
     * @param betAmount The amount(s) the users typed for bet for this round.
     */
    protected void runBet(boolean[] isCorrect, int[] betAmount)
    {
        if(isCorrect[0])
        { player1.increasePoints(betAmount[0]); }
        else
        { player1.decreasePoints(betAmount[0]); }
    }

    /**
     *
     * @param isCorrect list of booleans corresponding to whether the answer(s) from the player(s) is/are correct or not.
     * @param millisec milliseconds left from 5sec since the user answered
     */
    protected void runClock(boolean[] isCorrect, double[] millisec)
    {
        if(isCorrect[0])
        { player1.increasePoints((int) (0.2*millisec[0])); }
    }

    /**
     *
     * @return the type of current round
     */
    public String getRoundType() { return roundType; }

    /**
     *
     * @return the list which contains objects of class Question
     */
    public Question[] getQuestions() { return questions; }

    /**
     *
     * @return the number of questions for the current round
     */
    public int getNumOfQuestions() { return numOfQuestions; }
}