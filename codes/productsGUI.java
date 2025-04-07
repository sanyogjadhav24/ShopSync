import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class productsGUI implements ActionListener {
    JFrame frame;
    JPanel panel;
    JTextField productIDText, productNameText, productPriceText, stockText;
    JTextArea productDescriptionText;
    JTable jt = new JTable();
    DefaultTableModel model = (DefaultTableModel) jt.getModel();
    JScrollPane jsp = new JScrollPane(jt);
    static int exist = 0;

    public productsGUI() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("🛒 Add Products | Admin");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        frame.add(panel);

        JLabel headingLabel = new JLabel("📦 Enter Product Details");
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headingLabel.setBounds(30, 20, 400, 40);
        panel.add(headingLabel);

        int labelWidth = 140;
        int inputWidth = 220;
        int y = 80;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        productIDText = addField(panel, "Product ID:", y, labelWidth, inputWidth, labelFont, fieldFont); y += 50;
        productNameText = addField(panel, "Product Name:", y, labelWidth, inputWidth, labelFont, fieldFont); y += 50;
        productPriceText = addField(panel, "Price (₹):", y, labelWidth, inputWidth, labelFont, fieldFont); y += 50;
        stockText = addField(panel, "Stock:", y, labelWidth, inputWidth, labelFont, fieldFont); y += 50;

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(labelFont);
        descLabel.setBounds(30, y, labelWidth, 25);
        panel.add(descLabel);

        productDescriptionText = new JTextArea();
        productDescriptionText.setFont(fieldFont);
        productDescriptionText.setLineWrap(true);
        productDescriptionText.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(productDescriptionText);
        descScroll.setBounds(180, y, inputWidth, 60);
        panel.add(descScroll);
        y += 80;

        JButton addBtn = new JButton("➕ Add to Inventory");
        addBtn.setBounds(180, y, inputWidth, 35);
        addBtn.setBackground(new Color(76, 175, 80));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.addActionListener(this);
        panel.add(addBtn);

        // Table Section
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Price (₹)");
        model.addColumn("Stock");
        model.addColumn("Description");

        jt.setFont(fieldFont);
        jt.setRowHeight(28);
        jt.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        jt.getTableHeader().setBackground(new Color(63, 81, 181));
        jt.getTableHeader().setForeground(Color.WHITE);
        jsp.setBounds(450, 80, 500, 500);
        panel.add(jsp);

        getFromFile();

        frame.setVisible(true);
    }

    public JTextField addField(JPanel panel, String labelText, int y, int labelWidth, int inputWidth, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setBounds(30, y, labelWidth, 25);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setFont(fieldFont);
        textField.setBounds(180, y, inputWidth, 25);
        panel.add(textField);
        return textField;
    }

    public void getFromFile() {
        File directoryPath = new File("E:\\ShopOnlineFiles\\productInfo\\");
        String contents[] = directoryPath.list();
        if (contents == null) return;

        for (String currentFile : contents) {
            try {
                File myObj = new File(directoryPath + "\\" + currentFile);
                Scanner myReader = new Scanner(myObj);
                String productID = myReader.nextLine().split(":")[1];
                String name = myReader.nextLine().split(":")[1];
                String price = myReader.nextLine().split(":")[1];
                String stock = myReader.nextLine().split(":")[1];
                String desc = myReader.nextLine().split(":")[1];
                model.addRow(new Object[]{productID, name, price, stock, desc});
                myReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(String productID, String name, String price, String stock, String desc) {
        try {
            File checkFile = new File("E:\\ShopOnlineFiles\\productInfo\\" + productID + ".txt");
            if (checkFile.exists()) {
                exist = 1;
            } else {
                FileWriter myWriter = new FileWriter(checkFile);
                myWriter.write("ProductID:" + productID + "\n");
                myWriter.write("ProductName:" + name + "\n");
                myWriter.write("productPrice:" + price + "\n");
                myWriter.write("ProductStock:" + stock + "\n");
                myWriter.write("productDescription:" + desc + "\n");
                myWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new productsGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String productID = productIDText.getText().trim();
        String name = productNameText.getText().trim();
        String price = productPriceText.getText().trim();
        String stock = stockText.getText().trim();
        String desc = productDescriptionText.getText().trim();

        if (productID.isEmpty() || name.isEmpty() || price.isEmpty() || stock.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        createFile(productID, name, price, stock, desc);

        if (exist == 0) {
            model.addRow(new Object[]{productID, name, price, stock, desc});
            JOptionPane.showMessageDialog(frame, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            exist = 0;
            JOptionPane.showMessageDialog(frame, "Product already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        // Clear Fields
        productIDText.setText("");
        productNameText.setText("");
        productPriceText.setText("");
        stockText.setText("");
        productDescriptionText.setText("");
    }
}
