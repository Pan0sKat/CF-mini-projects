package gr.aueb.cf.miniprojects;

import java.util.Arrays;

/**
 * Η εφαρμογή αυτή διαβάζει ένα δισδιάστατο πίνακα που περιέχει τα στοιχεία άφιξης και αναχώρησης αυτοκινήτων σε μορφή
 * arr[][] = {{1012, 1136}, {1317, 1417}, {1015, 1020}} και εκτυπώνει τον μέγιστο αριθμό αυτοκινήτων που είναι
 * σταθμευμένα το ίδιο χρονικό διάστημα
 */
public class Project4 {
    public static void main(String[] args) {
        int[][] arr = {{1012, 1136}, {1317, 1417}, {1130, 1211}, {1019, 1130}, {1015, 1020}};
        int[][] timeArray = new int[2 * arr.length][2];
        int numCars = 0;
        int maxCars = 0;

        /* Κατασκευή νέου (2*i)x2 πίνακα timeArray με 1 ως στοιχεία της 2ης στήλης όταν αντιστοιχούν σε ώρα εισόδου και
         -1 όταν αντιστοιχούν σε ώρα εξόδου */
        for (int i = 0; i < arr.length; i++) {
            timeArray[2 * i][0] = arr[i][0];
            timeArray[2 * i][1] = 1;
            timeArray[2 * i + 1][0] = arr[i][1];
            timeArray[2 * i + 1][1] = -1;
        }

        // Ταξινόμηση του νέου πίνακα timeArray με βάσει την αύξουσα σειρά των στοιχείων της 1ης στήλης
        Arrays.sort(timeArray, (a, b) -> a[0] - b[0]);

//        /*Εμφάνιση του ταξινομημένου πίνακα*/
//        for (int[] row : timeArray) {
//            for (int col : row) {
//                System.out.printf("%2d\t", col);
//            }
//            System.out.println();
//        }

        /*Υπολογισμός του μέγιστου αριθμού ταυτόχρονα παρκαρισμένων αυτοκινήτων αθροίζοντας τις τιμές της 2ης στήλης
        και βρίσκοντας το μέγιστο άθροισμα */
        for (int i = 0; i < timeArray.length; i++) {
            if ((numCars += timeArray[i][1]) > maxCars) {
                maxCars = numCars;
            }
        }
        System.out.println("Max number of concurrently parked cars is " + maxCars + ".");
    }
}
