import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI extends JFrame implements ActionListener {

    String[] cardTitles = {
        "Customer Registration", "Admin Registration", "Customer Login",
        "Admin Login", "Customer Password Change", "Admin Password Change",
        "Browse Products", "View Cart", "Exit"
    };

    String[] iconPaths = {
        "icons/register.png", "icons/admin.png", "icons/login.png",
        "icons/admin_login.png", "icons/Cust_pass.png", "icons/admin_pass.png",
        "icons/products.jpg", "icons/cart.jpg", "icons/exit.jfif"
    };

    JButton[] cardButtons = new JButton[9];
    JPanel cardPanel;

    public HomeGUI() {
        setTitle("ShopSync- Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // FULL SCREEN
        setUndecorated(false); // Optional: set true for frameless

        setLayout(new BorderLayout());

        // ===== Navbar =====
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(33, 150, 243));
        navBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel navTitle = new JLabel("🛍️ ShopSync Dashboard");
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        navTitle.setForeground(Color.WHITE);
        navBar.add(navTitle);
        add(navBar, BorderLayout.NORTH);

        // ===== Main Grid Panel =====
        cardPanel = new JPanel(new GridLayout(3, 3, 30, 30));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        cardPanel.setBackground(Color.WHITE);

        for (int i = 0; i < 9; i++) {
            cardButtons[i] = createCardButton(cardTitles[i], iconPaths[i]);
            cardPanel.add(cardButtons[i]);
        }

        add(cardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createCardButton(String text, String iconPath) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setPreferredSize(new Dimension(200, 160));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(10);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Load icon if exists
        ImageIcon icon = loadIcon(iconPath, 64, 64);
        if (icon != null) button.setIcon(icon);

        button.addActionListener(this);
        return button;
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null; // fallback: no icon
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        String label = src.getText().replaceAll("<.*?>", ""); // strip HTML

        switch (label) {
            case "Customer Registration":
                new CustGUI(); break;
            case "Admin Registration":
                new AdminGUI(); break;
            case "Customer Login":
                new CustLogin(); break;
            case "Admin Login":
                new AdminLogin(); break;
            case "Customer Password Change":
                new CustPasswordChangeGUI(); break;
            case "Admin Password Change":
                new AdminPasswordChange(); break;
            case "Browse Products":
                  new BrowseProducts();
                 System.out.println("Opening Product Page..."); break;
            case "View Cart":
                new myCart();
                System.out.println("Opening Cart..."); break;
            case "Exit":
                dispose(); break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown action.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(HomeGUI::new);
    }
}
