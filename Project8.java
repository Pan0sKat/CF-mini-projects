package gr.aueb.cf.miniprojects;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Ανάπτυξη του παιχνιδιού τρίλιζα
 */
public class Project8 {
    static Scanner input = new Scanner(System.in);
    static char[][] gameTable = new char[3][3];


    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                System.out.println("Only choices 0 or 1 are allowed. ");
                choice = -1;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    input.close();
                    break;
                case 1:
                    playGame();
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
     * @return Επιστρέφει την επιθυμητή επιλογή του χρήστη
     * @throws InputMismatchException
     */
    static int menu() throws InputMismatchException {
        int choice = -1;

        System.out.println();
        System.out.println("Select action:");
        System.out.println("0. Exit");
        System.out.println("1. Play Game");
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
     * Έναρξη παιχνιδιού
     */
    static void playGame() {
        int turn = 1;
        boolean isWinner = false;

        // Αρχικοποίηση του πίνακα τρίλιζας
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                gameTable[i][j] = ' ';
            }
        }
        // Εμφάνιση τρίλιζας
        showTable(gameTable);
        do {
            //Καθορισμός παίχτη που έχει σειρά
            player(turn % 2);
            showTable(gameTable);
            /*Έλεγχος αν υπάρχει νικητής μετά τον 5ο γύρο αφού χρειάζονται να έχουν ήδη τοποθετηθεί 3 σύμβολα από τον
              πρώτο παίχτη και άρα 2 από τον δεύτερο. */
            if (turn >= 5) {
                if (isWinner = checkTriplet()) {
                    System.out.printf("\t\tCONGRADULATIONS PLAYER %d ! ! ! \n\t\t\tYOU HAVE WON ! ! !\n", turn % 2 == 0 ? 2 : 1);
                }
            }
            turn++;
        } while (turn <= 9 && !isWinner);

        if (!isWinner) {
            System.out.printf("\t\tDRAW!!! NO PLAYER WINS!\n");
        }
    }


    /**
     * Εμφανίζει την τρίλιζα με τα σύμβολα που έχουν τοποθετηθεί από τους παίχτες
     *
     * @param gameTable Ο char array όπου αποθηκεύονται οι κινήσεις των παικτών
     */
    public static void showTable(char[][] gameTable) {
        System.out.printf("\n\n\t\t\t[X]: Player 1\n\t\t\t[O]: Player 2\n\n");
        System.out.printf("\t\t   || 1 | 2 | 3 |\n");
        System.out.printf("\t\t===||===|===|===|\n");
        for (int i = 1; i <= 3; i++) {
            System.out.printf("\t\t%2d ||", i);
            for (int j = 1; j <= 3; j++) {
                System.out.printf("%2s |", gameTable[i - 1][j - 1]);
            }
            System.out.printf("\n\t\t---||---|---|---|\n");
        }
        System.out.println();
    }


    /**
     * Μέθοδος που ενημερώνει ποιος παίχτης έχει σειρά και με ποιο σύμβολο για το οποίο καλείται η μέθοδος
     * εισαγωγής της θέσης τοποθέτησής του.
     *
     * @param number Ο παίχτης που έχει σειρά να παίξει (τιμές: 1 ή 2)
     */
    static void player(int number) {
        char symbol = ' ';

        switch (number) {
            case 1:
                symbol = 'X';
                System.out.println("\t\tPLAYER 1  [symbol: X]");
                break;
            case 0:
                symbol = 'O';
                System.out.println("\t\tPLAYER 2  [symbol: O]");
                break;
            default:
                break;
        }
        selectPosition(symbol);
    }


    /**
     * Μέθοδος για την εισαγωγή της θέσης τοποθέτησης του αντίστοιχου συμβόλου του από τον εκάστοτε παίχτη που έχει
     * σειρά.
     *
     * @param symbol Το σύμβολο που θα τοποθετηθεί στην τρίλιζα αναλόγως με το ποιος παίχτης έχει σειρά να παίξει.
     */
    static void selectPosition(char symbol) {
        int row = 0, col = 0;
        boolean isAlreadyTaken = true;

        do {
            try {
                /* Έλεγχος αν οι συντεταγμένες της θέσης που δηλώνεται είναι έγκυρες */
                do {
                    System.out.println("Please enter position (1<=row<=3 1<=column<=3). ");
                    System.out.printf("\t\tRow: ");
                    row = input.nextInt();
                    System.out.printf("\t\tColumn: ");
                    col = input.nextInt();
                } while (row < 1 || row > 3 || col < 1 || col > 3);

                /* Έλεγχος αν η θέση που δηλώθηκε είναι ήδη κατειλημμένη */
                if (gameTable[row - 1][col - 1] == ' ') {
                    isAlreadyTaken = false;
                    gameTable[row - 1][col - 1] = symbol;
                } else {
                    System.out.printf("Position (%d,%d) is already taken. Please try again.\n", row, col);
                }
            } catch (InputMismatchException e) {
                //e.printStackTrace();
                input.nextLine();
                System.out.println("Only numbers 1, 2 and 3 are allowed for the position. Please try again.");
            }
        } while (isAlreadyTaken);
    }


    /**
     * Μέθοδος που ελέγχει σε κάθε γύρο αν έχει σχηματιστεί τρίλιζα
     *
     * @return Επιστρέφει true ή false αντιστοίχως
     */
    static boolean checkTriplet() {
        boolean isHorizontal = false, isVertical = false, isDiagonal = false;

        for (int i = 0; i <= 2; i++) {
            /* Αν σε κάθε επανάληψη η θέση [i][i] είναι κενή, τότε δεν χρειάζεται καν να ελέγξουμε αν έχει σχηματιστεί
            τρίλιζα στην i-οστή γραμμή και στην i-οστή στήλη. */
            if (gameTable[i][i] != ' ') {
                if (gameTable[i][0] == gameTable[i][1] && gameTable[i][1] == gameTable[i][2]) {
                    isHorizontal = true;
                }
                if (gameTable[0][i] == gameTable[1][i] && gameTable[1][i] == gameTable[2][i]) {
                    isVertical = true;
                }
            }
        }
        /* Αν η κεντρική θέση [1][1] είναι κενή, τότε δεν χρειάζεται καν να ελέγξουμε αν έχει σχηματιστεί
            τρίλιζα στις διαγωνίους. */
        if (gameTable[1][1] != ' ' && (gameTable[0][0] == gameTable[1][1] && gameTable[1][1] == gameTable[2][2]) || (gameTable[0][2] == gameTable[1][1] && gameTable[1][1] == gameTable[2][0])) {
            isDiagonal = true;
        }
        return (isHorizontal || isVertical || isDiagonal);
    }
}



