package Manage_Professors;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Professors {
    private JTable table1;
    private JPanel Porfessor_list;
    private JButton View;


    PreparedStatement pst;
    DatabaseConnection db ;


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("View_Professors");
        frame.setContentPane(new View_Professors().Porfessor_list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public View_Professors() throws SQLException {
        db = DatabaseConnection.getInstance();
        View.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    void table_load()
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
