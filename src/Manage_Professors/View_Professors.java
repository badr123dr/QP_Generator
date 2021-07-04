package Manage_Professors;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Professors {
    private JTable table1;
    private JPanel Porfessor_list;


    static PreparedStatement pst;
    static DatabaseConnection db ;


    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("View_Professors");

        frame.setContentPane(new View_Professors().Porfessor_list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }





    public View_Professors() throws SQLException {
        db=DatabaseConnection.getInstance();
        try
        {
            pst = db.connection.prepareStatement("select name_user as Name,lastname_user as LastName,username," +
                    "email from users");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }




}
