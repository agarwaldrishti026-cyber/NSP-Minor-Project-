package ui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private final String username;

    public Dashboard(String username) {
        this.username = username;

        setTitle("Admin Dashboard");
        setSize(600, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        JButton view = new JButton("View / Search Participants");
        JButton register = new JButton("New User Registration");
        JButton change = new JButton("Change Password");
        JButton logout = new JButton("Logout");

        for (JComponent c : new JComponent[]{welcome,view,register,change,logout}) {
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        center.add(welcome);
        center.add(Box.createVerticalStrut(20));
        center.add(view);
        center.add(Box.createVerticalStrut(10));
        center.add(register);
        center.add(Box.createVerticalStrut(10));
        center.add(change);
        center.add(Box.createVerticalStrut(10));
        center.add(logout);

        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        view.addActionListener(e -> { new ViewParticipants().setVisible(true); dispose(); });
        register.addActionListener(e -> { new RegistrationForm().setVisible(true); dispose(); });
        change.addActionListener(e -> new ChangePasswordFrame(username).setVisible(true));
        logout.addActionListener(e -> { new WelcomeFrame().setVisible(true); dispose(); });
    }

    public Dashboard() {
        this("admin");
    }
}
