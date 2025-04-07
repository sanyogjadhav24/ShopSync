import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.FlatLightLaf;

public class CustomerLoginGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CustLogin());
    }
}

class CustLogin extends JFrame implements ActionListener {
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JLabel usernameStatus, overallStatus;
    private JButton loginBtn, resetBtn, closeBtn;

    public CustLogin() {
        setTitle("Customer Login | ShopSync");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel heading = new JLabel("Customer Login", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameText = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordText = new JPasswordField(20);

        usernameStatus = new JLabel(" ");
        usernameStatus.setForeground(new Color(0, 102, 204));

        overallStatus = new JLabel(" ");
        overallStatus.setForeground(new Color(0, 153, 0));

        loginBtn = new JButton("🔐 Login");
        resetBtn = new JButton("♻ Reset");
        closeBtn = new JButton("❌ Close");

        loginBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        // Add elements to the form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameText, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordText, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(usernameStatus, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(overallStatus, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(loginBtn, gbc);
        gbc.gridx = 1;
        formPanel.add(resetBtn, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(closeBtn, gbc);

        panel.add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            usernameText.setText("");
            passwordText.setText("");
            usernameStatus.setText(" ");
            overallStatus.setText(" ");
        } else if (e.getSource() == loginBtn) {
            String username = usernameText.getText().trim();
            String password = new String(passwordText.getPassword()).trim();

            if (username.isEmpty()) {
                usernameStatus.setText("❌ Username cannot be empty.");
                overallStatus.setText("⚠ Please enter username.");
                return;
            }

            if (Customer.checkUsername(username) == 1) {
                usernameStatus.setText("✅ Username Found");
                Customer returningCustomer = new Customer("customer");
                returningCustomer.readFromFile(username);

                if (password.equals(returningCustomer.getPassword())) {
                    overallStatus.setText("✅ Login Successful!");
                    // Proceed to customer area
                    CustomerArea tabObj = new CustomerArea(returningCustomer.getUsername());
                    tabObj.productTable();
                    tabObj.createCartTable();
                    dispose(); // close login window
                } else {
                    overallStatus.setText("❌ Incorrect Password.");
                }
            } else {
                usernameStatus.setText("❌ Username not found.");
                overallStatus.setText("⚠ Invalid login.");
            }
        } else if (e.getSource() == closeBtn) {
            dispose();
        }
    }
}
