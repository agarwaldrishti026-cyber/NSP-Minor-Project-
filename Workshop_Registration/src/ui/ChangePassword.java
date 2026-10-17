package ui;

import javax.swing.*;

public class ChangePassword extends JFrame {

    public ChangePassword() {

        setTitle("Change Password");

        setSize(400,250);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Change Password",SwingConstants.CENTER));

        setVisible(true);

    }

}