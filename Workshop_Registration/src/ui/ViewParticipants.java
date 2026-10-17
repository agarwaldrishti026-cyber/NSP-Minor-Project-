package ui;

import dao.RegistrationDAO;
import model.Registration;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

public class ViewParticipants extends JFrame {
    private final RegistrationDAO dao = new RegistrationDAO();
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField search = new JTextField(18);

    public ViewParticipants() {
        setTitle("Registered Participants");
        setSize(1200,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("REGISTERED WORKSHOP & SEMINAR PARTICIPANTS",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));

        JPanel top = new JPanel();
        top.add(new JLabel("Search Roll Number:"));
        JButton searchBtn = new JButton("Search");
        JButton refresh = new JButton("Refresh");
        top.add(search); top.add(searchBtn); top.add(refresh);

        String[] cols={"Registration ID","Student Name","Roll Number","Department","Year",
                "Event Type","Event Name","Event Date","Mobile","Email"};

        model = new DefaultTableModel(cols,0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] widths={110,150,120,100,100,110,160,110,120,200};
        for(int i=0;i<widths.length;i++) table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JButton update=new JButton("Update");
        JButton delete=new JButton("Delete");
        JButton back=new JButton("Back");
        JPanel bottom=new JPanel();
        bottom.add(update); bottom.add(delete); bottom.add(back);

        add(title,BorderLayout.NORTH);
        add(top,BorderLayout.PAGE_START);
        add(new JScrollPane(table),BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);

        searchBtn.addActionListener(e->doSearch());
        refresh.addActionListener(e->{search.setText(""); loadAll();});
        update.addActionListener(e->update());
        delete.addActionListener(e->delete());
        back.addActionListener(e->{new Dashboard().setVisible(true);dispose();});

        loadAll();
    }

    private void loadAll(){ display(dao.getAllRegistrations()); }

    private void doSearch(){
        String s=search.getText().trim();
        if(s.isEmpty()){loadAll();return;}
        display(dao.searchRegistration(s));
    }

    private void display(ArrayList<Registration> list){
        model.setRowCount(0);
        for(Registration r:list)
            model.addRow(new Object[]{r.getRegistrationId(),r.getStudentName(),r.getRollNumber(),
                    r.getDepartment(),r.getYear(),r.getEventType(),r.getEventName(),
                    r.getEventDate(),r.getMobile(),r.getEmail()});
    }

    private void update(){
        int row=table.getSelectedRow();
        if(row<0){JOptionPane.showMessageDialog(this,"Select a registration first.");return;}

        int id=Integer.parseInt(model.getValueAt(row,0).toString());
        JTextField name=f(1,row), roll=f(2,row), dept=f(3,row), year=f(4,row),
                type=f(5,row), event=f(6,row), date=f(7,row), mobile=f(8,row), email=f(9,row);

        JPanel p=new JPanel(new GridLayout(9,2,8,8));
        String[] labels={"Student Name:","Roll Number:","Department:","Year:","Event Type:",
                "Event Name:","Event Date:","Mobile:","Email:"};
        JTextField[] fields={name,roll,dept,year,type,event,date,mobile,email};
        for(int i=0;i<labels.length;i++){p.add(new JLabel(labels[i]));p.add(fields[i]);}

        int result=JOptionPane.showConfirmDialog(this,p,"Update Registration",
                JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(result!=JOptionPane.OK_OPTION)return;

        try{
            Registration r=new Registration();
            r.setRegistrationId(id); r.setStudentName(name.getText().trim());
            r.setRollNumber(roll.getText().trim()); r.setDepartment(dept.getText().trim());
            r.setYear(year.getText().trim()); r.setEventType(type.getText().trim());
            r.setEventName(event.getText().trim()); r.setEventDate(Date.valueOf(date.getText().trim()));
            r.setMobile(mobile.getText().trim()); r.setEmail(email.getText().trim());

            if(dao.updateRegistration(r)){JOptionPane.showMessageDialog(this,"Updated successfully.");loadAll();}
            else JOptionPane.showMessageDialog(this,"Update failed.");
        }catch(IllegalArgumentException ex){JOptionPane.showMessageDialog(this,"Date must be yyyy-mm-dd.");}
    }

    private JTextField f(int col,int row){
        return new JTextField(model.getValueAt(row,col).toString());
    }

    private void delete(){
        int row=table.getSelectedRow();
        if(row<0){JOptionPane.showMessageDialog(this,"Select a registration first.");return;}
        int id=Integer.parseInt(model.getValueAt(row,0).toString());
        String name=model.getValueAt(row,1).toString();
        if(JOptionPane.showConfirmDialog(this,"Delete registration of "+name+"?",
                "Confirm Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            if(dao.deleteRegistration(id)){JOptionPane.showMessageDialog(this,"Deleted successfully.");loadAll();}
            else JOptionPane.showMessageDialog(this,"Delete failed.");
        }
    }
}
