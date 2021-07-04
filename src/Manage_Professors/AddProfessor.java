package Manage_Professors;



import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddProfessor {
    private JTextField textName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPassword;
    private JButton addNewProfessorButton;
    private JTextField txtUsername;
    private JPanel Add_Professor;
    private JTextField txtPhone;


    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("AddProfessor");
        frame.setContentPane(new AddProfessor().Add_Professor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    PreparedStatement pst;
    DatabaseConnection db ;





public AddProfessor() throws SQLException {

        db = DatabaseConnection.getInstance();
        addNewProfessorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Name,LastName,EmailAdress,Password,Username,Phone;
                LastName=txtLastName.getText();
                Name = textName.getText();
                EmailAdress = txtEmail.getText();
                Password = txtPassword.getText();
                Username = txtUsername.getText();
                Phone = txtPhone.getText();

                try {
                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "users(name_user,lastname_user,email,password,username,phone)" +
                            "VALUES(?,?,?,?,?,?)");
                    pst.setString(1, Name);
                    pst.setString(2, LastName);
                    pst.setString(3, EmailAdress);
                    pst.setString(4, Password);
                    pst.setString(5, Username);
                    pst.setString(6, Phone);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Professor added with succesefully");
                    //table_load();
                    textName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtPassword.setText("");
                    txtUsername.setText("");
                    txtPhone.setText("");
                    textName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }


        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
