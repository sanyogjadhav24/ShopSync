import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.FlatLightLaf;

public class CustPasswordChangeGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Sleek Look & Feel
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CustomerPasswordChange());
    }
}

class CustomerPasswordChange extends JFrame implements ActionListener {
    private JTextField usernameText;
    private JPasswordField oldPasswordText, newPasswordText;
    private JLabel usernameStatus, overallStatus;
    private JButton confirmBtn, resetBtn, closeBtn;

    public CustomerPasswordChange() {
        setTitle("🔐 Change Customer Password - ShopSync");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Change Customer Password", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameText = new JTextField(20);

        JLabel oldPassLabel = new JLabel("Old Password:");
        oldPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        oldPasswordText = new JPasswordField(20);

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        newPasswordText = new JPasswordField(20);

        usernameStatus = new JLabel(" ");
        usernameStatus.setForeground(Color.BLUE);
        overallStatus = new JLabel(" ");
        overallStatus.setForeground(new Color(0, 102, 0));

        confirmBtn = new JButton("✅ Confirm");
        resetBtn = new JButton("♻ Reset");
        closeBtn = new JButton("❌ Close");

        confirmBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameText, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(oldPassLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(oldPasswordText, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(newPassLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(newPasswordText, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(usernameStatus, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(overallStatus, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(confirmBtn, gbc);
        gbc.gridx = 1;
        formPanel.add(resetBtn, gbc);

        gbc.gridx = 1; gbc.gridy++;
        formPanel.add(closeBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            usernameText.setText("");
            oldPasswordText.setText("");
            newPasswordText.setText("");
            usernameStatus.setText(" ");
            overallStatus.setText(" ");
        } else if (e.getSource() == confirmBtn) {
            String username = usernameText.getText();
            String oldPass = new String(oldPasswordText.getPassword());
            String newPass = new String(newPasswordText.getPassword());

            if (Customer.checkUsername(username) == 1) {
                usernameStatus.setText("✅ Username Found");
                int result = Customer.changePassword(username, oldPass, newPass);

                if (result == 0) {
                    overallStatus.setText("✔ Password changed successfully!");
                } else if (result == 1) {
                    overallStatus.setText("❌ Incorrect old password.");
                } else {
                    overallStatus.setText("⚠ Error changing password.");
                }
            } else {
                usernameStatus.setText("❌ Username not found.");
                overallStatus.setText("⚠ Cannot change password.");
            }
        } else if (e.getSource() == closeBtn) {
            dispose();
        }
    }
}
