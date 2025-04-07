import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminLoginGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(AdminLogin::new);
    }
}

class AdminLogin extends JFrame implements ActionListener {

    JTextField usernameText;
    JPasswordField passwordText;
    JLabel usernameStatus, overallStatus;
    JButton loginBtn, resetBtn, closeWindowBtn;

    public AdminLogin() {
        setTitle("Admin Login | ShopSync");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(Color.WHITE);
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel heading = new JLabel("Administrator Login");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(heading, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        // Username
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);

        usernameText = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameText, gbc);

        // Username Status
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        usernameStatus = new JLabel(" ");
        usernameStatus.setForeground(Color.GRAY);
        panel.add(usernameStatus, gbc);

        // Password
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordText, gbc);

        // Overall Status
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        overallStatus = new JLabel(" ");
        overallStatus.setForeground(Color.BLUE);
        panel.add(overallStatus, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        loginBtn = new JButton("Login");
        resetBtn = new JButton("Reset");
        closeWindowBtn = new JButton("Close");
        buttonPanel.add(loginBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(closeWindowBtn);

        gbc.gridy++;
        panel.add(buttonPanel, gbc);

        // Event listeners
        loginBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        closeWindowBtn.addActionListener(e -> dispose());

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
                usernameStatus.setText("Username is required.");
                overallStatus.setText("Please enter your username.");
                return;
            }

            if (Admin.checkUsername(username) == 1) {
                usernameStatus.setText("Admin Found.");
                Admin returningAdmin = new Admin("admin");
                returningAdmin.readFromFile(username);

                if (password.equals(returningAdmin.getPassword())) {
                    overallStatus.setText("Login Successful.");
                    productsGUI obj = new productsGUI();
                    obj.productTable();
                } else {
                    overallStatus.setText("Incorrect Password.");
                }
            } else {
                usernameStatus.setText("Username not found.");
                overallStatus.setText("Admin not found.");
            }
        }
    }
}
