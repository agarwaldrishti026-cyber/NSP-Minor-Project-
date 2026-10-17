package ui;

import dao.RegistrationDAO;
import model.Registration;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class UserRegistrationFrame extends JFrame {
    private Registration r;
    private final String password;
    private final JTextField name=new JTextField(), roll=new JTextField(), dept=new JTextField(),
            year=new JTextField(), type=new JTextField(), event=new JTextField(),
            date=new JTextField(), mobile=new JTextField(), email=new JTextField();

    public UserRegistrationFrame(Registration r,String password){
        this.r=r;this.password=password;
        setTitle("My Registration");
        setSize(560,600);setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);setLayout(new BorderLayout());

        JLabel title=new JLabel("MY REGISTRATION - ID "+r.getRegistrationId(),SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,20));

        fill();

        JPanel form=new JPanel(new GridLayout(9,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(15,25,10,25));
        addRow(form,"Student Name:",name);addRow(form,"Roll Number:",roll);
        addRow(form,"Department:",dept);addRow(form,"Year:",year);
        addRow(form,"Event Type:",type);addRow(form,"Event Name:",event);
        addRow(form,"Event Date:",date);addRow(form,"Mobile:",mobile);addRow(form,"Email:",email);

        JButton update=new JButton("Update My Data"),delete=new JButton("Delete My Data"),back=new JButton("Back");
        JPanel buttons=new JPanel();buttons.add(update);buttons.add(delete);buttons.add(back);

        add(title,BorderLayout.NORTH);add(form,BorderLayout.CENTER);add(buttons,BorderLayout.SOUTH);

        update.addActionListener(e->update());
        delete.addActionListener(e->delete());
        back.addActionListener(e->{new WelcomeFrame().setVisible(true);dispose();});
    }

    private void fill(){
        name.setText(r.getStudentName());roll.setText(r.getRollNumber());dept.setText(r.getDepartment());
        year.setText(r.getYear());type.setText(r.getEventType());event.setText(r.getEventName());
        date.setText(r.getEventDate().toString());mobile.setText(r.getMobile());email.setText(r.getEmail());
    }

    private void addRow(JPanel p,String label,JTextField f){p.add(new JLabel(label));p.add(f);}

    private void update(){
        try{
            if(name.getText().trim().isEmpty()||roll.getText().trim().isEmpty()||event.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Please fill required fields.");return;
            }
            if(!mobile.getText().trim().matches("\\d{10}")){
                JOptionPane.showMessageDialog(this,"Mobile number must contain 10 digits.");return;
            }

            Registration u=new Registration();
            u.setRegistrationId(r.getRegistrationId());u.setStudentName(name.getText().trim());
            u.setRollNumber(roll.getText().trim());u.setDepartment(dept.getText().trim());
            u.setYear(year.getText().trim());u.setEventType(type.getText().trim());
            u.setEventName(event.getText().trim());u.setEventDate(Date.valueOf(date.getText().trim()));
            u.setMobile(mobile.getText().trim());u.setEmail(email.getText().trim());

            if(new RegistrationDAO().updateUserRegistration(u,password)){
                r=u;JOptionPane.showMessageDialog(this,"Your registration was updated successfully.");
            }else JOptionPane.showMessageDialog(this,"Update failed.");
        }catch(IllegalArgumentException ex){JOptionPane.showMessageDialog(this,"Date must be yyyy-mm-dd.");}
    }

    private void delete(){
        if(JOptionPane.showConfirmDialog(this,"Delete your registration permanently?",
                "Confirm Delete",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;

        if(new RegistrationDAO().deleteUserRegistration(r.getRegistrationId(),password)){
            JOptionPane.showMessageDialog(this,"Your registration has been deleted.");
            new WelcomeFrame().setVisible(true);dispose();
        }else JOptionPane.showMessageDialog(this,"Delete failed.");
    }
}
