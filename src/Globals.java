import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that holds all the global variables.
 * A list of all questions,a list for the category for each question ( parallel lists ),
 * and a list for each possible answer for every question.
 * @author Kitsios Vasileios
 * @version 0.0.1
 */

public class Globals {

    public static String[] categoriesArray = new String[100];
    public static String[] questionsArray = new String[100];
    public static String[][] answersArray = new String[100][4];
    public static String[] imagesArray = new String[100];


    /**
     * Constructor that fills all the lists with strings from the questions.txt file.
     * For the Questions list: [question1, question2, ....., question50]
     * For the Category list:  [category1,...., category10, category1, ....., category10]
     * For the Answers list:   [ [answer1, answer2, answer 3, answer4],
     *                           [            ....                   ],
     *                           [answer1, answer2, answer 3, answer4] ]
     * For the Images list: [imageName, -,...,imageName] ("-" when there is no image with the specific question)
     */
    public Globals()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("questions.txt")))
        {
            String line;
            int i = 0;
            while((line = reader.readLine())!=null)
            {
                String[] words = line.split("[*]");
                categoriesArray[i] = words[0];
                questionsArray[i] = words[1];
                answersArray[i][0] = words[2];
                answersArray[i][1] = words[3];
                answersArray[i][2] = words[4];
                answersArray[i][3] = words[5];
                imagesArray[i] = words[6];
                i++;
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
