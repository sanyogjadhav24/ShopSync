import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class myCart extends JFrame implements ActionListener {
    JTable cartTable;
    DefaultTableModel model;
    JButton checkoutButton, removeItemButton, backButton;
    JLabel totalLabel;
    JPanel panel;
    double totalAmount = 0.0;

    public myCart() {
        setTitle("My Cart | ShopSync - E-Commerce Platform");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // FlatLaf look and feel (optional but recommended)
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (Exception e) {
            System.out.println("Failed to set FlatLaf look.");
        }

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(250, 250, 250));

        // Table
        String[] columns = {"Product ID", "Product Name", "Price", "Quantity", "Subtotal"};
        model = new DefaultTableModel(columns, 0);
        cartTable = new JTable(model);
        cartTable.setRowHeight(28);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // South panel (bottom bar)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        checkoutButton = new JButton("Proceed to Checkout");
        removeItemButton = new JButton("Remove Selected Item");
        backButton = new JButton("Back");
        buttonPanel.add(backButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(checkoutButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        checkoutButton.addActionListener(this);
        removeItemButton.addActionListener(this);
        backButton.addActionListener(this);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Example cart data (can be replaced with actual cart logic)
        addItemToCart("P101", "Organic Fertilizer", 250.0, 2);
        addItemToCart("P202", "Tractor Rental", 1200.0, 1);

        setVisible(true);
    }

    public void addItemToCart(String id, String name, double price, int quantity) {
        double subtotal = price * quantity;
        model.addRow(new Object[]{id, name, price, quantity, subtotal});
        totalAmount += subtotal;
        updateTotal();
    }

    public void updateTotal() {
        totalLabel.setText("Total: ₹" + String.format("%.2f", totalAmount));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeItemButton) {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                double subtotal = (double) model.getValueAt(selectedRow, 4);
                totalAmount -= subtotal;
                model.removeRow(selectedRow);
                updateTotal();
            }
        } else if (e.getSource() == checkoutButton) {
            JOptionPane.showMessageDialog(this, "Proceeding to checkout...\nTotal: ₹" + totalAmount);
            // Proceed with payment or confirmation logic
        } else if (e.getSource() == backButton) {
            dispose(); // Go back or close
        }
    }

    public static void main(String[] args) {
        new myCart();
    }
}
