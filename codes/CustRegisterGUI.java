import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustRegisterGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(CustGUI::new);
    }
}

class CustGUI extends JFrame implements ActionListener {
    JTextField usernameText, fullnameText, dobText, addressText, phoneText, emailText, paymentprefText;
    JPasswordField passwordText;
    JLabel usernameStatus, overallStatus;

    public CustGUI() {
        setTitle("🛒 Customer Register | ShopSync");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        JLabel title = new JLabel("🛍️ Customer Registration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // Center Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Username
        formPanel.add(new JLabel("Username:"), gbc); gbc.gridx++;
        usernameText = new JTextField(20); usernameText.setFont(fieldFont); formPanel.add(usernameText, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Password:"), gbc); gbc.gridx++;
        passwordText = new JPasswordField(20); passwordText.setFont(fieldFont); formPanel.add(passwordText, gbc);

        // Fullname
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Full Name:"), gbc); gbc.gridx++;
        fullnameText = new JTextField(20); fullnameText.setFont(fieldFont); formPanel.add(fullnameText, gbc);

        // DOB
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Date of Birth (dd/mm/yyyy):"), gbc); gbc.gridx++;
        dobText = new JTextField(20); dobText.setFont(fieldFont); formPanel.add(dobText, gbc);

        // Address
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Address:"), gbc); gbc.gridx++;
        addressText = new JTextField(20); addressText.setFont(fieldFont); formPanel.add(addressText, gbc);

        // Phone
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Phone Number:"), gbc); gbc.gridx++;
        phoneText = new JTextField(20); phoneText.setFont(fieldFont); formPanel.add(phoneText, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Email:"), gbc); gbc.gridx++;
        emailText = new JTextField(20); emailText.setFont(fieldFont); formPanel.add(emailText, gbc);

        // Payment Pref
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Payment Preference (COD/UPI/Card):"), gbc); gbc.gridx++;
        paymentprefText = new JTextField(20); paymentprefText.setFont(fieldFont); formPanel.add(paymentprefText, gbc);

        // Username Availability
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        usernameStatus = new JLabel("Username Availability: ");
        usernameStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(usernameStatus, gbc);

        // Overall Status
        gbc.gridy++;
        overallStatus = new JLabel("Status: ");
        overallStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(overallStatus, gbc);

        // Buttons
        gbc.gridy++;
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton signupBtn = new JButton("Sign Up");
        JButton resetBtn = new JButton("Reset");
        JButton closeBtn = new JButton("Close");

        signupBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        closeBtn.addActionListener(e -> dispose());

        btnPanel.add(signupBtn);
        btnPanel.add(resetBtn);
        btnPanel.add(closeBtn);
        formPanel.add(btnPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Reset")) {
            usernameText.setText("");
            passwordText.setText("");
            fullnameText.setText("");
            dobText.setText("");
            addressText.setText("");
            phoneText.setText("");
            emailText.setText("");
            paymentprefText.setText("");
            usernameStatus.setText("Username Availability: ");
            overallStatus.setText("Status: ");
        } else if (cmd.equals("Sign Up")) {
            System.out.println("SIGN UP PRESSED");
            usernameStatus.setText("Username Availability: ");
            try {
                if (Customer.checkUsername(usernameText.getText()) == 1) {
                    usernameStatus.setText("Username Availability: Username Taken");
                    overallStatus.setText("Status: Username Taken.");
                } else {
                    usernameStatus.setText("Username Availability: Available");

                    Customer cust = new Customer("customer");
                    cust.setUsername(usernameText.getText());
                    cust.setPassword(new String(passwordText.getPassword()));
                    cust.setFullname(fullnameText.getText());
                    cust.setDOB(dobText.getText());
                    cust.setAddress(addressText.getText());
                    cust.setPhone(Long.parseLong(phoneText.getText()));
                    cust.setEmail(emailText.getText());
                    cust.setPaymentPreference(paymentprefText.getText());

                    cust.writeToFile();

                    usernameStatus.setText("Username Availability: Registered Successfully");
                    overallStatus.setText("Status: Successfully Registered");
                }
            } catch (Exception ex) {
                overallStatus.setText("Status: Error found. Check your input.");
                ex.printStackTrace();
            }
        }
    }
}
