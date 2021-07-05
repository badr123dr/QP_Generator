package Manage_Professors;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JButton updateButton;
    private JButton deleteButton;


    static PreparedStatement pst;
    static DatabaseConnection db ;


    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("View_Professors");

        frame.setContentPane(new View_Professors().Porfessor_list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }


    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("select id_user as ID ,name_user as Name,lastname_user as LastName,username," +"email from users");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public View_Professors() throws SQLException {
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
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                if(table1.getSelectedRowCount()==1){
                    //if a single row is selected
                    int i = table1.getSelectedRow();
                    String id = tblModel.getValueAt(i,0).toString();

                    try {
                        int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                                "alert", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            pst = db.connection.prepareStatement("DELETE FROM users WHERE id_user =?");
                            pst.setString(1, id);
                            pst.executeUpdate();

                            showTableData();
                            //tblModel.removeRow(level_table.getSelectedRow());
                            JOptionPane.showMessageDialog(null, "Deleted successfully !");
                        }
                    } catch (SQLException | HeadlessException ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }

                }else{
                    if(table1.getRowCount()==0){
                        //if table is empty
                        JOptionPane.showMessageDialog(null,"Table is empty !!");
                    }else {
                        //if table is not empty but any row is selected or multiple rows are selected
                        JOptionPane.showMessageDialog(null,"Please select a single row to delete !");
                    }
                }

            }
        });
    }




}
