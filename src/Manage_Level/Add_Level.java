package Manage_Level;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Database.*;

public class Add_Level {
    private JPanel manageLevel;
    private JLabel lb_title;
    private JLabel lb_name;
    private JLabel lb_description;
    private JButton bt_ajouter;
    private JTextField txt_nameLVL;
    private JTextField txt_descriptionLVL;


    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("Add_Level");
        frame.setContentPane(new Add_Level().manageLevel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    PreparedStatement pst;
    DatabaseConnection db ;

    public Add_Level() throws SQLException {

        db = DatabaseConnection.getInstance();
        bt_ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String level_name,description;
                level_name = txt_nameLVL.getText();
                description = txt_descriptionLVL.getText();

                try {
                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "level(name_level,description)" +
                            "VALUES(?,?)");
                    pst.setString(1, level_name);
                    pst.setString(2, description);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Level added succesefully");
                    //table_load();
                    txt_nameLVL.setText("");
                    txt_descriptionLVL.setText("");
                    txt_nameLVL.requestFocus();
                }catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
    }
}
