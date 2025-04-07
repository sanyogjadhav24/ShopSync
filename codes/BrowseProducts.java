import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class BrowseProducts extends JFrame {
    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;

    public BrowseProducts() {
        setTitle("Browse Products | ShopSync");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Panel and Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#f8f9fa"));
        add(panel);

        // Heading
        JLabel heading = new JLabel("Available Products", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(heading, BorderLayout.NORTH);

        // Table Model and Table
        String[] columnNames = { "Product ID", "Product Name", "Price", "Stock", "Description" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(24);

        // Header Styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(Color.decode("#007BFF"));
        header.setForeground(Color.white);

        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load product data
        loadProducts();

        setVisible(true);
    }

    private void loadProducts() {
        File dir = new File("E:\\ShopOnlineFiles\\productInfo\\");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                try (Scanner sc = new Scanner(file)) {
                    String[] data = new String[5];
                    int i = 0;
                    while (sc.hasNextLine() && i < 5) {
                        String[] parts = sc.nextLine().split(":", 2);
                        data[i++] = (parts.length > 1) ? parts[1].trim() : "";
                    }
                    model.addRow(data);
                } catch (Exception ex) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
    }
}
