import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPasswordChangeGUI {
    public static void main(String[] args) {
        new AdminPasswordChange();
    }
}

class AdminPasswordChange extends JFrame implements ActionListener {

    private JTextField usernameText;
    private JPasswordField oldPasswordText, newPasswordText;
    private JLabel usernameStatus, overallStatus;

    public AdminPasswordChange() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); // Modern theme
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTitle("🔒 Change Admin Password | ShopSync");
        setSize(600, 500);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("🔑 Change Admin Password");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBounds(150, 20, 300, 30);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading);

        int labelX = 100;
        int fieldX = 250;
        int y = 80;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(labelX, y, 120, 25);
        add(usernameLabel);

        usernameText = new JTextField();
        usernameText.setFont(fieldFont);
        usernameText.setBounds(fieldX, y, 200, 25);
        add(usernameText);

        // Username status
        usernameStatus = new JLabel("Username Availability:");
        usernameStatus.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        usernameStatus.setForeground(Color.GRAY);
        usernameStatus.setBounds(labelX + 50, y + 30, 400, 20);
        add(usernameStatus);

        y += 70;

        // Old password
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setFont(labelFont);
        oldPasswordLabel.setBounds(labelX, y, 120, 25);
        add(oldPasswordLabel);

        oldPasswordText = new JPasswordField();
        oldPasswordText.setFont(fieldFont);
        oldPasswordText.setBounds(fieldX, y, 200, 25);
        add(oldPasswordText);

        y += 50;

        // New password
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(labelFont);
        newPasswordLabel.setBounds(labelX, y, 120, 25);
        add(newPasswordLabel);

        newPasswordText = new JPasswordField();
        newPasswordText.setFont(fieldFont);
        newPasswordText.setBounds(fieldX, y, 200, 25);
        add(newPasswordText);

        y += 60;

        // Overall status
        overallStatus = new JLabel("Status:");
        overallStatus.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        overallStatus.setForeground(Color.GRAY);
        overallStatus.setBounds(labelX + 80, y, 400, 20);
        add(overallStatus);

        y += 40;

        // Buttons
        JButton confirmBtn = new JButton("✅ Confirm");
        confirmBtn.setBounds(160, y, 110, 35);
        confirmBtn.setBackground(new Color(76, 175, 80));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmBtn.addActionListener(this);
        add(confirmBtn);

        JButton resetBtn = new JButton("🔄 Reset");
        resetBtn.setBounds(280, y, 110, 35);
        resetBtn.setBackground(new Color(33, 150, 243));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resetBtn.addActionListener(this);
        add(resetBtn);

        JButton closeBtn = new JButton("❌ Close");
        closeBtn.setBounds(400, 20, 80, 25);
        closeBtn.setForeground(Color.RED);
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.contains("Reset")) {
            usernameText.setText("");
            oldPasswordText.setText("");
            newPasswordText.setText("");
            usernameStatus.setText("Username Availability:");
            overallStatus.setText("Status:");
        } else if (action.contains("Confirm")) {
            String username = usernameText.getText();
            String oldPass = new String(oldPasswordText.getPassword());
            String newPass = new String(newPasswordText.getPassword());

            usernameStatus.setText("Username Availability:");
            overallStatus.setText("Status:");

            if (Admin.checkUsername(username) == 1) {
                usernameStatus.setText("Username Availability: ✅ Admin Found");
                overallStatus.setText("Status: 🔐 Valid Username");
                int result = Admin.changePassword(username, oldPass, newPass);

                switch (result) {
                    case 0 -> overallStatus.setText("Status: ✅ Password changed successfully");
                    case 1 -> {
                        overallStatus.setText("Status: ❌ Incorrect old password");
                        oldPasswordText.setText("");
                        newPasswordText.setText("");
                    }
                    case 2 -> overallStatus.setText("Status: ⚠️ Error during password change");
                }
            } else {
                usernameStatus.setText("Username Availability: ❌ Not Found");
                overallStatus.setText("Status: ❌ Invalid Admin Username");
            }
        }
    }
}
