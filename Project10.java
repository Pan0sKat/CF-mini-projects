package gr.aueb.cf.miniprojects;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Πρόγραμμα διαχείρισης κρατήσεων θέσεων θεάτρου με 30 σειρές και 12 στήλες.
 *
 * @author P. Katopis
 */
public class Project10 {
    static Scanner input = new Scanner(System.in);
    static boolean[][] mapSeats = new boolean[30][12];


    public static void main(String[] args) throws IOException {
        int choice = -1;
        int[] selectedSeat = new int[2]; // index 0: Selected seat column, index 1: Selected seat row

        // Αρχικοποίηση του πίνακα θέσεων mapSeats με τιμή false
        for (boolean[] row : mapSeats) {
            for (boolean reservedSeat : row) {
                reservedSeat = false;
            }
        }

        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                System.out.println("Only choices 0, 1, 2 or 3 are allowed. ");
                choice = -1;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    input.close();
                    break;
                case 1:
                    System.out.println("Please choose seat to book. ");
                    selectedSeat = parseSeat();
                    book((char) selectedSeat[0], selectedSeat[1]);
                    break;
                case 2:
                    System.out.println("Please choose seat to cancel. ");
                    selectedSeat = parseSeat();
                    cancel((char) selectedSeat[0], selectedSeat[1]);
                    break;
                case 3:
                    showMap(mapSeats);
                    break;
                default:
                    System.out.println("Please choose again.");
                    break;
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
        System.out.println("1. Book a seat");
        System.out.println("2. Cancel a reserved seat");
        System.out.println("3. Show map of seats");
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
     * Μετατρέπει το String που εισάγει ο χρήστης σε συντεταγμένες θέσης του θεάτρου
     *
     * @return Επιστρέφει πίνακα με τις συντεταγμένες της επιλεγμένης θέσης του χρήστη (index 0: Selected seat column, index 1: Selected seat row)
     * @throws IOException
     */
    static int[] parseSeat() throws IOException {
        int[] selectedSeat = {-1, -1};

        do {
            System.out.println("Enter column letter (A - L): ");
            selectedSeat[0] = input.next().charAt(0);
            if ((char) selectedSeat[0] >= 'a' && (char) selectedSeat[0] <= 'l') {
                selectedSeat[0] -= 32; // Μετατροπή έγκυρης στήλης σε κεφαλαία
            }
        } while ((char) selectedSeat[0] < 'A' || ((char) selectedSeat[0] > 'L'));
        System.out.println("Column " + (char) selectedSeat[0] + " is selected.");

        do {
            System.out.println("Enter row number (1 - 30): ");
            try {
                selectedSeat[1] = input.nextInt();
            } catch (InputMismatchException e) {
                //e.printStackTrace();
                System.out.println("Row number should be between 1 and 30.");
            } finally {
                input.nextLine();  // To consume any leftover in buffer from the previous user input for "choice"
            }
        } while ((selectedSeat[1] < 1) || (selectedSeat[1] > 30));

        System.out.println("You have chosen seat " + (char) selectedSeat[0] + selectedSeat[1]);

        return selectedSeat;

    }

    /**
     * Κάνει book μία θέση αν δεν είναι ήδη booked.
     *
     * @param column Η στήλη της επιλεγμένης από το χρήστη θέση
     * @param row    Η γραμμή της επιλεγμένης από το χρήστη θέση
     */
    public static void book(char column, int row) {
        if (mapSeats[row - 1][(int) column - 64 - 1]) {
            System.out.println("Seat " + column + row + " is already reserved.");
            System.out.println("Please choose another seat.");
        } else {
            mapSeats[row - 1][(int) column - 64 - 1] = true;
            System.out.println("Seat " + column + row + " successfully reserved.");
        }
    }

    /**
     * Ακυρώνει την κράτηση μία θέσης αν είναι ήδη booked.
     *
     * @param column Η στήλη της επιλεγμένης από το χρήστη θέση
     * @param row    Η γραμμή της επιλεγμένης από το χρήστη θέση
     */
    public static void cancel(char column, int row) {
        if (mapSeats[row - 1][(int) column - 64 - 1]) {
            mapSeats[row - 1][(int) column - 64 - 1] = false;
            System.out.println("Seat " + column + row + " successfully canceled.");
        } else {
            System.out.println("Seat " + column + row + " is already available.");
            System.out.println("Please choose another seat.");
        }
    }

    /**
     * Εμφανίζει τον χάρτη με τις διαθέσιμες και δεσμευμένες θέσεις του θεάτρου
     *
     * @param mapSeats Ο boolean array όπου αποθηκεύονται οι κρατήσεις θέσεων
     */
    public static void showMap(boolean[][] mapSeats) {
        System.out.printf("\n\n[x]: reserved seat\n[ ]: available seat\n\n");
        System.out.println("   | A | B | C | D | E | F | G | H | I | J | K | L |");
        System.out.println("___|___|___|___|___|___|___|___|___|___|___|___|___|");
        for (int i = 1; i <= 30; i++) {
            System.out.printf("%2d |", i);
            for (int j = 1; j <= 12; j++) {
                System.out.printf("%s|", mapSeats[i - 1][j - 1] ? " x " : "   ");
            }
            System.out.printf("\n___|___|___|___|___|___|___|___|___|___|___|___|___|\n");
        }
    }
}


