import java.io.*;
import java.util.*;
public class WestminsterShoppingManager implements ShoppingManager{
    private static final int MAX_PRODUCTS = 50;
    private static final String FILE_NAME = "products.txt";
    protected List<Product> productList = new ArrayList();
    private final Scanner scanner;
    private ShoppingCart shoppingCart;

    public WestminsterShoppingManager() {
        this.scanner = new Scanner(System.in);
        this.shoppingCart = new ShoppingCart();
        this.loadProductsFromFile();
    }

    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    public void addProduct(Product product) {
        if (this.productList.size() < 50) {
            this.productList.add(product);
            System.out.println("Product added successfully! " + product.getProductName());
        } else {
            System.out.println("Cannot add more products. Maximum limit reached.");
        }

    }

    public void removeProduct(Product product) {
        if (this.productList.remove(product)) {
            System.out.println("Product removed: " + product.getProductName());
            System.out.println("Total number of products left: " + this.productList.size());
        } else {
            System.out.println("Product not found: " + product.getProductName());
        }

    }

    public List<Product> getAllProducts() {
        return new ArrayList(this.productList);
    }

    private void displayGUI() {
        new MyFrame(this);
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("-----------------------------------------");
            System.out.println("          Shopping System Menu          ");
            System.out.println("-----------------------------------------");
            System.out.println();
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save to file");
            System.out.println("5. Open Shopping Cart GUI");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = this.scanner.nextInt();
            this.scanner.nextLine();
                if (choice == 0){
                    this.saveProductsToFile();
                    System.out.println("Exiting...");
                    break;
                    }
                else if (choice == 1) {
                    this.addNewProduct();
                    break;
                    }
                else if (choice == 2) {
                    this.deleteProduct();
                    break;
                    }
                else if (choice == 3) {
                    this.printProductList();
                    break;
                    }
                else if (choice == 4) {
                    this.saveProductsToFile();
                    break;
                    }
                else if (choice == 5) {
                    this.displayGUI();
                    break;
                    }
                else {
                    System.out.println("Invalid choice. Please try again.");
                }
        } while(choice != 0);

        this.scanner.close();
    }

    private void addNewProduct() {
        boolean validProductType = false;

        do {
            try {
                System.out.println("Enter product type (Clothing/Electronics) : ");
                String productType = this.scanner.next();
                if (!productType.equalsIgnoreCase("clothing") && !productType.equalsIgnoreCase("electronics")) {
                    System.err.println("Please enter a valid product type!");
                } else {
                    validProductType = true;
                    System.out.println("Enter the product id: ");
                    String productID = this.scanner.next();
                    System.out.println("Enter the product name: ");
                    String name = this.scanner.next();

                    int availableItem;
                    while(true) {
                        try {
                            System.out.println("Enter the available items: ");
                            availableItem = this.scanner.nextInt();
                            break;
                        } catch (InputMismatchException var16) {
                            System.err.println("Invalid input. Please enter a valid integer for available items.");
                            this.scanner.nextLine();
                        }
                    }

                    double price;
                    while(true) {
                        try {
                            System.out.println("Enter price: ");
                            price = this.scanner.nextDouble();
                            break;
                        } catch (InputMismatchException var15) {
                            System.err.println("Invalid input. Please enter a valid price.");
                            this.scanner.nextLine();
                        }
                    }

                    if (productType.equalsIgnoreCase("clothing")) {
                        String[] validSizes = new String[]{"xs", "s", "m", "l", "xl", "xxl"};

                        while(true) {
                            System.out.println("Enter size (xs, s, m, l, xl, xxl): ");
                            String size = this.scanner.next().toLowerCase();
                            if (Arrays.asList(validSizes).contains(size)) {
                                String color = "";
                                boolean validColor = false;

                                while(!validColor) {
                                    try {
                                        System.out.println("Enter color: ");
                                        String input = this.scanner.next();
                                        if (input.matches("^[a-zA-Z]*$")) {
                                            color = input;
                                            validColor = true;
                                        } else {
                                            System.err.println("Invalid input. Please enter a valid color.");
                                        }
                                    } catch (InputMismatchException var13) {
                                        System.err.println("Invalid input. Please enter a valid color.");
                                        this.scanner.nextLine();
                                    }
                                }

                                Product product = new Clothing(productID, name, availableItem, price, size, color);
                                this.addProduct(product);
                                break;
                            }

                            System.err.println("Please enter a valid size.");
                        }
                    } else if (productType.equalsIgnoreCase("electronics")) {
                        System.out.println("Enter the brand here : ");
                        String brand = this.scanner.next();

                        Double time;
                        while(true) {
                            try {
                                System.out.println("Enter the warranty period (Years): ");
                                time = this.scanner.nextDouble();
                                break;
                            } catch (InputMismatchException var14) {
                                System.err.println("Please enter a valid integer for the warranty period.");
                                this.scanner.nextLine();
                            }
                        }

                        Product product = new Electronics(productID, name, availableItem, price, brand, time);
                        this.addProduct(product);
                    }
                }
            } catch (InputMismatchException var17) {
                System.err.println("Invalid input. Please enter the correct data type.");
                this.scanner.nextLine();
            }
        } while(!validProductType);

    }

    private void deleteProduct() {
        try {
            System.out.println("Enter the product ID to delete: ");
            String productIdToDelete = this.scanner.next();
            Product productToDelete = this.findProductById(productIdToDelete);
            if (productToDelete != null) {
                this.removeProduct(productToDelete);
            } else {
                System.out.println("Product not found with ID: " + productIdToDelete);
            }
        } catch (InputMismatchException var3) {
            System.err.println("Invalid input. Please enter the correct data type.");
            this.scanner.nextLine();
        }

    }

    private void printProductList() {
        System.out.println("Product List:");

        try {
            Iterator var1 = this.getAllProducts().iterator();

            while(var1.hasNext()) {
                Product product = (Product)var1.next();
                PrintStream var10000 = System.out;
                String var10001 = product.getProductId();
                var10000.println("ID: " + var10001 + ", Name: " + product.getProductName() + ", Available Items: " + product.getAvailableItems() + ", Price: " + product.getPrice());
            }
        } catch (Exception var3) {
            System.err.println("An error occurred while processing the product list: " + var3.getMessage());
        }

    }

    private void saveProductsToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.txt"));

            try {
                oos.writeObject(this.productList);
                System.out.println("Products saved to file successfully.");
            } catch (Throwable var5) {
                try {
                    oos.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            oos.close();
        } catch (IOException var6) {
            System.out.println("Error saving products to file: " + var6.getMessage());
        }

    }

    private void loadProductsFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"));

            try {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    this.productList = (List)obj;
                    System.out.println("Products loaded from file.");
                }
            } catch (Throwable var5) {
                try {
                    ois.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            ois.close();
        } catch (ClassNotFoundException | IOException var6) {
            System.out.println("Error loading products from file: " + var6.getMessage());
        }

    }

    private Product findProductById(String productId) {
        Iterator var2 = this.getAllProducts().iterator();

        Product product;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            product = (Product)var2.next();
        } while(!product.getProductId().equals(productId));

        return product;
    }
}
