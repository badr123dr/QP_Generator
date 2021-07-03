package Manage_Professors;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Professor {


    private JPanel Professors_list;
    private JTable table1;
    DatabaseConnection db;
    PreparedStatement pst;



    public static void main(String[] args) {
        JFrame frame = new JFrame("View_Professor");
        frame.setContentPane(new View_Professor().Professors_list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    void DisplayData()
    {
        try
        {
            pst = db.connection.prepareStatement("select * from users");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
