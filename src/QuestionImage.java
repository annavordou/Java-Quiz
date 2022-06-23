/**
 * Η κλάση αυτή αναπαριστά μία ερώτηση του παιχνιδιού που περιέχει και εικόνα.
 *
 * @author Anna Vordou
 * @version 1
 */
public class QuestionImage extends Question{

    private String image;

    /**
     * Η μέθοδος αυτή είναι ο κατασκευαστής της κλάσης ο
     * οποίος κληρονομεί από τον κατασκευαστή της κλάσης Question
     *
     * @param c συμβολοσειρά με την κατηγορία της ερώτησης
     * @param q συμβολοσειρά με την εκφώνηση της ερώτησης
     * @param a0 συμβλοσειρά με την 1η πιθανή απάντηση
     * @param a1 συμβλοσειρά με την 2η πιθανή απάντηση
     * @param a2 συμβλοσειρά με την 3η πιθανή απάντηση
     * @param a3 συμβλοσειρά με την 4η πιθανή απάντηση
     * @param im συμβλοσειρά με το όνομα της εικόνας
     */
    public QuestionImage(String c, String q, String a0, String a1, String a2, String a3, String im){
        super(c, q, a0, a1, a2, a3);
        image = im;
    }

    /**
     *
     * @return το URL της εικόνας
     */
    public String getImage(){
        return image;
    }
}
