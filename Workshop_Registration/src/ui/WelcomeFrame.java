package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    public WelcomeFrame() {
        setTitle("Workshop & Seminar Registration System");
        setSize(520, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("WORKSHOP & SEMINAR REGISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JPanel top = new JPanel();
        top.setBackground(new Color(52, 73, 94));
        top.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));
        top.add(title);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel welcome = new JLabel("Welcome! Please select an option", SwingConstants.CENTER);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton admin = new JButton("Admin Login");
        JButton register = new JButton("User Registration");
        JButton manage = new JButton("Manage My Registration");
        JButton exit = new JButton("Exit");

        Dimension d = new Dimension(250, 42);
        admin.setPreferredSize(d); register.setPreferredSize(d);
        manage.setPreferredSize(d); exit.setPreferredSize(d);

        admin.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        manage.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(welcome);
        center.add(Box.createVerticalStrut(15));
        center.add(admin);
        center.add(Box.createVerticalStrut(10));
        center.add(register);
        center.add(Box.createVerticalStrut(10));
        center.add(manage);
        center.add(Box.createVerticalStrut(10));
        center.add(exit);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        admin.addActionListener(e -> { new LoginFrame().setVisible(true); dispose(); });
        register.addActionListener(e -> { new RegistrationForm().setVisible(true); dispose(); });
        manage.addActionListener(e -> { new UserManageLoginFrame().setVisible(true); dispose(); });
        exit.addActionListener(e -> System.exit(0));
    }
}