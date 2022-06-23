import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import java.net.URL;

/**
 * Η κλάση αυτή αναπαριστά τα γραφικά του παιχνιδιού.
 *
 * @author Anna Vordou and Vasileios Kitsios
 * @version  1
 */

public class GameGUI {
    private int numOfPlayers;
    private Player p1, p2;
    private int numOfRounds;
    private Round[] rounds;

    // Kitsios Vasileios
    private JPanel startingScreenPanel;
    private JLabel welcomeLabel;
    private JPanel centerPanel;
    private JButton onePlayerButton, twoPlayersButton;
    private JButton matchHistoryButton;
    private JFrame matchHistoryFrame;
    private JLabel highestScoreLabel;
    private JLabel player1StatsLabel;
    private JLabel player2StatsLabel;

    // Anna Vordou
    private JFrame mainFrame;
    private JPanel mainPanel, topPanel, cardPanel;
    private JLabel categoryLabel, questionLabel, answer1Label, answer2Label, answer3Label, answer4label, imageLabel;
    private JPanel displayPanel;
    private JLabel roundLabel, timerLabel, score1Label, score2Label;
    private ImageIcon imageIcon;
    private JLabel explainLabel1, explainLabel2;
    private JPanel explainPanel;
    private int highestScore, victories1, victories2;


    // Kitsios Vasileios & Anna Vordou
    private int [] answers;
    private boolean[] hasAnswered;
    private int currentRound, currentQuestion;
    private boolean[] results;
    private int[] bets;
    private double[] millisecondsLeft;
    private double p1AnswerStart, p1AnswerEnd, p2AnswerStart, p2AnswerEnd;
    private int[] outOfFive;

    // Kitsios Vasileios
    private JPanel gameFinishedPanel;
    private JLabel gameFinishedMessageLabel;
    private JLabel player1PointsLabel;
    private JLabel player2PointsLabel;
    private JPanel gameFinishedDisplayPointsPanel;

    // Kitsios Vasileios
    private JPanel takeBetsPanel;
    private JLabel player1BetLabel, player2BetLabel, questionCategory;
    private JTextField takePlayer1Bet, takePlayer2Bet;

    /**
     * Εδώ έχουμε τον κατασκευαστή της κλάσης όπου δημιουργούμε τα περισσότερα γραφικά
     * και καλούμε τις απαραίτητες συναρτήσεις.
     */
    public GameGUI(){
        numOfRounds = 5;

        //main frame - Anna
        mainFrame = new JFrame("Quiz App");
        mainFrame.setSize(500,500);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setFocusable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(numOfPlayers==1) takeAnswer1p(e);
                else takeAnswer2p(e);
            }
        });

        //card layout - Anna
        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());
        mainFrame.add(cardPanel);

        // Starting screen panel:
        startingScreenPanel = new JPanel();
        startingScreenPanel.setLayout(new BorderLayout());
        cardPanel.add(startingScreenPanel);

        // welcome label:
        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 23));
        startingScreenPanel.add(welcomeLabel, BorderLayout.PAGE_START);

        // center panel:
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        startingScreenPanel.add(centerPanel, BorderLayout.CENTER);

        // 1p & 2p buttons:
        onePlayerButton = new JButton("1p");
        onePlayerButton.setPreferredSize(new Dimension(100, 100));
        onePlayerButton.setFont(new Font("Verdana", Font.BOLD, 20));
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 1;
                rounds = new Round[numOfRounds];

                p1 = new Player("p1");
                results = new boolean[numOfPlayers];
                bets = new int[numOfPlayers];
                millisecondsLeft = new double[numOfPlayers];
                answers = new int[numOfPlayers];
                hasAnswered = new boolean[numOfPlayers];
                outOfFive = new int[numOfPlayers];

                makeGame();
                makeMainPanel(1);
                //handlingKeys(1);
                startingScreenPanel.setVisible(false);
                mainPanel.setVisible(true);
                mainFrame.setFocusable(true);
                currentRound = 0;
                currentQuestion = 0;
                if(rounds[currentRound].getRoundType().equals("bet"))
                    takeBets();
                displayQuestion(currentRound, currentQuestion);
                p1AnswerStart = System.currentTimeMillis();
            }
        });

        twoPlayersButton = new JButton("2p");
        twoPlayersButton.setPreferredSize(new Dimension(100, 100));
        twoPlayersButton.setFont(new Font("Verdana", Font.BOLD, 20));
        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfPlayers = 2;
                rounds = new Round[numOfRounds];

                p1 = new Player("player1");
                p2 = new Player("player2");
                results = new boolean[numOfPlayers];
                bets = new int[numOfPlayers];
                millisecondsLeft = new double[numOfPlayers];
                answers = new int[numOfPlayers];
                hasAnswered = new boolean[numOfPlayers];
                outOfFive = new int[numOfPlayers];

                makeGame();
                makeMainPanel(2);
                //handlingKeys(2);
                startingScreenPanel.setVisible(false);
                mainPanel.setVisible(true);
                mainFrame.setFocusable(true);
                currentRound = 0;
                currentQuestion = 0;
                if(rounds[currentRound].getRoundType().equals("bet"))
                    takeBets();
                displayQuestion(currentRound, currentQuestion);
                p1AnswerStart = System.currentTimeMillis();
                p2AnswerStart = System.currentTimeMillis();
            }
        });

        centerPanel.add(onePlayerButton);
        centerPanel.add(twoPlayersButton);

        // explain panel - Anna
        explainLabel1 = new JLabel("Player1: 1-1, 2-2, 3-3, 4-4");
        explainLabel2 = new JLabel("Player2: 1-a, 2-s, 3-d, 4-f");
        explainLabel1.setFont(new Font("Verdana", Font.ITALIC, 23));
        explainLabel2.setFont(new Font("Verdana", Font.ITALIC, 23));
        explainPanel = new JPanel();
        explainPanel.setLayout(new GridLayout(2,1));
        explainPanel.add(explainLabel1);
        explainPanel.add(explainLabel2);
        centerPanel.add(explainPanel,BorderLayout.CENTER);

        // match history button:
        matchHistoryButton = new JButton("Match  History");
        matchHistoryButton.setFont(new Font("Verdana", Font.BOLD, 20));
        matchHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMatchHistoryButton();
                matchHistoryFrame.setVisible(true);
            }
        });
        startingScreenPanel.add(matchHistoryButton, BorderLayout.PAGE_END);

        // match history frame:
        matchHistoryFrame = new JFrame("Match History");
        matchHistoryFrame.setSize(300, 300);
        matchHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        matchHistoryFrame.setLocationRelativeTo(null);
        matchHistoryFrame.setResizable(false);
        matchHistoryFrame.setLayout(new GridLayout(3, 1, 10, 10));

        // match history labels:
        highestScoreLabel = new JLabel("Highest score: " + 0);
        player1StatsLabel = new JLabel("Player1 victories: " + 0);
        player2StatsLabel = new JLabel("Player2 victories: " + 0);
        matchHistoryFrame.add(highestScoreLabel);
        matchHistoryFrame.add(player1StatsLabel);
        matchHistoryFrame.add(player2StatsLabel);

        // Game finished panel:
        gameFinishedPanel = new JPanel();
        gameFinishedPanel.setLayout(new BorderLayout());
        gameFinishedPanel.setVisible(false);
        cardPanel.add(gameFinishedPanel);

        // Game finished points sub-panel
        gameFinishedDisplayPointsPanel = new JPanel();
        gameFinishedDisplayPointsPanel.setLayout(new GridLayout(2,1));
        gameFinishedPanel.add(gameFinishedDisplayPointsPanel, BorderLayout.CENTER);

        // Game finished labels:
        gameFinishedMessageLabel = new JLabel("Game finished.");
        gameFinishedMessageLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        gameFinishedMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        gameFinishedPanel.add(gameFinishedMessageLabel, BorderLayout.PAGE_START);

        // Asking bets panel and labels:
        takeBetsPanel = new JPanel();
        takeBetsPanel.setLayout(new GridLayout(3, 2));

        player1BetLabel = new JLabel("Player1 bet: ");
        takePlayer1Bet = new JTextField();
        player2BetLabel = new JLabel("Player2 bet: ");
        takePlayer2Bet = new JTextField();
        questionCategory = new JLabel();

        takeBetsPanel.add(player1BetLabel);
        takeBetsPanel.add(takePlayer1Bet);
        takeBetsPanel.add(player2BetLabel);
        takeBetsPanel.add(takePlayer2Bet);
        takeBetsPanel.add(questionCategory);

        //cardPanel.setVisible(true);
        mainFrame.setVisible(true);
    }


    /**
     * Method that creates 5 rounds of a random type each.
     * Depending on the button the user pressed (1 player or 2 players) we create the corresponding
     * round instance (Round or Round2p)
     * @author Kitsios Vasileios
     */
    private void makeGame()
    {
        if(numOfPlayers == 1) {
            for(int i = 0; i<numOfRounds; i++) {
                int type;
                Random ran = new Random();
                type = ran.nextInt(3);
                if (type == 0) {
                    rounds[i] = new Round("correctAnswer", p1);
                }
                else if (type == 1) {
                    rounds[i] = new Round("bet", p1);
                }
                else{
                    rounds[i] = new Round("clock", p1);
                }
            }
        }
        else if(numOfPlayers == 2) {
            for(int i = 0; i<numOfRounds; i++) {
                int type;
                Random ran = new Random();
                type = ran.nextInt(5);
                if (type == 0) {
                    rounds[i] = new Round2p("correctAnswer", p1, p2);
                }
                else if (type == 1) {
                    rounds[i] = new Round2p("bet", p1, p2);
                }
                else if (type == 2) {
                    rounds[i] = new Round2p("clock", p1, p2);
                }
                else if (type == 3) {
                    rounds[i] = new Round2p("quickAnswer", p1, p2);
                }
                else{
                    rounds[i] = new Round2p("bestOfFive", p1, p2);
                }
            }
        }
    }

    /**
     * Η μέθοδος αυτή φτιάχνει το βασικό panel του παιχνιδιού που θα εμφανίζεται όσο οι
     * παίζουν και θα παρουσιάζει τις ερωτήσεις, τις απαντήσεις, την κατηγορία της τρέχουσας
     * ερώτησης, τον αριθμό του τρέχοντος γύρου, την εικόνα όπου υπάρχει, καθώς επίσης και
     * το τρέχον σκορ.
     *
     * @param numOfPlayers ο αριθμός των παικτών με βάση τον οπόιο προσαρμόζονται τα παραπάνω
     * @author Anna Vordou
     */
    private void makeMainPanel(int numOfPlayers)
    {
        //basic panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        categoryLabel = new JLabel();
        //mainPanel.setFocusable(true);
        mainPanel.add(categoryLabel, BorderLayout.PAGE_END);

        //top panel
        topPanel = new JPanel();
        if (numOfPlayers == 1){
            topPanel.setLayout(new GridLayout(3,0));
            score1Label = new JLabel("Player 1: 0");
            topPanel.add(score1Label);
        }
        else if (numOfPlayers == 2){
            topPanel.setLayout(new GridLayout(4,0));
            score1Label = new JLabel("Player 1: 0");
            score2Label = new JLabel("Player 2: 0");
            topPanel.add(score1Label);
            topPanel.add(score2Label);
        }
        roundLabel = new JLabel();
        topPanel.add(roundLabel);
        timerLabel = new JLabel("Timer: 00.00");
        timerLabel.setVisible(false);
        topPanel.add(timerLabel);
        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        //display question & answers
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayout(6,0));
        questionLabel = new JLabel();
        imageLabel = new JLabel();
        imageLabel.setVisible(false);
        answer1Label = new JLabel();
        answer2Label = new JLabel();
        answer3Label = new JLabel();
        answer4label = new JLabel();
        displayPanel.add(questionLabel);
        displayPanel.add(imageLabel);
        displayPanel.add(answer1Label);
        displayPanel.add(answer2Label);
        displayPanel.add(answer3Label);
        displayPanel.add(answer4label);
        mainPanel.add(displayPanel, BorderLayout.CENTER);

        cardPanel.add(mainPanel);
    }

    /**
     * Η μέθοδος αυτή ενημερώνει κατά τη διάρκεια του παιχνιδιού τα labels των ερωτήσεων,
     * απαντήσεων, αριμού γύρου και κατηγορίας ερώτησης. Αν περιλαμβάνεται και εικόνα,
     * εμφανίζει και την εικόνα.
     *
     * @param roundIndex η θέση του γύρου που βρισκόμαστε στον πίνακα των γύρων
     * @param questionIndex η θέση της ερώτησης που βρισκόμαστε με βάση τον πίνακα
     *                      που περιέχει τις ερωτήσεις στην κλάση Round
     *
     * @author Anna Vordou
     */
    private void displayQuestion(int roundIndex, int questionIndex)
    {
        Question question = rounds[roundIndex].getQuestions()[questionIndex];
        roundLabel.setText("Round: " + (roundIndex+1) + " -- " + rounds[roundIndex].getRoundType());
        categoryLabel.setText("Question category: " + question.getQuestionCategory());
        questionLabel.setText(question.getQuestion());
        int randomAnswerNum;
        Random ran = new Random();
        ArrayList<Integer> answerDisplayed;
        answerDisplayed = new ArrayList<>();
        int i = 0;
        while(i<4){
            randomAnswerNum = ran.nextInt(4);
            if(!answerDisplayed.contains(randomAnswerNum)){
                if(i==0){
                    answer1Label.setText("1) " + question.getAnswer(randomAnswerNum));
                }
                else if(i==1){
                    answer2Label.setText("2) " + question.getAnswer(randomAnswerNum));
                }
                else if(i==2){
                    answer3Label.setText("3) " + question.getAnswer(randomAnswerNum));
                }
                else{
                    answer4label.setText("4) " + question.getAnswer(randomAnswerNum));
                }
                answerDisplayed.add(randomAnswerNum);
                if(randomAnswerNum == 0){
                    question.setCorrectAnswerIndex(i);
                }
                i++;
            }
        }
        if (question instanceof QuestionImage){
            URL url = getClass().getResource(((QuestionImage) question).getImage());
            if (url != null) {
                imageIcon = new ImageIcon(url);
                Image image = imageIcon.getImage();
                Image newImg = image.getScaledInstance(500,100, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(newImg);
            }
            imageLabel.setIcon(imageIcon);
            imageLabel.setVisible(true);
        }
        else { imageLabel.setVisible(false); }
    }


    /**
     * For one player game this method takes the input the user types.
     * If the user presses any other key other than [1,2,3,4] it returns and the game waits for new input.
     * If the input is correct then it stores the answer, counts elapses time since the question was displayed,
     * and checks if the answer is correct or not. Then it updates the points calling the runRound() method
     * and it proceeds to the next question and restarts the timer.
     * If it is the last question it calls the finishGame() method.
     * @author Kitsios Vasileios
     * @param e the key user presses
     */
    private void takeAnswer1p(KeyEvent e) {
        if (e.getKeyChar() == '1')
            answers[0] = 0;
        else if (e.getKeyChar() == '2')
            answers[0] = 1;
        else if (e.getKeyChar() == '3')
            answers[0] = 2;
        else if (e.getKeyChar() == '4')
            answers[0] = 3;
        else return;

        p1AnswerEnd = System.currentTimeMillis(); // Stop counting time
        // Store how many milliseconds were remaining from the 5sec timer(== 5000 milliseconds)
        if (5000 - (p1AnswerEnd-p1AnswerStart)>0) { millisecondsLeft[0] = 5000 - (p1AnswerEnd - p1AnswerStart); }
        else { millisecondsLeft[0] = 0; }
        results[0] = answers[0] == rounds[currentRound].getQuestions()[currentQuestion].getCorrectAnswerIndex();
        runRound(rounds[currentRound].getRoundType());
        score1Label.setText("Player1: " + p1.getPoints());
        if (currentQuestion >= rounds[currentRound].getNumOfQuestions()-1)
        {
            currentQuestion = 0;
            if(currentRound==numOfRounds-1) {
                finishGame();
                return;
            }
            currentRound += 1;
        }
        else
            currentQuestion += 1;
        if(rounds[currentRound].getRoundType().equals("bet"))
            takeBets();
        displayQuestion(currentRound, currentQuestion);
        // sleep for 3 sec maybe?????????????????
        p1AnswerStart = System.currentTimeMillis();
    }


    /**
     * similar with takeAnswers1p(). Note that for multiplayer games we need to wait for both users to give
     * their answers and only then proceed to update points and to the next question.
     * That said, the method uses a variable that has true or false depending if a player has given their answer yet.
     * when both players answer it continues with the next question.
     * @author Kitsios Vasileios
     * @param e the key user presses
     */
    private void takeAnswer2p(KeyEvent e)
    {
        if (e.getKeyChar() == '1') {
            answers[0] = 0;
            hasAnswered[0] = true;
            p1AnswerEnd = System.currentTimeMillis(); // Stop counting time for p1
        }
        else if (e.getKeyChar() == '2') {
            answers[0] = 1;
            hasAnswered[0] = true;
            p1AnswerEnd = System.currentTimeMillis(); // Stop counting time for p1
        }
        else if (e.getKeyChar() == '3') {
            answers[0] = 2;
            hasAnswered[0] = true;
            p1AnswerEnd = System.currentTimeMillis(); // Stop counting time for p1
        }
        else if (e.getKeyChar() == '4') {
            answers[0] = 3;
            hasAnswered[0] = true;
            p1AnswerEnd = System.currentTimeMillis(); // Stop counting time for p1
        }
        else if (e.getKeyChar() == 'a') {
            answers[1] = 0;
            hasAnswered[1] = true;
            p2AnswerEnd = System.currentTimeMillis(); // Stop counting time for p2
        }
        else if (e.getKeyChar() == 's') {
            answers[1] = 1;
            hasAnswered[1] = true;
            p2AnswerEnd = System.currentTimeMillis(); // Stop counting time for p2
        }
        else if (e.getKeyChar() == 'd') {
            answers[1] = 2;
            hasAnswered[1] = true;
            p2AnswerEnd = System.currentTimeMillis(); // Stop counting time for p2
        }
        else if (e.getKeyChar() == 'f') {
            answers[1] = 3;
            hasAnswered[1] = true;
            p2AnswerEnd = System.currentTimeMillis(); // Stop counting time for p2
        }
        else return;
        if (5000 - (p1AnswerEnd-p1AnswerStart)>0) { millisecondsLeft[0] = 5000 - (p1AnswerEnd - p1AnswerStart); }
        else { millisecondsLeft[0] = 0; }
        if (5000 - (p2AnswerEnd-p2AnswerStart)>0) { millisecondsLeft[1] = 5000 - (p2AnswerEnd - p2AnswerStart); }
        else { millisecondsLeft[1] = 0; }

        results[0] = answers[0] == rounds[currentRound].getQuestions()[currentQuestion].getCorrectAnswerIndex();
        results[1] = answers[1] == rounds[currentRound].getQuestions()[currentQuestion].getCorrectAnswerIndex();
        if(results[0] && (e.getKeyChar()=='1' || e.getKeyChar()=='2' || e.getKeyChar()=='3' || e.getKeyChar()=='4'))
            outOfFive[0]++;
        if(results[1] && (e.getKeyChar()=='a' || e.getKeyChar()=='s' || e.getKeyChar()=='d' || e.getKeyChar()=='f'))
            outOfFive[1]++;
        if(hasAnswered[0] && hasAnswered[1])
        {
            runRound(rounds[currentRound].getRoundType());
            score1Label.setText("Player1: " + p1.getPoints());
            score2Label.setText("Player2: " + p2.getPoints());
            hasAnswered[0] = false;
            hasAnswered[1] = false;
            if (currentQuestion >= (rounds[currentRound].getNumOfQuestions()-1) || (outOfFive[0]==5 || outOfFive[1]==5))
            {
                currentQuestion = 0;
                outOfFive[0] = 0;
                outOfFive[1] = 0;
                if(currentRound==(numOfRounds-1)) {
                    finishGame();
                    return;
                }
                currentRound += 1;
            }
            else
                currentQuestion += 1;
            if(rounds[currentRound].getRoundType().equals("bet"))
                takeBets();
            displayQuestion(currentRound, currentQuestion);
            // sleep for 3 sec maybe?????????????????
            p1AnswerStart = System.currentTimeMillis();
            p2AnswerStart = System.currentTimeMillis();
        }
    }


    /**
     * Method that pops up a window presenting the user(s) with the next question category and asks for the bet amount(s)
     * In order to avoid any bugs we check if the input the user gives can be converted to numeric amount.
     * In addition to that, in case the user gives no input but just clicks on "cancel" button the method
     * won't finish and it will ask for bet again and again until it receives a valid amount.
     * @author Kitsios Vasileios
     */
    private void takeBets() {
        questionCategory.setText("Question category: " + rounds[currentRound].getQuestions()[currentQuestion].getQuestionCategory());
        if (numOfPlayers == 1)
        {
            player2BetLabel.setVisible(false);
            takePlayer2Bet.setVisible(false);
        }
        int result = JOptionPane.showConfirmDialog(null, takeBetsPanel, "Give bets", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION)
        {
            if(numOfPlayers==1)
            {
                String b = takePlayer1Bet.getText();
                if(isNumeric(b)) {
                    int b_int = Integer.parseInt(b);
                    if (b_int == 250 || b_int == 500 || b_int == 750 || b_int == 1000)
                        bets[0] = b_int;
                    else takeBets();
                }
                else takeBets();
            }
            else
            {
                String b1,b2;
                int b1_int, b2_int;
                b1 = takePlayer1Bet.getText();
                b2 = takePlayer2Bet.getText();
                if(isNumeric(b1) && isNumeric(b2)) {
                    b1_int = Integer.parseInt(b1);
                    b2_int = Integer.parseInt(b2);
                    if ((b1_int == 250 || b1_int == 500 || b1_int == 750 || b1_int == 1000) && (b2_int == 250 || b2_int == 500 || b2_int == 750 || b2_int == 1000)) {
                        bets[0] = b1_int;
                        bets[1] = b2_int;
                    } else takeBets();
                }
                else takeBets();
            }
        }
        else takeBets();
    }

    /**
     * Depending of the current round type, this method takes the answer(s) the user(s) gave and updates points
     * depending on type of round calling the correct methods from Round and Round2p classes.
     * @author Kitsios Vasileios
     * @param type type of the current round
     */
    private void runRound(String type)
    {
        if(type.equals("correctAnswer"))
        {
            rounds[currentRound].runCorrectAnswer(results);
        }
        else if(type.equals("bet"))
            rounds[currentRound].runBet(results, bets);
        else if(type.equals("clock"))
            rounds[currentRound].runClock(results, millisecondsLeft);
        else if(type.equals("quickAnswers"))
        {
            double[] timeForAnswer = new double[2];
            timeForAnswer[0] = p1AnswerEnd-p1AnswerStart;
            timeForAnswer[1] = p2AnswerEnd-p2AnswerStart;
            ((Round2p) rounds[currentRound]).runQuickAnswer(results, timeForAnswer);
        }
        else if(type.equals("bestOfFive"))
        {
            if(outOfFive[0]==5 && outOfFive[1]==5)
            {
                if(p1AnswerEnd-p1AnswerStart<p2AnswerEnd-p2AnswerStart)
                    ((Round2p) rounds[currentRound]).runBestOfFive(1);
                else
                    ((Round2p) rounds[currentRound]).runBestOfFive(2);
            }
            else if(outOfFive[0]==5)
                ((Round2p) rounds[currentRound]).runBestOfFive(1);
            else if(outOfFive[1]==5)
                ((Round2p) rounds[currentRound]).runBestOfFive(2);
        }
    }

    /**
     * Creates and shows the panel that presents the players with the game details and stores the scores
     * in match history file.
     * Also makes the frame to stop accepting answers.
     * @author Kitsios Vasileios
     */
    private void finishGame()
    {
        setMatchHistory();
        player1PointsLabel = new JLabel("Player 1 has " + p1.getPoints() + " points.");
        player1PointsLabel.setHorizontalAlignment(JLabel.CENTER);
        player1PointsLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        gameFinishedDisplayPointsPanel.add(player1PointsLabel);
        if(numOfPlayers==2) {
            player2PointsLabel = new JLabel("Player 2 has " + p2.getPoints() + " points.");
            player2PointsLabel.setHorizontalAlignment(JLabel.CENTER);
            player2PointsLabel.setFont(new Font("Verdana", Font.BOLD, 20));
            gameFinishedDisplayPointsPanel.add(player2PointsLabel);
        }
        mainFrame.setFocusable(false);
        mainPanel.setVisible(false);
        gameFinishedPanel.setVisible(true);
    }

    /**
     * Η μέθοδος αυτή ενημερώνει από το αρχείο matchHistory.txt τις μεταβλητές highestScore, victories1
     * και victories2 και στη συνέχεια το αρχείο αυτό με βάση τις καινούριες τιμές των
     * παραπάνω μεταβλητών.
     *
     * @author Anna Vordou
     */
    private void setMatchHistory(){
        try(BufferedReader reader = new BufferedReader(new FileReader("matchHistory.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] words = line.split(" ");
                highestScore = Integer.parseInt(words[0]);
                victories1 = Integer.parseInt(words[1]);
                victories2 = Integer.parseInt(words[2]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        if(numOfPlayers == 1){
            if(highestScore < p1.getPoints()){
                highestScore = p1.getPoints();
            }
        }
        else if (numOfPlayers == 2){
            if(p1.getPoints() > p2.getPoints()){
                victories1 += 1;
            }
            else if (p1.getPoints() < p2.getPoints()){
                victories2 += 1;
            }
            else{
                victories1 += 1;
                victories2 += 1;
            }
        }
        try (FileWriter w = new FileWriter("matchHistory.txt")) {
            w.write(highestScore + " " + victories1 + " " + victories2);
        } catch (IOException e) {
            System.out.println("Παρουσιάστηκε πρόβλημα");
        }

    }

    /**
     * Η μέθοδος αυτή διαβάζει από το αρχείο matchHistory.txt το μεγαλύτερο σκορ
     * και τις νίκες του κάθε παίκτη και ενημερώνει τα κατάλληλα labels.
     *
     * @author Anna Vordou
     */
    private void setMatchHistoryButton(){
        try(BufferedReader reader = new BufferedReader(new FileReader("matchHistory.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] words = line.split(" ");
                highestScoreLabel.setText("Highest score: " + words[0]);
                player1StatsLabel.setText("Player1 victories: " + words[1]);
                player2StatsLabel.setText("Player2 victories: " + words[2]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param s string input
     * @return true if the string can be converted to numerical value, false otherwise.
     */
    private boolean isNumeric(String s)
    {
        if (s == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
