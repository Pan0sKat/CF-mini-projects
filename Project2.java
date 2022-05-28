package gr.aueb.cf.miniprojects;


import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Δημιουργία εφαρμογής επαφών για ένα κινητό τηλέφωνο, η οποία μπορεί να
 * περιέχει μέχρι 500 επαφές. Κάθε επαφή έχει Επώνυμο, Όνομα και Τηλέφωνο.
 * Χρησιμοποιείται ένας δισδιάστατος πίνακας 500x3
 * όπου στην 1η θέση κάθε επαφής αποθηκεύεται το Επώνυμο, στη 2η θέση το Όνομα
 * και στην 3η θέση το τηλέφωνο, όλα ως String.
 * Υλοποιούνται οι βασικές CRUD πράξεις:
 * Αναζήτηση Επαφής με βάση αλφαριθμητικό που δίνει ο χρήστης,
 * Εισαγωγή Επαφής με βάση το τηλέφωνο (αν δεν υπάρχει ήδη),
 * Ενημέρωση Επαφής (εάν υπάρχει),
 * Διαγραφή Επαφής (εάν υπάρχει).
 */
public class Project2 {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;
        int nextContactIndex = 0; // Δηλώνει τη θέση του πίνακα για την προσθήκη νέας επαφής

        String[][] phoneBook = new String[500][3];

        // Αρχικοποίηση πίνακα επαφών phoneBook
        for (String[] row : phoneBook) {
            Arrays.fill(row, "");
        }

        //Εμφάνιση μενού επιλογών
        while (choice != 0) {
            try {
                choice = menu();
            } catch (InputMismatchException e1) {
                //e1.printStackTrace();
                System.out.println("Only choices 0, 1, 2, 3, 4 or 5 are allowed. ");
                choice = -1;
            }

            switch (choice) {
                case 0:
                    System.out.println("Exiting program...");
                    input.close();
                    break;
                case 1:
                    findContact(phoneBook, nextContactIndex);
                    break;
                case 2:
                    /* Αν ο πίνακας δεν είναι γεμάτος και γίνει επιτυχής προσθήκη νέας επαφής,
                       η addContact επιστρέφει την επόμενη κενή θέση του πίνακα
                     */
                    if (nextContactIndex != 500) {
                        nextContactIndex = addContact(phoneBook, nextContactIndex);
                        System.out.println();
                        System.out.println("    Contact added successfully.");
                    } else {
                        System.out.println("The phonebook is full.");
                    }
                    break;
                case 3:
                    if (nextContactIndex != 0) {
                        editContact(phoneBook, nextContactIndex);
                    } else {
                        System.out.println("The phonebook is empty.");
                    }
                    break;
                case 4:
                    /* Αν ο πίνακας δεν είναι άδειος και γίνει επιτυχής διαγραφή της επαφής,
                       η deleteContact επιστρέφει την τελευταία κενή θέση του πίνακα
                    */
                    if (nextContactIndex != 0) {
                        nextContactIndex = deleteContact(phoneBook, nextContactIndex);
                    } else {
                        System.out.println("The phonebook is empty.");
                    }
                    break;
                case 5:
                    if (nextContactIndex != 0) {
                        showPhoneBook(phoneBook, nextContactIndex);
                    } else {
                        System.out.println("The phonebook is empty.");
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
        System.out.println("0. Exit");
        System.out.println("1. Find contact");
        System.out.println("2. Add contact");
        System.out.println("3. Edit contact");
        System.out.println("4. Delete contact");
        System.out.println("5. Show phonebook");
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
     * Ελέγχει αν στα στοιχεία του πίνακα επαφών (μέχρι και τη γραμμή όπου έχει καταχωρηθεί επαφή)
     * περιέχεται το αλφαριθμητικό που δίνει ο χρήστης.
     * Η αναζήτηση μπορεί να γίνει και με μέρος του επιθυμητού Επιθέτου, Ονόματος ή Τηλεφώνου.
     *
     * @param phoneBook        Ο πίνακας που περιέχει τα στοιχεία επαφών
     * @param nextContactIndex Η θέση του πίνακα στην οποία θα προστεθεί η νέα επαφή
     */
    static void findContact(String[][] phoneBook, int nextContactIndex) {
        int counter = 0; // Για την καταμέτρηση των επαφών που βρέθηκαν τα οποία περιέχουν το ζητούμενο αλαφαριθμητικό
        String searchString = "";

        if (nextContactIndex != 0) {
            System.out.print("Please type what you would like to search the phonebook for: ");
            searchString = input.next();
            System.out.printf("%n  #\tLast name\t\t\t\tFirst name\t\t\t\tPhone number\n");
            System.out.println("-----------------------------------------------------------------");
            for (int i = 0; i < nextContactIndex; i++) {
                for (int j = 0; j < 3; j++) {
                    if (phoneBook[i][j].contains(searchString)) {
                        counter++;
                        System.out.printf("%3d\t%-20s\t%-20s\t%-10s%n", i + 1, phoneBook[i][0], phoneBook[i][1], phoneBook[i][2]);
                        j = 3; // Αν βρεθεί το ζητούμενο αλφαριθμητικό σε μια στήλη, η αναζήτηση συνεχίζεται στην επόμενη γραμμή
                    }
                }
            }
            System.out.printf("%n\t%d result%s %s found containing \"%s\".\n", counter, counter == 1 ? "" : "s", counter == 1 ? "was" : "were", searchString);
        } else {
            System.out.println("The phonebook is empty.");
        }
    }


    /**
     * Προσθέτει τα στοιχεία μιας νέας επαφής εφόσον ο τηλεφωνικός κατάλογος δεν είναι γεμάτος και δεν υπάρχει ήδη
     * καταχωρημένη η επαφή
     *
     * @param phoneBook        Ο πίνακας με τα στοιχεία επαφών
     * @param nextContactIndex Η θέση του πίνακα στην οποία θα προστεθεί η νέα επαφή
     * @return Επιστρέφει την επόμενη κενή θέση του πίνακα
     */
    static int addContact(String[][] phoneBook, int nextContactIndex) {

        System.out.print("Please enter contact's Phone Number: ");
        String phoneNumber = input.next();
        /*
        Έλεγχος αν το τηλέφωνο που δόθηκε υπάρχει ήδη στον κατάλογο, οπότε επιστέφει αμετάβλητο τον δείκτη προς την
        επόμενη κενή θέση
         */
        for (int i = 0; i < nextContactIndex; i++) {
            if (phoneBook[i][2].contains(phoneNumber)) {
                System.out.println();
                System.out.println("Contact already exists in phonebook.");
                return nextContactIndex;
            }
        }
        /*
        Αν ο αριθμός δεν υπάρχει ήδη στον κατάλογο γίνεται προσθήκη όλων των στοιχείων και επιστρέφεται αυξημένος κατά 1
        ο δείκτης για την επόμενη κενή θέση του πίνακα
        */
        phoneBook[nextContactIndex][2] = phoneNumber;
        System.out.print("Please enter contact's Last Name: ");
        phoneBook[nextContactIndex][0] = input.next();
        System.out.print("Please enter contact's First Name: ");
        phoneBook[nextContactIndex][1] = input.next();
        return ++nextContactIndex;
    }


    /**
     * Τροποποιεί τα στοιχεία μιας επιλεγμένης επαφής βάσει του αύξοντα αριθμού εγγραφής της.
     *
     * @param phoneBook        Ο πίνακας με τα στοιχεία επαφών.
     * @param nextContactIndex Δείκτης στην επόμενη κενή θέση του πίνακα για προσθήκη νέας επαφής.
     */
    static void editContact(String[][] phoneBook, int nextContactIndex) {
        int contactOrdinal = -2;
        do {
            /*
            Πληκτρολόγηση από το χρήστη:
             - είτε του αύξοντα αριθμού εγγραφής της επιθυμητής προς επεξεργασία επαφής
             - είτε -1 για επιστροφή στο μενού
             - είτε 0 για αναζήτηση του αύξοντα αριθμού εγγραφής της επιθυμητής προς επεξεργασίας επαφής
             */
            System.out.println("Please type the record # of the contact you would like to edit.");
            System.out.println("Enter 0 to search for a contact's record #.");
            System.out.println("You can return to main menu by typing -1.");
            System.out.print("Enter your choice: ");
            try {
                contactOrdinal = input.nextInt();
            } catch (InputMismatchException e) {
                //e.printStackTrace();
                contactOrdinal = -2; //επιστροφή στις επιλογές για διαγραφή επαφής
            } finally {
                input.nextLine();  // To consume any leftover in buffer from the previous user input for "choice"
            }
            //Αναζήτηση της επιθυμητής προς επεξεργασία επαφής για τον εντοπισμό του αύξοντα αριθμού εγγραφής της
            if (contactOrdinal == 0) {
                findContact(phoneBook, nextContactIndex);
                contactOrdinal = -2; //επιστροφή στις επιλογές για επεξεργασία επαφής
            }
        } while (contactOrdinal < -1 || contactOrdinal > nextContactIndex);

        /*
        Εφόσον ΔΕΝ δοθεί -1 για ακύρωση της επεξεργασίας και επιστροφή στο κυρίως μενού της εφαρμογής, γίνεται
        προσθήκη των νέων στοιχείων (addContact) στη θέση του πίνακα (contactOrdinal-1) που αντιστοιχεί στην επαφή
        που επέλεξε προς επεξεργασία ο χρήστης
        */
        if (contactOrdinal != -1) {
            addContact(phoneBook, (contactOrdinal - 1));

            System.out.println();
            System.out.println("    Contact was edited successfully.");
        }
    }


    /**
     * Διαγράφει μια επιλεγμένη επαφή βάσει του αύξοντα αριθμού εγγραφής της.
     * Η κενή θέση που δημιουργείται καλύπτεται με διαδοχικές μεταφορές των επόμενων εγγραφών ώστε η ενδεχόμενη
     * προσθήκη νέας επαφής να γίνεται στο τέλος του πίνακα στην πρώτη κενή θέση.
     *
     * @param phoneBook        Ο πίνακας με τα στοιχεία επαφών.
     * @param nextContactIndex Δείκτης στην επόμενη κενή θέση του πίνακα για προσθήκη νέας επαφής.
     * @return Επιστρέφει την πρώτη κενή θέση του πίνακα με τα στοιχεία επαφών.
     */
    static int deleteContact(String[][] phoneBook, int nextContactIndex) {
        int contactOrdinal = -2;
        do {
            /*
            Πληκτρολόγηση από το χρήστη:
             - είτε του αύξοντα αριθμού εγγραφής της επιθυμητής προς διαγραφή επαφής
             - είτε -1 για επιστροφή στο μενού
             - είτε 0 για αναζήτηση του αύξοντα αριθμού εγγραφής της επιθυμητής προς διαγραφή επαφής
             */
            System.out.println("Please type the record # of the contact you would like to edit.");
            System.out.println("Enter 0 to search for a contact's record #.");
            System.out.println("You can return to main menu by typing -1.");
            System.out.print("Enter your choice: ");
            try {
                contactOrdinal = input.nextInt();
            } catch (InputMismatchException e) {
                //e.printStackTrace();
                contactOrdinal = -2; //επιστροφή στις επιλογές για διαγραφή επαφής
            } finally {
                input.nextLine();  // To consume any leftover in buffer from the previous user input for "choice"
            }
            //Αναζήτηση της επιθυμητής προς διαγραφή επαφής για τον εντοπισμό του αύξοντα αριθμού εγγραφής της
            if (contactOrdinal == 0) {
                findContact(phoneBook, nextContactIndex);
                contactOrdinal = -2; //επιστροφή στις επιλογές για διαγραφή επαφής
            } else if (contactOrdinal == -1) {
                return nextContactIndex; //επιστέφει στο κεντρικό μενού με αμετάβλητο τον δείκτη προς την επόμενη κενή θέση
            }
        } while (contactOrdinal < -1 || contactOrdinal > nextContactIndex);

        //Εμφάνιση των στοιχείων της επαφής που διαγράφηκε
        System.out.println();
        System.out.println("Contact #" + contactOrdinal + ":  " + phoneBook[contactOrdinal - 1][0] + "  "
                + phoneBook[contactOrdinal - 1][1] + "  " + phoneBook[contactOrdinal - 1][2] + "  deleted successfully.");

        /* Διαδοχική αντιγραφή των επόμενων από τη διαγραμμένη θέση επαφών προκειμένου ο πίνακας να μην εχει ενδιάμεσες
        κενές γραμμές
         */
        for (int i = contactOrdinal - 1; i <= nextContactIndex - 2; i++) {
            for (int j = 0; j < 3; j++) {
                phoneBook[i][j] = phoneBook[i + 1][j];
            }
        }
//        //Αντικατάσταση της τελευταίας γραμμής με το κενό String
//        for (int k = 0; k < 3; k++) {
//            phoneBook[nextContactIndex - 1][k] = "";
//        }

        // Επιστρέφει την νέα κενή θέση του πίνακα
        return nextContactIndex - 1;
    }


    /**
     * Εμφανίζει όλα τα στοιχεία του τηλεφωνικού καταλόγου εφόσον δεν είναι κενός
     *
     * @param phoneBook        Ο πίνακας με τα στοιχεία επαφών
     * @param nextContactIndex Η θέση του πίνακα στην οποία θα προστεθεί η νέα επαφή
     */
    static void showPhoneBook(String[][] phoneBook, int nextContactIndex) {
        System.out.printf("%n  #\tLast name\t\t\t\tFirst name\t\t\t\tPhone number\n");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < nextContactIndex; i++) {
            System.out.printf("%3d\t%-20s\t%-20s\t%-10s%n", i + 1, phoneBook[i][0], phoneBook[i][1], phoneBook[i][2]);
        }
        System.out.printf("%n\tThe phonebook contains %d contact%s.\n", nextContactIndex, nextContactIndex == 1 ? "" : "s");
    }

}
