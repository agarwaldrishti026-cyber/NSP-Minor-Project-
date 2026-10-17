package ui;

import dao.LoginDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public LoginFrame() {
        setTitle("Admin Login");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("ADMIN LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel form = new JPanel(new GridLayout(2,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(25,30,15,30));
        form.add(new JLabel("Username:")); form.add(username);
        form.add(new JLabel("Password:")); form.add(password);

        JButton login = new JButton("Login");
        JButton clear = new JButton("Clear");
        JButton back = new JButton("Back");
        JPanel buttons = new JPanel();
        buttons.add(login); buttons.add(clear); buttons.add(back);

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        login.addActionListener(e -> doLogin());
        clear.addActionListener(e -> { username.setText(""); password.setText(""); });
        back.addActionListener(e -> { new WelcomeFrame().setVisible(true); dispose(); });
    }

    private void doLogin() {
        String u = username.getText().trim();
        String p = new String(password.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }

        if (new LoginDAO().login(u,p)) {
            new Dashboard(u).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}
