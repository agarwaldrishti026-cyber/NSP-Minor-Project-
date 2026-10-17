package ui;

import dao.RegistrationDAO;
import model.Registration;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class RegistrationForm extends JFrame {
    private JTextField name, roll, eventName, eventDate, mobile, email;
    private JComboBox<String> department, year, eventType;
    private JPasswordField password, confirm;

    public RegistrationForm() {
        setTitle("User Registration");
        setSize(560,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("WORKSHOP / SEMINAR REGISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel form = new JPanel(new GridLayout(11,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(15,25,10,25));

        name = new JTextField();
        roll = new JTextField();
        department = new JComboBox<>(new String[]{"CSE","IT","ECE","EE","ME","Civil"});
        year = new JComboBox<>(new String[]{"1st Year","2nd Year","3rd Year","4th Year"});
        eventType = new JComboBox<>(new String[]{"Workshop","Seminar"});
        eventName = new JTextField();
        eventDate = new JTextField();
        mobile = new JTextField();
        email = new JTextField();
        password = new JPasswordField();
        confirm = new JPasswordField();

        addRow(form,"Student Name:",name);
        addRow(form,"Roll Number:",roll);
        addRow(form,"Department:",department);
        addRow(form,"Year:",year);
        addRow(form,"Event Type:",eventType);
        addRow(form,"Event Name:",eventName);
        addRow(form,"Event Date (yyyy-mm-dd):",eventDate);
        addRow(form,"Mobile:",mobile);
        addRow(form,"Email:",email);
        addRow(form,"Access Password:",password);
        addRow(form,"Confirm Password:",confirm);

        JButton save = new JButton("Register");
        JButton clear = new JButton("Clear");
        JButton back = new JButton("Back");
        JPanel buttons = new JPanel();
        buttons.add(save); buttons.add(clear); buttons.add(back);

        add(title,BorderLayout.NORTH);
        add(form,BorderLayout.CENTER);
        add(buttons,BorderLayout.SOUTH);

        save.addActionListener(e -> save());
        clear.addActionListener(e -> clear());
        back.addActionListener(e -> { new WelcomeFrame().setVisible(true); dispose(); });
    }

    private void addRow(JPanel p,String label,Component c) {
        p.add(new JLabel(label)); p.add(c);
    }

    private void save() {
        String pw = new String(password.getPassword()).trim();
        String cpw = new String(confirm.getPassword()).trim();

        if (name.getText().trim().isEmpty() || roll.getText().trim().isEmpty()
                || eventName.getText().trim().isEmpty() || eventDate.getText().trim().isEmpty()
                || mobile.getText().trim().isEmpty() || email.getText().trim().isEmpty()
                || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Please fill all fields.");
            return;
        }

        if (!pw.equals(cpw)) {
            JOptionPane.showMessageDialog(this,"Passwords do not match.");
            return;
        }

        if (!mobile.getText().trim().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,"Mobile number must contain 10 digits.");
            return;
        }

        if (!email.getText().trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this,"Enter a valid email.");
            return;
        }

        Date date;
        try {
            date = Date.valueOf(eventDate.getText().trim());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,"Date must be yyyy-mm-dd.");
            return;
        }

        Registration r = new Registration();
        r.setStudentName(name.getText().trim());
        r.setRollNumber(roll.getText().trim());
        r.setDepartment(department.getSelectedItem().toString());
        r.setYear(year.getSelectedItem().toString());
        r.setEventType(eventType.getSelectedItem().toString());
        r.setEventName(eventName.getText().trim());
        r.setEventDate(date);
        r.setMobile(mobile.getText().trim());
        r.setEmail(email.getText().trim());
        r.setRegistrationPassword(pw);

        int id = new RegistrationDAO().saveRegistrationAndGetId(r);

        if (id > 0) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful!\n\nRegistration ID: " + id
                            + "\nRemember your ID and Access Password.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clear();
        } else {
            JOptionPane.showMessageDialog(this,"Registration failed.");
        }
    }

    private void clear() {
        name.setText(""); roll.setText(""); eventName.setText("");
        eventDate.setText(""); mobile.setText(""); email.setText("");
        password.setText(""); confirm.setText("");
        department.setSelectedIndex(0); year.setSelectedIndex(0); eventType.setSelectedIndex(0);
    }
}
