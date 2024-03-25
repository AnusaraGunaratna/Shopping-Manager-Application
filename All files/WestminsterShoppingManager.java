import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    //
    private HashMap<String,Product> productList = new HashMap<String,Product>();    //Main hashmap of the stored products
    private ArrayList<String> electronicsList = new ArrayList<>();  //All the electronic products
    private ArrayList<String> clothingList = new ArrayList<>();     //All the Clothing products
    private ArrayList<String> productsSortList = new ArrayList<>();
    //
    static boolean toContinue = true;
    static boolean toContinue2 = true;
    static boolean toContinue3 = true;

    private WestminsterShoppingManager instance;

    public HashMap<String, Product> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Product> productList) {
        this.productList = productList;
    }

    public ArrayList<String> getElectrnoicsList() {
        return electronicsList;
    }

    public void setElectrnoicsList(ArrayList<String> electrnoicsList) {
        this.electronicsList = electrnoicsList;
    }

    public ArrayList<String> getClothingList() {
        return clothingList;
    }

    public void setClothingList(ArrayList<String> clothingList) {
        this.clothingList = clothingList;
    }

    public ArrayList<String> getProductsSortList() {
        return productsSortList;
    }

    public void setProductsSortList(ArrayList<String> productsSortList) {
        this.productsSortList = productsSortList;
    }
    public void setInstance(WestminsterShoppingManager instance){
        this.instance = instance;
        loadProducts();
    }
    public WestminsterShoppingManager getInstance(){
        return instance;
    }

    public void displayMenu(){
        Scanner input1 = new Scanner(System.in);
        int optionInput1;

        while (toContinue2) {
            System.out.println(
                    "\n1: Add a new product\n" +
                            "2: Delete a product\n" +
                            "3: Print the list of products\n" +
                            "4: Save in a file\n" +
                            "5: Cancel\n");
            System.out.print("Enter the option number:");

            if (input1.hasNextInt()) {
                optionInput1 = input1.nextInt();

                switch (optionInput1) {
                    case 1:
                        if (productsLimit()){
                            continue;
                        }else{
                            addProduct();
                        }
                        break;
                    case 2:
                        toContinue3 = true;
                        deleteProduct();
                        toContinue = false;
                        continue;
                    case 3:
                        printProducts();
                        break;
                    case 4:
                        saveProducts();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please enter a valid option.");
                        continue;
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer: ");
                input1.next();
            }
        }
    }
    @Override
    public void addProduct() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("\nSelect option:\n" +
                "1: Add Electronic item\n" +
                "2: Add Clothing item");
        System.out.print("Option: ");
        int optionInput2;

        optionInput2 = validateOptionInput(input2, new int[]{1, 2});

        String prefix = null;

        switch (optionInput2) {
            case 1:
                System.out.print("\nEnter the product ID: A");
                prefix = "A";
                break;
            case 2:
                System.out.print("\nEnter the product ID: B");
                prefix = "B";
                break;
            default:
                System.out.println("Invalid option. Please enter a valid option.");
                return;
        }

        String tempID = input2.next();
        String productID = prefix + tempID;

        if (productList.containsKey(productID)){
            System.out.print("\nThis product ID exists. Do you replace the information of the product?\n" +
                    "1: Yes\n" +
                    "2: No, add different product\n" +
                    "3: Cancel\n" +
                    ">>");
            int y_nAnswer = validateOptionInput(input2, new int[]{1, 2, 3});

            switch (y_nAnswer){
                case 1:
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    System.out.println();
                    displayMenu();
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        }

        System.out.print("\nEnter the product name:");
        String productName = input2.next();
        productName += input2.nextLine();

        System.out.print("\nEnter the number of available items:");
        int quantity = validateIntInput(input2);

        System.out.print("\nEnter the price:");
        double price = validateDoubleInput(input2);
        toContinue = true;

        if (optionInput2 == 1) {
            System.out.print("\nEnter the brand name:");
            String brand = input2.next();
            System.out.print("\nEnter the warranty period (number of weeks):");
            int warranty = validateIntInput(input2);
            Electronics newElectronic = new Electronics(productID, productName, quantity, price, brand, warranty);
            productList.put(productID,newElectronic);
            electronicsList.add(productID);

            System.out.print("Product Successfully Added!\n");
            displayMenu();
        } else {
            ClothingSize size;

            while (true) {
                System.out.print("\nEnter the size (S, M, L, XL):");
                String sizeInput  = input2.next().toUpperCase();

                try {
                    size = ClothingSize.valueOf(sizeInput);
                    break; // Exit the loop if the input is a valid size
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid size. Please enter a valid size.");
                }
            }


            System.out.print("\nEnter the colour:");
            String colour = input2.next();
            Clothing newClothing = new Clothing(productID,productName,quantity,price,size,colour);
            productList.put(productID,newClothing);
            clothingList.add(productID);
            System.out.print("Product Successfully Added!\n");
            displayMenu();
        }
    }

    @Override
    public void deleteProduct() {
        if (toContinue3){
            while(true){
                Scanner input3 = new Scanner(System.in);
                System.out.print("\nEnter the product ID: ");
                String productID = input3.next();
                if(productList.containsKey(productID)){
                    System.out.println("\n** "+productID +" product is deleted. **");
                    deleteProductInfo(productID);
                    break;
                }else {
                    System.out.println("\nProduct ID not found!\n");
                    System.out.println("Select an option:\n" +
                            "1: Re-enter product ID\n" +
                            "2: Cancel");
                    System.out.print(">>");
                    int deleteAnswer;

                    while (true) {
                        deleteAnswer = validateIntInput(input3);
                        if (deleteAnswer == 1 || deleteAnswer == 2) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a valid option.");
                        }
                    }

                    if (deleteAnswer == 1) {
                        continue;
                    } else if (deleteAnswer == 2) {
                        break;
                    }
                }
                toContinue3 = true;
                break;
            }
        }
    }

    public void deleteProductInfo(String productID){
        //System.out.println(productID);
        if(electronicsList.contains(productID)){
            System.out.println("Category: Electronics");
        }else if(clothingList.contains(productID)){
            System.out.println("Category: Clothing");
        }
        System.out.println("Product Name: "+productList.get(productID).getProductName());
        System.out.println("Total number of products left: "+(productList.size()-1));
        productList.remove(productID);
    }



    @Override
    public void printProducts() {
        productsSortList.clear();

        for (Map.Entry<String,Product> i : productList.entrySet()){
            productsSortList.add(i.getKey());
        }
        Collections.sort(productsSortList); //Sorting the array list

        System.out.println("");
        for (String entry : productsSortList){
            Product products = productList.get(entry);
            if (electronicsList.contains(entry)){
                Electronics electronicProduct = (Electronics) products; //Type casting
                System.out.println("Product ID: " + entry);
                System.out.println("Category: Electronics");
                System.out.println("Product Name: "+productList.get(entry).getProductName());
                System.out.println("Quantity: "+productList.get(entry).getQuantityLeft());
                System.out.println("Price: "+productList.get(entry).getPrice());
                System.out.println("Brand name: "+electronicProduct.getBrand());
                System.out.println("Warranty period: "+electronicProduct.getWarrantyPeriod() + " months");
                System.out.println("\n");
                //System.out.println(electronicProduct.getBrand());
            }else if (clothingList.contains(entry)){
                Clothing clothingProduct = (Clothing) products;
                System.out.println("Product ID: " + entry);
                System.out.println("Category: Clothing");
                System.out.println("Product Name: "+productList.get(entry).getProductName());
                System.out.println("Quantity: "+productList.get(entry).getQuantityLeft());
                System.out.println("Price: "+productList.get(entry).getPrice());
                System.out.println("Size: "+clothingProduct.getSize());
                System.out.println("Colour: "+clothingProduct.getColour());
                System.out.println("\n");
            }
        }
        displayMenu();
    }

    @Override
    public void saveProducts() {

        try{
            FileOutputStream fileInput = new FileOutputStream("Products List.txt");
            ObjectOutputStream os = new ObjectOutputStream(fileInput);
            for (Product p : productList.values()){
                os.writeObject(p);
            }
            fileInput.close();
            os.close();
            System.out.println("All the available products were saved.");
        }catch(IOException e){
            System.out.println("Error while loading the file!");
        }
        displayMenu();
    }

    @Override
    public void loadProducts() {

        try{
            FileInputStream fileInput = new FileInputStream("Products List.txt");
            ObjectInputStream os = new ObjectInputStream(fileInput);
            while (true){
                try{
                    Product entry = (Product) os.readObject();
                    productList.put(entry.getProductID(), entry);
                    if (entry.getProductID().substring(0,1).equals("A")){
                        //System.out.println("Electronic");
                        electronicsList.add(entry.getProductID());

                    }
                    else if (entry.getProductID().substring(0,1).equals("B")){
                        //System.out.println("CLothing");
                        clothingList.add(entry.getProductID());
                    }

                } catch (EOFException e){
                    break;
                }
            }
            System.out.println("The File under the name 'Products List' was loaded.");
            fileInput.close();
            os.close();

        }catch(IOException| ClassNotFoundException e){
            ///e.printStackTrace();
            System.out.println("No saved files were found, products list is empty!");
        }
    }



    public boolean productsLimit(){
        boolean continue4 = false;
        if (productList.size()>49){
            System.out.println("The products limit has been reached, no more products can be added.\n");
            continue4 = true;
        }
        return continue4;
    }

    private int validateOptionInput(Scanner scanner, int[] validOptions) {
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                for (int option : validOptions) {
                    if (input == option) {
                        return input;
                    }
                }
                System.out.println("Invalid option. Please enter a valid option.");
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    private static int validateIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double validateDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }

}

