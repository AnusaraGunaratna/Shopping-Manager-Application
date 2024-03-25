import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private HashMap<String, String> userData = new HashMap<>();
    public static boolean preRegister = false;
    private WestminsterShoppingManager userInstance;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInstanceUser(WestminsterShoppingManager userInstance){
        this.userInstance = userInstance;
    }

    public void registration() {
        loadUsers();
        while (true) {
            Scanner regInput1 = null;
            try {
                regInput1 = new Scanner(System.in);
                System.out.print("\nSelect and enter the option\n" + "1) New User\n" + "2) Login\n" + "Option :");
                int option1 = regInput1.nextInt();
                if (option1 == 1) {
                    newReg();
                    break;
                } else if (option1 == 2) {
                    logIn();
                    break;
                } else {
                    System.out.println("Invalid Input, please re-enter");
                }
            } catch (Exception e) {
                System.out.println("Invalid Input, please re-enter");
                regInput1.nextLine();
            }
        }
    }

    public void newReg() {
        while (true) {
            Scanner newRegInput = new Scanner(System.in);
            System.out.print("Enter an username: ");
            String un = newRegInput.nextLine();
            if (userData.containsKey(un)) {
                System.out.println("That username is taken, Please enter another one.");
            } else {
                System.out.print("Enter Password: ");
                String pw = newRegInput.nextLine();
                System.out.println("New User saved");
                addUsers(un, pw);
                username=un;
                new GUI(userInstance);
                break;
            }
        }
    }

    public void logIn() {
        Scanner logInInput = new Scanner(System.in);
        while (true) {
            System.out.print("Enter an username: ");
            String un = logInInput.nextLine();
            if (userData.containsKey(un)) {
                while (true) {
                    System.out.print("Enter Password: ");
                    String pw = logInInput.nextLine();

                    if (pw.equals(userData.get(un))) {
                        System.out.println("Login successful!\n");
                        preRegister=true;
                        username = un;
                        new GUI(userInstance);
                        break;
                    } else {
                        System.out.println("Invalid password, Re-enter");
                    }
                }
            } else {
                System.out.println("Invalid username, Re-enter");
                continue;
            }
            break;
        }
    }

    public void addUsers(String mapUsername, String mapPassword) {
        userData.put(mapUsername, mapPassword);
        saveUsers();
    }

    public void saveUsers() {
        try {
            File userFile = new File("UserData.txt");
            BufferedWriter appendData = new BufferedWriter(new FileWriter(userFile));

            for (Map.Entry<String, String> entry : userData.entrySet()) {
                appendData.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }

            appendData.flush();
            appendData.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsers() {
        try (Scanner loadData = new Scanner(new File("UserData.txt"))) {
            while (loadData.hasNextLine()) {
                String line = loadData.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username2 = parts[0];
                    String password2 = parts[1];
                    userData.put(username2, password2);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous user records");
        }
    }
}
