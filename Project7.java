package gr.aueb.cf.miniprojects;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Δύο μέθοδοι που αφορούν στην αντιγραφή δισδιάστατων πινάκων.
 * Η μέθοδος int[][] shallowCopy(int[][] arr) αντιγράφει μόνο τις τιμές του βασικού πίνακα και οι οποίες ουσιαστικά
 * είναι μόνο αναφορές στους πίνακες-στοιχεία του βασικού πίνακα.
 * Η μέθοδος int[][] deepCopy(int[][] arr) αντιγράφει τα πλήρη στοιχεία του αρχικού πίνακα, όχι μόνο τις αναφορές.
 */
public class Project7 {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                System.out.println("Only choices 0, 1, or 2 are allowed. ");
                choice = -1;
            }

            int[][] arr = new int[5][10];
            //Populate arr
            arr = populateArray(arr);

            int[][] newArr = new int[5][10];

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    input.close();
                    break;
                case 1:
                    System.out.println("Shallow copy demo ");
                    newArr = shallowCopy(arr);
                    break;
                case 2:
                    System.out.println("Deep copy demo ");
                    newArr = deepCopy(arr);
                    break;
                default:
                    System.out.println("Please choose again.");
                    break;
            }

            if (choice != 0) {
                //Show arr
                System.out.printf("\t\tarr\n");
                showArray(arr);
                System.out.println();

                //Προβολή του νέου πίνακα newArr
                System.out.printf("\tnewArr\n");
                showArray(newArr);
                System.out.println();

                //Αλλαγή ενός στοιχείου του νέου πίνακα newArr και προβολή του
                System.out.println("Setting element newArr[5][10] equal to 0");
                newArr[4][9] = 0;
                System.out.printf("\nnewArr after the change of newArr[5][10]\n");
                showArray(newArr);
                System.out.println();

                //Προβολή του αρχικού πίνακα arr μετά την αλλαγή στον πίνακα newArr
                System.out.println("arr after the change in newArr");
                showArray(arr);
            }
        }
    }


    /**
     * Εμφανίζει μενού επιλογών στον χρήστη
     *
     * @return Επιστρέφει τον ακέραιο που αντιστοιχεί στην επιλογή του χρήστη
     * @throws InputMismatchException
     */
    public static int menu() throws InputMismatchException {
        int choice = -1;

        System.out.println();
        System.out.println("Select action:");
        System.out.println("0. Exit program");
        System.out.println("1. Shallow copy Demo");
        System.out.println("2. Deep copy Demo");
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
     * Γεμίζει έναν πίνακα με 1
     *
     * @param arr Ο πίνακας που θέλουμε να γεμίσει
     * @return Επιστρέφει τον γεμισμένο πίνακα
     */
    static int[][] populateArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = 1;
            }
            System.out.println();
        }
        return arr;
    }


    /**
     * Εμφανίζει τα στοιχεία ενός πίνακα
     *
     * @param arr Ο πίνακας του οποίου τα στοιχεία θέλουμε να εμφανιστούν
     */
    static void showArray(int[][] arr) {
        for (int[] row : arr) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }

    }


    /**
     * Αντιγράφει τα στοιχεία ενός πίνακα σε έναν άλλο χρησιμοποιώντας την Arrays.copyOf
     *
     * @param arr Ο πίνακας τον οποίο θέλουμε να αντιγράψουμε σε έναν άλλον
     * @return Επιστρέφει τον νέο πίνακα
     */
    static int[][] shallowCopy(int[][] arr) {
        int[][] newArr = Arrays.copyOf(arr, arr.length);
        return newArr;
    }


    /**
     * Αντιγράφει τα στοιχεία ενός πίνακα σε έναν άλλον καταχωρώντας τις αντίστοιχες τιμές στις αντίστοιχες θέσεις στον
     * νέο πίνακα
     *
     * @param arr Ο πίνακας τα στοιχεία του οποίου αντιγράφονται σε έναν άλλον
     * @return Επιστρέφει τον νέο πίνακα
     */
    static int[][] deepCopy(int[][] arr) {
        int[][] newArr = new int[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

}



