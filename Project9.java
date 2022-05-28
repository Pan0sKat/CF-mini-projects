package gr.aueb.cf.miniprojects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Πρόγραμμα κρυπτογράφησης-αποκρυπτογράφησης:
 * <p>
 * ο 1ος χαρακτήρας κωδικοποιείται με την ακέραια τιμή ASCII που του αντιστοιχεί
 * Οι επόμενοι χαρακτήρες του μηνύματος κωδικοποιούνται προσθέτοντας την ακέραια τιμή ASCII του καθενός με τον κωδικό του προηγούμενού του και παίρνοντας το υπόλοιπο της διαίρεσης του αθροίσματος αυτού με ένα σταθερό κλειδί κρυπτογράφησης.
 * Τα μηνύματα τελειώνουν με τον χαρακτήρα # ενώ το αντίστοιχο κρυπτογραφημένο είναι μια ακολουθία χαρακτήρων που τελειώνει σε -1.
 * <p>
 * Η αποκρυπτογράφηση λαμβάνει ως είσοδο μια ακολουθία ακεραίων που τελειώνει σε -1 και επιστρέφει το αρχικό μήνυμα.
 *
 * @author Panos Katopis
 */
public class Project9 {
    static Scanner sc = new Scanner(System.in);
    final static int KEY = 1031; // Δήλωση του κλειδιού απο/κρυπτογράφησης ως σταθερά

    public static void main(String[] args) {
        int choice = -1;
        String plainText = new String();
        String cipherText = new String();


        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                System.out.println("Only choices 0, 1 or 2 are allowed. ");
                choice = -1;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    sc.close();
                    break;
                case 1:
                    cipherText = encrypt();
                    System.out.println("The encrypted message is : " + cipherText);
                    printToFile(cipherText, "./cipher-file.txt");
                    break;
                case 2:
                    plainText = decrypt();
                    if (!plainText.isEmpty()) {
                        printToFile(plainText, "./plain-file.txt");
                    }
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
        System.out.println("0. Exit program");
        System.out.println("1. Encrypt text");
        System.out.println("2. Decrypt text");
        System.out.println();
        System.out.print("Choice : ");
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            //e.printStackTrace();
            throw e;
        } finally {
            sc.nextLine();  // To consume any leftover in buffer from the previous user input for "choice"
        }
        return choice;
    }


    /**
     * Διαβάζει ένα String (συμπεριλαμβανομένων white spaces) που πληκτρολογεί ο χρήστης
     * και το εκτυπώνει κρυπτογραφημένο με βάσει ένα δεδομένο κλειδί (1031)
     *
     * @return Επιστρέφει String με το κρυπτογραφημένο κείμενο
     */
    public static String encrypt() {
        String plainText = new String();
        StringBuilder cipherText = new StringBuilder();

        do {
            System.out.println("Please enter message to be encrypted.");
            System.out.println("Type # to mark the end of the message.");

            plainText = sc.nextLine();
        } while (!plainText.contains("#"));

        int previousCode = -1;
        if ((plainText.charAt(0) == '#')) {
            cipherText.append("-1");
        } else {
            previousCode = (int) plainText.charAt(0);
            cipherText.append(String.valueOf(previousCode)).append(" ");

            for (int j = 1; j < plainText.length(); j++) {
                if (plainText.charAt(j) == '#') {
                    cipherText.append("-1");
                    break;
                } else {
                    cipherText.append(((int) plainText.charAt(j) + previousCode) % KEY).append(" ");
                    previousCode = ((int) plainText.charAt(j) + previousCode) % KEY;
                }
            }
        }
        return cipherText.toString();
    }


    /**
     * Δημιουργεί ένα αρχείο στο οποίο εκτυπώνει τα απο/κρυπτογραφημένα κείμενα
     *
     * @param messageText Το κείμενο για εγγραφή στο αρχείο
     * @param filePath    Η διαδρομή και το όνομα του αρχείου για την εγγραφή
     */
    public static void printToFile(String messageText, String filePath) {
        try {
            File cipherFile = new File(filePath);
            if (cipherFile.createNewFile()) {
                System.out.println("File created: " + cipherFile.getName());
            } else {
                System.out.println("File " + filePath + ", already exists, it will be overwritten.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try (PrintStream ps = new PrintStream(filePath)) {
            ps.println(messageText);
            System.out.println("Message written to " + filePath + ".");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            System.out.println("Output file not found.");
        }
    }


    /**
     * Διαβάζει μια ακολουθία ακεραίων που τελειώνει σε -1 και την αποκρυπτογραφεί στο αρχικό μήνυμα
     *
     * @return Επιστρέφει το αποκρυπτογραφημένο μήνυμα
     */
    public static String decrypt() {
        String token;
        int number, previousNumber;
        StringBuilder plainText = new StringBuilder();

        System.out.println("Please enter sequence of integers to be decrypted.");
        System.out.println("Message should end with -1.");

        // Αποκρυπτογράφηση του 1ου στοιχείου της ακολουθίας ακεραίων
        try {
            number = sc.nextInt();
            if (number == -1) {
                plainText.append("#");
                System.out.println("The decrypted message is : " + plainText);
                return plainText.toString();
            } else {
                plainText.append((char) number);
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
            System.out.println("Invalid data in encrypted sequence. Aborting...");
            return null;
        }
        // Αποκρυπτογράφηση των υπόλοιπων ακεραίων της ακολουθίας ακεραίων μέχρι να βρεθεί -1
        do {
            try {
                previousNumber = number;
                number = sc.nextInt();
                int diff = number - previousNumber;

                if (number == -1) {
                    plainText.append('#');
                } else if (diff < 0) {
                    plainText.append((char) (KEY + diff));
                } else {
                    plainText.append((char) (diff));
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                e.printStackTrace();
                System.out.println("Invalid data in encrypted sequence. Aborting...");
                return "";
            }
        } while (number != -1);

        System.out.println("The decrypted message is : " + plainText);

        return plainText.toString();
    }

}


