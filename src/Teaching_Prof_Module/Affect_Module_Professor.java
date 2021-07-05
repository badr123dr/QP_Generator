package Teaching_Prof_Module;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Affect_Module_Professor {
    private JLabel module_professor;
    private JComboBox moduleComboBox;
    private JComboBox professorComboBox;
    private JPanel Teaching;
    private JButton assignButton;
    private JLabel Full_name_Professor_label;

    PreparedStatement pst;
    DatabaseConnection db ;

    public  void load_data_modules() throws SQLException {


        db = DatabaseConnection.getInstance();
        pst = db.connection.prepareStatement("SELECT * FROM module");
        ResultSet res = pst.executeQuery();
        moduleComboBox.removeAllItems();
        while(res.next())
        {
            moduleComboBox.addItem(res.getString(2));
        }




    }

    public void load_data_professor() throws SQLException {
        db = DatabaseConnection.getInstance();
        pst = db.connection.prepareStatement("SELECT u.username,u.name_user,u.lastname_user FROM users u join professeur p ON p.id_user=u.id_user");
        ResultSet res = pst.executeQuery();
        professorComboBox.removeAllItems();
        while(res.next())
        {
            professorComboBox.addItem(res.getString(1));


        }
    }
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Affect_Module_Professor");
        frame.setContentPane(new Affect_Module_Professor().Teaching);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(350,400);


    }

    public Affect_Module_Professor() throws SQLException {
        db = DatabaseConnection.getInstance();
        load_data_modules();
        load_data_professor();
        Full_name_Professor_label.setVisible(false);
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String module = moduleComboBox.getSelectedItem().toString();
                    String professor = professorComboBox.getSelectedItem().toString();



                    pst = db.connection.prepareStatement("SELECT m.id_module from  module m  where m.name_module=?");
                    pst.setString(1, module);
                    ResultSet rs = pst.executeQuery();
                    ArrayList<String> arrayList = new ArrayList<>();
                    while(rs.next())
                    {

                        arrayList.add(rs.getString("id_module"));

                    }

                     String moduleid = arrayList.get(0);

                    pst = db.connection.prepareStatement("SELECT p.id_user from users u JOIN professeur p " +
                            "ON p.id_user=u.id_user  where u.username=?");
                    pst.setString(1, professor);
                    ResultSet res = pst.executeQuery();
                    ArrayList<String> liste = new ArrayList<>();
                    while(res.next())
                    {

                        liste.add(res.getString("id_user"));

                    }

                    String professorid = liste.get(0);

                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "module_professor(id_user,id_module)" +
                            "VALUES(?,?)");
                    pst.setString(1, professorid);
                    pst.setString(2, moduleid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "module assigned with successfully");
                }catch (SQLException e1)
                {
                    e1.printStackTrace();
                }


            }
        });
        professorComboBox.addItemListener(new ItemListener()  {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try{
                    String username=professorComboBox.getSelectedItem().toString();

                    db = DatabaseConnection.getInstance();
                    pst = db.connection.prepareStatement("SELECT u.name_user,u.lastname_user FROM users u where u.username=?");
                    pst.setString(1,username );
                    ResultSet res = pst.executeQuery();
                    ArrayList<String> listes = new ArrayList<>();

                    while(res.next())
                    {
                        listes.add(res.getString(1) + " " + res.getString(2));


                    }
                    Full_name_Professor_label.setVisible(true);
                    Full_name_Professor_label.setText(listes.get(0));



                }catch(SQLException e1)
                {
                    e1.printStackTrace();
                }


            }
        });
    }
}
