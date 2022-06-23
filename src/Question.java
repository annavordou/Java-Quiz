import java.util.ArrayList;
import java.util.Random;

/**
 * Η κλάση αυτή αναπαριστά μία ερώτηση του παιχνιδιού.
 *
 * @author Anna Vordou
 * @version 1
 */

public class Question {

    private String question;
    private String[] answers;
    int correctAnswerIndex;
    private String questionCategory;

    /**
     * Η μέθοδος αυτή είναι ο κατασκευαστής της κλάσης.
     * @param c συμβολοσειρά με την κατηγορία της ερώτησης
     * @param q συμβολοσειρά με την εκφώνηση της ερώτησης
     * @param a0 συμβλοσειρά με την 1η πιθανή απάντηση
     * @param a1 συμβλοσειρά με την 2η πιθανή απάντηση
     * @param a2 συμβλοσειρά με την 3η πιθανή απάντηση
     * @param a3 συμβλοσειρά με την 4η πιθανή απάντηση
     */

    public Question(String c, String q, String a0, String a1, String a2, String a3){
        this.questionCategory = c;
        this.question= q;
        answers = new String[4];
        answers[0] = a0;
        answers[1] = a1;
        answers[2] = a2;
        answers[3] = a3;
        correctAnswerIndex = 0;
    }

    /**
     * Η μέθοδος αυτή εμφανίζει στην οθόνη την εκφώνηση της ερώτησης
     * και τις 4 πιθανές απαντήσεις με τυχαία σειρά.
     */

    public void displayQuestion(){
        System.out.println(question);
        int randomAnswerNum;
        Random ran = new Random();
        ArrayList<Integer> answerDisplayed;
        answerDisplayed = new ArrayList<>();
        int i = 0;
        while(i<4){
            randomAnswerNum = ran.nextInt(4);
            if(!answerDisplayed.contains(randomAnswerNum)) {
                System.out.print((i + 1) + ". ");
                System.out.println(answers[randomAnswerNum]);
                answerDisplayed.add(randomAnswerNum);
                if(randomAnswerNum == 0){
                    correctAnswerIndex = i;
                }
                i++;
            }
        }
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την θέση της σωστής απάντησης στον πίνακα απαντήσεων.
     *
     * @return την θέση της σωστής απάντησης στον πίνακα απαντήσεων
     */
    public int getCorrectAnswerIndex(){
        return correctAnswerIndex;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την εκφώνηση της σωστής απάντησης,
     * η οποία είναι κατά σύμβαση πάντα η 1η.
     *
     * @return την εκφώνηση της σωστής απάντησης
     */
    public String getCorrectAnswer(){
        return answers[0];
    }

    /**
     * Η μέθοδος αυτή επιστέφει την κατηγορία της ερώτησης.
     *
     * @return την κατηγορία της ερώτησης
     */
    public String getQuestionCategory(){
        return questionCategory;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την εκφώνηση της ερώτησης.
     *
     * @return την εκφώνηση της ερώτησης
     */
    public String getQuestion(){
        return question;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει την εκφώνηση της απάντησης που
     * βρίσκεται στη θέση που ορίζει η παράμετρος που δέχεται.
     *
     * @param n η θέση της απάντησης
     * @return την εκφώνηση της αντίστοιχης απάντησης
     */
    public String getAnswer(int n){
        return answers[n];
    }

    /**
     *
     * @param k η θέση της σωστής απάντησης στον πίνακα των απαντήσεων
     */
    public void setCorrectAnswerIndex(int k){
        correctAnswerIndex = k;
    }

}