package gr.aueb.cf.miniprojects;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Το πρόγραμμα αυτό βρίσκει το low και high index για ένα δοσμένο στοιχείο σε έναν ταξινομημένο πίνακα ακεραίων
 */
public class Project5 {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;
        int num = -1;
        int[] arr = {0, 1, 4, 4, 4, 6, 7, 8, 8, 8, 8, 9};
        int[] lowHighIndexes = new int[2];

        //Εμφάνιση μενού επιλογών
        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                //e1.printStackTrace();
                System.out.println("Only choices 0 or 1 are allowed. ");
                choice = -1;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    input.close();
                    break;
                case 1:
                    System.out.print("Please enter an integer to return its low and high index: ");
                    try {
                        num = input.nextInt();
                        lowHighIndexes = getLowAndHighIndexOf(arr, num);
                        // Αν δεν βρεθεί ο αριθμός στον πίνακα
                        if (lowHighIndexes[0] == -1) {
                            System.out.println("The number you entered was not found.");
                        } else { //Αν υπάρχει ο αριθμός στον πίνακα
                            System.out.println("Low index of " + num + " : " + lowHighIndexes[0]);
                            System.out.println("High index of " + num + " : " + lowHighIndexes[1]);
                        }
                    } catch (InputMismatchException e2) {
                        //e2.printStackTrace();
                        input.nextLine();
                        System.out.println("Wrong input. Please enter an integer.");
                    } finally {
                        break;
                    }
                default:
                    System.out.println("Please choose again.");
                    break;
            }
        }


    }


    /**
     * Εμφανίζει μενού επιλογών στον χρήστη
     *
     * @return Επιστρέφει την επιθυμητή επιλογή του χρήστη
     * @throws InputMismatchException
     */
    static int menu() throws InputMismatchException {
        int choice = -1;

        System.out.println();
        System.out.println("Select action:");
        System.out.println("0. Exit");
        System.out.println("1. Find low & high indexes");
        System.out.println();
        System.out.print("Choice : ");
        try {
            choice = input.nextInt();
        } catch (InputMismatchException e) {
            //e.printStackTrace();
            throw e;
        } finally {
            input.nextLine();  // To consume any leftover in buffer from the previous user input for "choice"
        }
        return choice;
    }


    /**
     * Εντοπίζει το low και high index ενός αριθμού όταν βρεθεί σε έναν ταξινομημένο πίνακα
     *
     * @param arr Ο ταξινομημένος πίνακας για τον εντοπισμού των low & high indexes ενός δοσμένου αριθμού
     * @param key Ο αριθμός του οποίου τα low & high indexes ζητούνται
     * @return Επιστρέφει πίνακα με τις τιμές των low & high indexes εφόσον βρεθεί ο ζητούμενος αριθμός
     */
    static int[] getLowAndHighIndexOf(int[] arr, int key) {
        int[] lowHighIndexes = {-1, -1};
        boolean foundKey = false;

        // Γίνεται αναζήτηση στον πίνακα μέχρι να βρεθεί το στοιχείο ή μέχρι το τέλος του.
        for (int i = 0; i < arr.length; i++) {
            if (!foundKey && arr[i] == key) {
                foundKey = true;
                lowHighIndexes[0] = i;
            }
            /* Αν έχει βρεθεί το ζητούμενο στοιχείο, τότε για όσες επόμενες θέσεις συνεχίζει να εντοπίζεται ενημερώνεται
               ανάλογα ο high index
            */
            if (foundKey && arr[i] == key) {
                lowHighIndexes[1] = i;
            /* Διαφορετικά αν δεν επαναληφθεί το ζητούμενο στοιχείο σε κάποια επόμενη θέση, γίνεται διακοπή της
               αναζήτησης και επιστρέφονται οι μέχρι τώρα indexes
             */
            } else if (foundKey && arr[i] != key) {
                return lowHighIndexes;
            }
        }
        return lowHighIndexes;
    }


}

