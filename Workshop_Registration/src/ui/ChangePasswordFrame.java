package ui;

import dao.LoginDAO;
import javax.swing.*;
import java.awt.*;

public class ChangePasswordFrame extends JFrame {
    private final String username;
    private final JPasswordField oldPw=new JPasswordField(), newPw=new JPasswordField(), confirm=new JPasswordField();

    public ChangePasswordFrame(String username){
        this.username=username;
        setTitle("Change Admin Password");setSize(450,330);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setLayout(new BorderLayout());

        JLabel title=new JLabel("CHANGE PASSWORD",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,20));

        JPanel form=new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,30,10,30));
        form.add(new JLabel("Old Password:"));form.add(oldPw);
        form.add(new JLabel("New Password:"));form.add(newPw);
        form.add(new JLabel("Confirm Password:"));form.add(confirm);

        JButton change=new JButton("Change Password"),cancel=new JButton("Cancel");
        JPanel buttons=new JPanel();buttons.add(change);buttons.add(cancel);

        add(title,BorderLayout.NORTH);add(form,BorderLayout.CENTER);add(buttons,BorderLayout.SOUTH);
        change.addActionListener(e->change());cancel.addActionListener(e->dispose());
    }

    private void change(){
        String o=new String(oldPw.getPassword()).trim();
        String n=new String(newPw.getPassword()).trim();
        String c=new String(confirm.getPassword()).trim();

        if(o.isEmpty()||n.isEmpty()||c.isEmpty()){JOptionPane.showMessageDialog(this,"Fill all fields.");return;}
        if(!n.equals(c)){JOptionPane.showMessageDialog(this,"New passwords do not match.");return;}
        if(n.length()<4){JOptionPane.showMessageDialog(this,"Password must be at least 4 characters.");return;}

        if(new LoginDAO().changePassword(username,o,n)){
            JOptionPane.showMessageDialog(this,"Password changed successfully.");dispose();
        }else JOptionPane.showMessageDialog(this,"Old password is incorrect.");
    }
}
