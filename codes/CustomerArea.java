import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.formdev.flatlaf.FlatLightLaf;

public class CustomerArea implements ActionListener {  
    JFrame f;  
    JTable jt = new JTable();
    DefaultTableModel model = (DefaultTableModel) jt.getModel();
    JScrollPane jsp = new JScrollPane(jt);
    JTableHeader tableHeader = jt.getTableHeader();

    JTabbedPane tp = new JTabbedPane();

    // Product components
    JLabel productIDCustomerLabel;
    JTextField productIDCustomerText;
    JLabel productQuantityCustomerLabel;
    JTextField productQuantityCustomerText;
    JButton addToCartBtn;

    // Profile components
    JLabel heading, usernameLabel, fullnameLabel, accesslevelLabel, 
          dobLabel, addressLabel, phoneLabel, emailLabel, paymentPrefLabel;

    // Cart components
    JTable jt1 = new JTable();
    DefaultTableModel model1 = (DefaultTableModel) jt1.getModel();
    JScrollPane jsp1 = new JScrollPane(jt1);
    JTableHeader tableHeader1 = jt1.getTableHeader();
    JLabel totalCostLabel;
    JButton clearCartBtn;
    JButton placeOrderBtn;

    // Panels
    JPanel p1;
    JPanel p2;
    JPanel p3;

    int totalCost = 0;

    public CustomerArea(String custUsername) {  
        // Setup FlatLaf theme
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // Custom FlatLaf styling
        setupCustomUI();

        f = new JFrame("Welcome to ShopSync - Customer");  
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main tabbed pane
        tp = new JTabbedPane(JTabbedPane.TOP);
        tp.setBackground(Color.WHITE);
        
        // Create panels
        p1 = createProductsPanel();
        p2 = createCartPanel();
        p3 = createProfilePanel(custUsername);
        
        // Add tabs
        tp.addTab("Browse Products", new ImageIcon("resources/products.png"), p1);  
        tp.addTab("Your Cart", new ImageIcon("resources/cart.png"), p2);  
        tp.addTab("Your Profile", new ImageIcon("resources/profile.png"), p3); 
        
        f.add(tp);  
        f.setVisible(true);  

        // Initialize tables
        productTable();
        createCartTable();
    }

    private void setupCustomUI() {
        // Customize UI colors and fonts
        UIManager.put("TabbedPane.selectedBackground", Color.WHITE);
        UIManager.put("TabbedPane.underlineColor", Color.decode("#4A90E2"));
        UIManager.put("TabbedPane.tabSeparatorsFullHeight", true);
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.tabHeight", 40);
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 15, 10, 15));
        
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("TextComponent.arc", 5);
        
        UIManager.put("Button.innerFocusWidth", 1);
        UIManager.put("Button.focusedBackground", Color.decode("#E1F0FF"));
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Product table
        jsp.setBounds(30, 30, 940, 400);
        panel.add(jsp);

        // Input fields
        productIDCustomerLabel = new JLabel("Product ID:");
        styleLabel(productIDCustomerLabel);
        productIDCustomerLabel.setBounds(30, 450, 80, 30);
        panel.add(productIDCustomerLabel);

        productIDCustomerText = new JTextField();
        styleTextField(productIDCustomerText);
        productIDCustomerText.setBounds(120, 450, 200, 30);
        panel.add(productIDCustomerText);

        productQuantityCustomerLabel = new JLabel("Quantity:");
        styleLabel(productQuantityCustomerLabel);
        productQuantityCustomerLabel.setBounds(340, 450, 80, 30);
        panel.add(productQuantityCustomerLabel);

        productQuantityCustomerText = new JTextField();
        styleTextField(productQuantityCustomerText);
        productQuantityCustomerText.setBounds(420, 450, 100, 30);
        panel.add(productQuantityCustomerText);

        addToCartBtn = new JButton("Add to Cart");
        stylePrimaryButton(addToCartBtn);
        addToCartBtn.setBounds(540, 450, 150, 30);
        addToCartBtn.addActionListener(this);
        panel.add(addToCartBtn);

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Cart table
        jsp1.setBounds(30, 30, 940, 350);
        panel.add(jsp1);

        // Total cost label
        totalCostLabel = new JLabel("Total Cost: $0");
        styleHeadingLabel(totalCostLabel);
        totalCostLabel.setBounds(30, 400, 300, 40);
        panel.add(totalCostLabel);

        // Buttons
        clearCartBtn = new JButton("Clear Cart");
        styleSecondaryButton(clearCartBtn);
        clearCartBtn.setBounds(30, 450, 150, 35);
        clearCartBtn.addActionListener(this);
        panel.add(clearCartBtn);

        placeOrderBtn = new JButton("Place Order");
        stylePrimaryButton(placeOrderBtn);
        placeOrderBtn.setBounds(200, 450, 150, 35);
        placeOrderBtn.addActionListener(this);
        panel.add(placeOrderBtn);

        return panel;
    }

    private JPanel createProfilePanel(String custUsername) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Customer profile
        Customer myCust = new Customer("customer");
        myCust.readFromFile(custUsername);

        heading = new JLabel("Customer Profile");
        styleLargeHeading(heading);
        heading.setBounds(30, 20, 300, 40);
        panel.add(heading);

        // Profile details
        int y = 70;
        int labelWidth = 300;

        usernameLabel = createProfileLabel("Username:", myCust.getUsername(), y);
        panel.add(usernameLabel);
        y += 40;

        fullnameLabel = createProfileLabel("Full Name:", myCust.getFullname(), y);
        panel.add(fullnameLabel);
        y += 40;

        accesslevelLabel = createProfileLabel("Access Level:", myCust.getAccessLevel(), y);
        panel.add(accesslevelLabel);
        y += 40;

        dobLabel = createProfileLabel("Date of Birth:", myCust.getDOB(), y);
        panel.add(dobLabel);
        y += 40;

        addressLabel = createProfileLabel("Address:", myCust.getAddress(), y);
        panel.add(addressLabel);
        y += 40;

        phoneLabel = createProfileLabel("Phone:",  String.valueOf(myCust.getPhone()), y);
        panel.add(phoneLabel);
        y += 40;

        emailLabel = createProfileLabel("Email:", myCust.getEmail(), y);
        panel.add(emailLabel);
        y += 40;

        paymentPrefLabel = createProfileLabel("Payment Preference:", myCust.getPaymentPreference(), y);
        panel.add(paymentPrefLabel);

        return panel;
    }

    private JLabel createProfileLabel(String label, String value, int y) {
        JLabel lbl = new JLabel("<html><b>" + label + "</b> " + value + "</html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(Color.decode("#333333"));
        lbl.setBounds(30, y, 500, 30);
        return lbl;
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.decode("#333333"));
    }

    private void styleHeadingLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(Color.decode("#4A90E2"));
    }

    private void styleLargeHeading(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(Color.decode("#4A90E2"));
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#CCCCCC")),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void stylePrimaryButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(Color.decode("#4A90E2"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void styleSecondaryButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.decode("#4A90E2"));
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#4A90E2")));
        button.setFocusPainted(false);
    }
  

      public void productTable() {
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(Color.decode("#4A90E2"));
        tableHeader.setForeground(Color.WHITE);
    
        jt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jt.setRowHeight(30);
        jt.setShowGrid(false);
        jt.setIntercellSpacing(new Dimension(0, 0));
        jt.setSelectionBackground(Color.decode("#E1F0FF"));
        jt.setSelectionForeground(Color.BLACK);
    
        // Create a new model instead of trying to remove columns
        model = new DefaultTableModel();
        jt.setModel(model);
    
        // Add columns
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Price");
        model.addColumn("Stock");
        model.addColumn("Description");
    
        getFromFile();
    }
    public void getFromFile() {
        File directoryPath = new File("E:\\ShopOnlineFiles\\productInfo");
        String contents[] = directoryPath.list();
        
        if (contents != null) {
            for (String currentFile : contents) {
                try {
                    File myObj = new File("E:\\ShopOnlineFiles\\productInfo\\" + currentFile);
                    Scanner myReader = new Scanner(myObj);
                    
                    String productIDdata = myReader.nextLine().split(":")[1];
                    String productNameData = myReader.nextLine().split(":")[1];
                    String productPriceData = myReader.nextLine().split(":")[1]; 
                    String productStockData = myReader.nextLine().split(":")[1]; 
                    String productDescriptionData = myReader.nextLine().split(":")[1]; 

                    model.insertRow(model.getRowCount(), new Object[]{
                        productIDdata, productNameData, productPriceData, 
                        productStockData, productDescriptionData
                    });

                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void cartTabFileHandling(String productID , String productQuantity){

        try{
              // File myObj = new File("C:\\Users\\USER\\Desktop\\java\\oop sem peoject\\gui\\productInfo\\"+productID+".txt");
                     File myObj = new File("E:\\ShopOnlineFiles\\productInfo\\"+productID+".txt");
      
      
              Scanner myReader = new Scanner(myObj);
               while (myReader.hasNextLine()) {
              String productIDdata = myReader.nextLine().split(":")[1];
              String productNameData =myReader.nextLine().split(":")[1];
              String productPriceData =myReader.nextLine().split(":")[1]; 
      
              String productStockData =myReader.nextLine().split(":")[1]; 
              String productDescriptionData =myReader.nextLine().split(":")[1]; 
      
                   
      
              int cost=Integer.parseInt(productPriceData.trim())*Integer.parseInt(productQuantity.trim());
              totalCost+=cost;
      
              totalCostLabel.setText("Total Cost = "+totalCost);
      
              
      
             // System.out.println(cost);
      
              //System.out.println(productIDdata+" "+productNameData+" "+ productPriceData+" "+ productStockData+" "+ productDescriptionData); 
              //System.out.println(data);
      
      
              model1.insertRow(model1.getRowCount(), new Object[] { productIDdata, productNameData, productPriceData, productQuantity, Integer.toString(cost) });
      
      
      
      
            }
      
      
      
              myReader.close();
              }
               catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
         }
      
public void createCartTable() {
    tableHeader1.setFont(new Font("Segoe UI", Font.BOLD, 14));
    tableHeader1.setBackground(Color.decode("#4A90E2"));
    tableHeader1.setForeground(Color.WHITE);

    jt1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    jt1.setRowHeight(30);
    jt1.setShowGrid(false);
    jt1.setIntercellSpacing(new Dimension(0, 0));
    jt1.setSelectionBackground(Color.decode("#E1F0FF"));
    jt1.setSelectionForeground(Color.BLACK);

    // Create a new model instead of trying to remove columns
    model1 = new DefaultTableModel();
    jt1.setModel(model1);

    // Add columns
    model1.addColumn("Product ID");
    model1.addColumn("Product Name");
    model1.addColumn("Price");
    model1.addColumn("Quantity");
    model1.addColumn("Subtotal");
}


    
public  void createFile(String productID, String productName, String productPrice, String stock, String productDescription){

    try {
     
     
             // FileWriter myWriter = new FileWriter("C:\\Users\\USER\\Desktop\\java\\oop sem peoject\\gui\\productInfo\\"+productID+".txt");
                 FileWriter myWriter = new FileWriter("E:\\ShopOnlineFiles\\productInfo\\"+productID+".txt");

             myWriter.write("ProductID:"+productID+"\n");
             
             myWriter.write("ProductName:"+productName+"\n");
             myWriter.write("productPrice:"+productPrice+"\n");

             myWriter.write("ProductStock:"+stock+"\n");
             myWriter.write("productDescription:"+productDescription+"\n");



           myWriter.close();

         

     }


  /*   if (myObj.createNewFile()) {
       System.out.println("File created: " + myObj.getName());
     } else {
       System.out.println("File already exists.");
     }*/
    catch (IOException e) {
     System.out.println("An error occurred.");
     e.printStackTrace();
   }



 }



public boolean refreshProductsTable(String productID, String quantity, boolean addBackToStock) {
   String productIDdata;
   String productNameData;
   String productPriceData;
   String productStockData;
   String productDescriptionData;
   int updatedStock = 0;

   try {
       File checkFile = new File("E:\\ShopOnlineFiles\\productInfo\\"+productID+".txt");
       if(checkFile.exists()) {
           File myObj = new File("E:\\ShopOnlineFiles\\productInfo\\"+productID+".txt");
           Scanner myReader = new Scanner(myObj);
           
           // Read all lines first
           List<String> lines = new ArrayList<>();
           while (myReader.hasNextLine()) {
               lines.add(myReader.nextLine());
           }
           myReader.close();

           // Verify we have enough lines
           if (lines.size() < 5) {
               return false;
           }

           // Process each line with proper error handling
           try {
               productIDdata = lines.get(0).split(":")[1].trim();
               productNameData = lines.get(1).split(":")[1].trim();
               productPriceData = lines.get(2).split(":")[1].trim();
               productStockData = lines.get(3).split(":")[1].trim();
               productDescriptionData = lines.get(4).split(":")[1].trim();
           } catch (ArrayIndexOutOfBoundsException e) {
               System.out.println("Malformed product file: " + productID);
               return false;
           }

           if(addBackToStock) {
               updatedStock = Integer.parseInt(productStockData) + Integer.parseInt(quantity.trim());
           } else {
               updatedStock = Integer.parseInt(productStockData) - Integer.parseInt(quantity.trim());
           }

           if(updatedStock >= 0) {
               createFile(productIDdata, productNameData, productPriceData, 
                         Integer.toString(updatedStock), productDescriptionData);
               return true;
           } else {
               return false;
           }
       } else {
           return false;
       }
   } catch (FileNotFoundException e) {
       System.out.println("An error occurred.");
       e.printStackTrace();
       return false;
   } catch (NumberFormatException e) {
       System.out.println("Error parsing numbers in product file");
       return false;
   }
}




  





    // ... [Rest of your existing methods remain exactly the same] ...
    // All your existing methods like cartTabFileHandling, createFile, refreshProductsTable, etc.
    // should be kept exactly as they were, only the UI initialization code has changed

    public static void main(String[] args) {  
        SwingUtilities.invokeLater(() -> {
            new CustomerArea("testUser");
        });
    }

    @Override
    public void actionPerformed(ActionEvent e){
    
      if(e.getSource()==addToCartBtn){
        System.out.println(model.getRowCount());
    
      
    
      /*System.out.println(productIDCustomerText.getText());
      System.out.println(productQuantityCustomerText.getText());*/
    
    String productIdCustomer=productIDCustomerText.getText();
    String productQuantity=productQuantityCustomerText.getText();
    
    System.out.println(productIdCustomer);
    System.out.println(productQuantity);
    
    
    if(productIdCustomer.trim().length()==0 || productQuantity.trim().length()==0){
    JOptionPane.showMessageDialog(p1, "Fill all fields",
    "Warning", JOptionPane.WARNING_MESSAGE);
    
    
    }
    
    else{
    
    
    
      boolean fileExistStatus= refreshProductsTable(productIdCustomer.trim(),productQuantity.trim(),false);
    
      if(fileExistStatus){
    
      model.setRowCount(0);//reseting the table
      getFromFile();//reading the refreshed file
    
    
      cartTabFileHandling(productIdCustomer.trim(),productQuantity.trim());
      }
      else{
        JOptionPane.showMessageDialog(p1, "Product is out of stock or does not exist",
    "Warning", JOptionPane.WARNING_MESSAGE);
    
      }
    
      
      
    
    }
    
     
    
    
    
    
    }
    
    else if(e.getSource()==clearCartBtn){
    
       int a=JOptionPane.showConfirmDialog(p2,"Are you sure?");  
    if(a==JOptionPane.YES_OPTION){
    for(int i=0;i<model1.getRowCount();++i){
    Object productID = jt1.getModel().getValueAt(i, 0);
    Object quantity = jt1.getModel().getValueAt(i, 3);
    
      boolean fileExistStatus= refreshProductsTable(String.valueOf(productID),String.valueOf(quantity),true);
      try{
      Thread.sleep(500);}
      catch(Exception abc){
        System.out.println("error from sleep");
      }
    } 
    
      model.setRowCount(0);
      getFromFile();
    
    
        model1.setRowCount(0);
      totalCostLabel.setText(""); 
      totalCost=0; 
    
    }  
    
    
    
      
    }
    
    else if(e.getSource()==placeOrderBtn){
      if(model1.getRowCount()==0){
        JOptionPane.showMessageDialog(p2, "Your cart is empty, add products to your cart",
    "Warning", JOptionPane.WARNING_MESSAGE);
    
      }
      else{
        JOptionPane.showMessageDialog(p2, "Thank You, Order placed successfully",
    "Warning", JOptionPane.WARNING_MESSAGE);
        model1.setRowCount(0);
        totalCostLabel.setText("");
        totalCost=0;
    
      }
    }
    
    
    }  }