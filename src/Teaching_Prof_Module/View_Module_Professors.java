package Teaching_Prof_Module;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Module_Professors {
    private JLabel list_label;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel list_module_professor;
    static PreparedStatement pst;
    static DatabaseConnection db ;


    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("select mp.id_module as ModuleID ," +
                    "m.name_module as Module,mp.id_user,u.name_user,u.lastname_user" +

                    "                      from users u join professeur p ON u.id_user=p.id_user" +
                    "                    JOIN module_professor mp ON p.id_user=mp.id_user" +
                    "                    JOIN module m ON m.id_module=mp.id_module " 
                    );
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("View_Module_Professors");
        frame.setContentPane(new View_Module_Professors().list_module_professor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public View_Module_Professors() throws SQLException {
        db=DatabaseConnection.getInstance();
        showTableData();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
