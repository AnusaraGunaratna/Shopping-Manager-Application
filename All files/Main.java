import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main (String[] Args) {
        WestminsterShoppingManager start = new WestminsterShoppingManager(); //Creating a common instance
        start.setInstance(start);
        Scanner mainInput = new Scanner(System.in);
        System.out.println("\nWELCOME !\n");
        while (true) {
            try {
                System.out.println("Select an option\n" +
                        "1) Manager: Open the Console Menu\n" +
                        "2) User: Online Shopping centre\n");
                System.out.print("Option: ");
                int mainAnswer = mainInput.nextInt();

                switch (mainAnswer) {
                    case 1:
                        start.displayMenu(); // WestminsterShoppingManager class is called from here
                        break;
                    case 2:
                        User userStart = new User(); // GUI can be accessed from here
                        userStart.setInstanceUser(start);
                        userStart.registration();
                        break;
                    default:
                        System.out.println("Invalid Input, Please Re-enter");
                        break;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a valid integer.\n");
                mainInput.nextLine();
            }
        }
    }

}

