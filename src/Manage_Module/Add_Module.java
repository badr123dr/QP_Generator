package Manage_Module;

import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Add_Module {
    private JPanel newModule;
    private JLabel AddModule;
    private JTextField txtName;
    private JButton saveButton;
    private JTextField txtdescription;
    private JLabel Description;
    PreparedStatement pst;
    DatabaseConnection db ;

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Add_Module");
        frame.setContentPane(new Add_Module().newModule);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Add_Module() throws SQLException {
        db = DatabaseConnection.getInstance();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Name,Description;
                Description=txtdescription.getText();
                Name = txtName.getText();


                try {
                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "module(name_module,description)" +
                            "VALUES(?,?)");
                    pst.setString(1, Name);
                    pst.setString(2, Description);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Module added with succesefully");
                    //table_load();
                    txtdescription.setText("");
                    txtName.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }
}
