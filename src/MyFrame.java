import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
public class MyFrame extends JFrame implements ActionListener{
    private JButton myButton;
    private JComboBox<String> comboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private WestminsterShoppingManager shoppingManager;
    private JLabel selectCategoryLabel;
    private JButton shoppingCartButton;
    private JTable shoppingCartTable;
    private JLabel selectProductsLabel;
    private JLabel productIDLabel;
    private JLabel categoryLabel;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JLabel colorLabel;
    private JLabel ItemsAvailableLabel;
    private JLabel brandLabel;
    private JLabel WarrantyLabel;
    private JPanel shoppingCartPanel;
    private JFrame shoppingCartFrame;
    private JLabel totalLabel;
    private JLabel discountTenLabel;
    private JLabel discountTwentyLabel;
    private JLabel finalTotalLabel;

    private void updateTableData() {
        this.tableModel.setRowCount(0);
        Iterator var1 = this.shoppingManager.getAllProducts().iterator();

        while(true) {
            Product product;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                product = (Product)var1.next();
            } while(!this.comboBox.getSelectedItem().equals("All") && (!this.comboBox.getSelectedItem().equals("Clothes") || !(product instanceof Clothing)) && (!this.comboBox.getSelectedItem().equals("Electronics") || !(product instanceof Electronics)));

            Object[] rowData = new Object[]{product.getProductId(), product.getProductName(), product instanceof Clothing ? "Clothing" : "Electronics", product.getPrice(), this.getProductInfo(product)};
            this.tableModel.addRow(rowData);
        }
    }

    private String getProductInfo(Product product) {
        String var10000;
        if (product instanceof Electronics) {
            var10000 = ((Electronics)product).getBrand();
            return "Brand: " + var10000 + ", Warranty: " + ((Electronics)product).getWarranty() + " years, Available Items: " + product.getAvailableItems();
        } else if (product instanceof Clothing) {
            var10000 = ((Clothing)product).getSize();
            return "Size: " + var10000 + ", Color: " + ((Clothing)product).getColor() + ", Available Items: " + product.getAvailableItems();
        } else {
            return "N/A";
        }
    }

    private Product getProductInfo(String info) {
        String[] tokens = info.split(", ");
        String type = tokens[0];
        String size;
        String color;
        if (type.contains("Brand")) {
            size = tokens[0].substring(tokens[0].indexOf(":") + 1).trim();
            color = tokens[1].substring(tokens[1].indexOf(":") + 1).trim();
            return new Electronics("", "", 0, 0.0, size, Double.parseDouble(color.split(" ")[0]));
        } else if (type.contains("Size")) {
            size = tokens[0].substring(tokens[0].indexOf(":") + 1).trim();
            color = tokens[1].substring(tokens[1].indexOf(":") + 1).trim();
            return new Clothing("", "", 0, 0.0, size, color);
        } else {
            return null;
        }
    }

    MyFrame(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        this.setPreferredSize(new Dimension(1200, 720));
        String[] items = new String[]{"All", "Clothes", "Electronics"};
        this.selectCategoryLabel = new JLabel("Select Product Category");
        this.shoppingCartPanel = new JPanel();
        this.shoppingCartPanel.setPreferredSize(new Dimension(500, 500));
        this.shoppingCartPanel.setVisible(false);
        this.productIDLabel = new JLabel("   Product ID :");
        this.categoryLabel = new JLabel("   Category :");
        this.nameLabel = new JLabel("   Name :");
        this.sizeLabel = new JLabel("   Size :");
        this.colorLabel = new JLabel("   Colour :");
        this.ItemsAvailableLabel = new JLabel();
        this.brandLabel = new JLabel("   Brand :");
        this.WarrantyLabel = new JLabel("   Warranty :");
        this.totalLabel = new JLabel("    Total -");
        this.discountTenLabel = new JLabel("    First Purchase Discount (10%) -");
        this.discountTwentyLabel = new JLabel("    Three item in same Category (20%) -");
        this.finalTotalLabel = new JLabel("   Final Total -");
        this.finalTotalLabel.setFont(new Font("Arial", 1, 14));
        this.comboBox = new JComboBox(items);
        this.comboBox.addActionListener(this);
        this.myButton = new JButton("Add to Shopping Cart");
        this.myButton.setFocusable(false);
        this.myButton.addActionListener(this);
        this.selectProductsLabel = new JLabel("  Selected Products - Details");
        this.selectProductsLabel.setFont(new Font("Arial", 1, 14));
        this.shoppingCartButton = new JButton("Shopping Cart");
        this.shoppingCartButton.setFocusable(false);
        this.shoppingCartButton.addActionListener(this);
        this.tableModel = new DefaultTableModel();
        this.tableModel.addColumn("Product ID");
        this.tableModel.addColumn("Name");
        this.tableModel.addColumn("Category");
        this.tableModel.addColumn("Price(£)");
        this.tableModel.addColumn("Info");
        this.productTable = new JTable(this.tableModel);
        this.productTable.setRowHeight(30);
        this.updateTableData();
        this.highlightLowStockProducts();
        this.productTable.getSelectionModel().addListSelectionListener((e) -> {
            int selectedRow = this.productTable.getSelectedRow();
            if (selectedRow != -1) {
                Object category = this.tableModel.getValueAt(selectedRow, 2);
                Object productId = this.tableModel.getValueAt(selectedRow, 0);
                Object productName = this.tableModel.getValueAt(selectedRow, 1);
                Product product = this.getProductInfo(this.tableModel.getValueAt(selectedRow, 4).toString());
                this.productIDLabel.setText("   Product ID: " + productId.toString());
                this.nameLabel.setText("   Name: " + productName.toString());
                this.categoryLabel.setText("   Category: " + category.toString());
                if (product instanceof Clothing) {
                    this.sizeLabel.setText("   Size :" + ((Clothing)product).getSize());
                    this.colorLabel.setText("   Color :" + ((Clothing)product).getColor());
                } else if (product instanceof Electronics) {
                    this.brandLabel.setText("   Brand :" + ((Electronics)product).getBrand());
                    this.WarrantyLabel.setText("   Warranty :" + ((Electronics)product).getWarranty() + " years");
                }

                if (category.toString().equals("Clothing")) {
                    this.sizeLabel.setVisible(true);
                    this.colorLabel.setVisible(true);
                    this.ItemsAvailableLabel.setVisible(true);
                    this.brandLabel.setVisible(false);
                    this.WarrantyLabel.setVisible(false);
                } else if (category.toString().equals("Electronics")) {
                    this.brandLabel.setVisible(true);
                    this.WarrantyLabel.setVisible(true);
                    this.sizeLabel.setVisible(false);
                    this.colorLabel.setVisible(false);
                    this.ItemsAvailableLabel.setVisible(false);
                }
            }

        });
        this.productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = MyFrame.this.productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Object productId = MyFrame.this.tableModel.getValueAt(selectedRow, 0);
                        Object productName = MyFrame.this.tableModel.getValueAt(selectedRow, 1);
                        MyFrame.this.productIDLabel.setText("   Product ID: " + productId.toString());
                        MyFrame.this.nameLabel.setText("   Name: " + productName.toString());
                    }
                }

            }
        });
        JScrollPane scrollPane = new JScrollPane(this.productTable);
        this.setTitle("Westminster Shopping Center");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.blue);
        this.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new FlowLayout(1));
        northPanel.add(this.selectCategoryLabel);
        northPanel.add(this.comboBox);
        northPanel.add(this.shoppingCartButton);
        this.add(northPanel, "North");
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(this.selectProductsLabel, "North");
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, 1));
        labelsPanel.add(this.productIDLabel);
        labelsPanel.add(this.categoryLabel);
        labelsPanel.add(this.nameLabel);
        labelsPanel.add(this.sizeLabel);
        labelsPanel.add(this.colorLabel);
        labelsPanel.add(this.ItemsAvailableLabel);
        labelsPanel.add(this.brandLabel);
        labelsPanel.add(this.WarrantyLabel);
        southPanel.add(labelsPanel, "Center");
        JPanel buttonPanel = new JPanel(new FlowLayout(1));
        buttonPanel.add(this.myButton);
        southPanel.add(buttonPanel, "South");
        this.add(southPanel, "South");
        this.add(scrollPane, "Center");
        this.pack();
        this.setVisible(true);
    }

    private void highlightLowStockProducts() {
        this.productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int availableItemsColumnIndex = 4;
                Object availableItemsInfo = table.getValueAt(row, availableItemsColumnIndex);
                String availableItemsString = availableItemsInfo.toString().replaceAll("\\D+", "");
                int availableItems = 0;

                try {
                    availableItems = Integer.parseInt(availableItemsString);
                } catch (NumberFormatException var13) {
                    System.err.println("Error parsing available items: " + var13.getMessage());
                }

                if (availableItems < 3) {
                    cellComponent.setBackground(Color.RED);
                } else {
                    cellComponent.setBackground(table.getBackground());
                }

                return cellComponent;
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.myButton) {
            this.addToShoppingCart();
        }

        if (e.getSource() == this.shoppingCartButton) {
            this.openShoppingCartWindow();
        }

        if (e.getSource() == this.comboBox) {
            String selectedCategory = (String)this.comboBox.getSelectedItem();
            if (!selectedCategory.equals("All") && !selectedCategory.equals("Clothes") && !selectedCategory.equals("Electronics")) {
                System.out.println(selectedCategory);
            }

            this.updateTableData();
        }

    }

    private void addToShoppingCart() {
        if (this.shoppingCartTable == null) {
            this.initializeShoppingCart();
        }

        int selectedRow = this.productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object productId = this.tableModel.getValueAt(selectedRow, 0);
            Object productName = this.tableModel.getValueAt(selectedRow, 1);
            Object price = this.tableModel.getValueAt(selectedRow, 3);
            DefaultTableModel shoppingCartTableModel = (DefaultTableModel)this.shoppingCartTable.getModel();
            boolean itemAlreadyExists = false;

            for(int i = 0; i < shoppingCartTableModel.getRowCount(); ++i) {
                String cartProductId = shoppingCartTableModel.getValueAt(i, 0).toString().split(",")[0].replace("ID: ", "").trim();
                if (cartProductId.equals(productId.toString())) {
                    int currentQuantity = (Integer)shoppingCartTableModel.getValueAt(i, 1);
                    shoppingCartTableModel.setValueAt(currentQuantity + 1, i, 1);
                    double totalPrice = (double)(currentQuantity + 1) * Double.parseDouble(price.toString());
                    shoppingCartTableModel.setValueAt(totalPrice, i, 2);
                    itemAlreadyExists = true;
                    break;
                }
            }

            if (!itemAlreadyExists) {
                Object[] var10000 = new Object[3];
                String var10003 = productId.toString();
                var10000[0] = "ID: " + var10003 + ", Name: " + productName.toString();
                var10000[1] = 1;
                var10000[2] = price;
                Object[] rowData = var10000;
                shoppingCartTableModel.addRow(rowData);
            }

            this.updateShoppingCartDetails();
        }

    }

    private void updateShoppingCartDetails() {
        DefaultTableModel shoppingCartTableModel = (DefaultTableModel)this.shoppingCartTable.getModel();
        double total = 0.0;
        int itemCount = shoppingCartTableModel.getRowCount();

        for(int i = 0; i < itemCount; ++i) {
            double price = (Double)shoppingCartTableModel.getValueAt(i, 2);
            int quantity = (Integer)shoppingCartTableModel.getValueAt(i, 1);
            total += price * (double)quantity;
        }

        double discountTen = total * 0.1;
        double discountTwenty = total >= 3.0 ? total * 0.2 : 0.0;
        double finalTotal = total - discountTen - discountTwenty;
        JLabel var10000 = this.totalLabel;
        Object[] var10002 = new Object[]{total};
        var10000.setText("    Total - " + String.format("%.2f", var10002) + " £");
        var10000 = this.discountTenLabel;
        var10002 = new Object[]{discountTen};
        var10000.setText("    First Purchase Discount (10%) - " + String.format("%.2f", var10002) + " £");
        var10000 = this.discountTwentyLabel;
        var10002 = new Object[]{discountTwenty};
        var10000.setText("    Three items in the same Category Discount (20%) - " + String.format("%.2f", var10002) + " £");
        var10000 = this.finalTotalLabel;
        var10002 = new Object[]{finalTotal};
        var10000.setText("    Final Total - " + String.format("%.2f", var10002) + " £");
    }

    private void openShoppingCartWindow() {
        if (this.shoppingCartFrame == null) {
            this.initializeShoppingCart();
        }

        this.shoppingCartFrame.setVisible(true);
    }

    private void initializeShoppingCart() {
        this.shoppingCartFrame = new JFrame("Shopping Cart");
        this.shoppingCartFrame.setDefaultCloseOperation(2);
        this.shoppingCartFrame.setSize(500, 500);
        this.shoppingCartFrame.setLayout(new BorderLayout());
        JPanel shoppingCartPanel = new JPanel();
        shoppingCartPanel.setLayout(new BorderLayout());
        DefaultTableModel shoppingCartTableModel = new DefaultTableModel();
        shoppingCartTableModel.addColumn("Product");
        shoppingCartTableModel.addColumn("Quantity");
        shoppingCartTableModel.addColumn("Price");
        this.shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane shoppingCartScrollPane = new JScrollPane(this.shoppingCartTable);
        this.shoppingCartTable.setRowHeight(30);
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 1));
        labelsPanel.add(this.totalLabel);
        labelsPanel.add(this.discountTenLabel);
        labelsPanel.add(this.discountTwentyLabel);
        labelsPanel.add(this.finalTotalLabel);
        shoppingCartPanel.add(shoppingCartScrollPane, "Center");
        shoppingCartPanel.add(labelsPanel, "South");
        this.shoppingCartFrame.add(shoppingCartPanel);
    }
}
