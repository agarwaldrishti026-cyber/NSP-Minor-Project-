package ui;

import dao.RegistrationDAO;
import model.Registration;
import javax.swing.*;
import java.awt.*;

public class UserManageLoginFrame extends JFrame {
    private final JTextField id = new JTextField();
    private final JPasswordField password = new JPasswordField();

    public UserManageLoginFrame(){
        setTitle("Manage My Registration");
        setSize(450,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title=new JLabel("MANAGE MY REGISTRATION",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,20));

        JPanel form=new JPanel(new GridLayout(2,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(25,30,15,30));
        form.add(new JLabel("Registration ID:"));form.add(id);
        form.add(new JLabel("Access Password:"));form.add(password);

        JButton login=new JButton("Continue"), back=new JButton("Back");
        JPanel buttons=new JPanel();buttons.add(login);buttons.add(back);

        add(title,BorderLayout.NORTH);add(form,BorderLayout.CENTER);add(buttons,BorderLayout.SOUTH);

        login.addActionListener(e->login());
        back.addActionListener(e->{new WelcomeFrame().setVisible(true);dispose();});
    }

    private void login(){
        try{
            int regId=Integer.parseInt(id.getText().trim());
            String pw=new String(password.getPassword()).trim();
            if(pw.isEmpty()){JOptionPane.showMessageDialog(this,"Enter your password.");return;}

            Registration r=new RegistrationDAO().getUserRegistration(regId,pw);
            if(r!=null){new UserRegistrationFrame(r,pw).setVisible(true);dispose();}
            else JOptionPane.showMessageDialog(this,"Invalid Registration ID or password.",
                    "Access Denied",JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException e){JOptionPane.showMessageDialog(this,"Registration ID must be a number.");}
    }
}
