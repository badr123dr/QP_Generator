package Manage_Module;

import Database.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Module {
    private JLabel list_module;
    private JPanel Modules;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    static PreparedStatement pst;
    static DatabaseConnection db ;

    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("select id_module ID ,name_module as Name,description as Description from module");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("View_Module");
        frame.setContentPane(new View_Module().Modules);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public View_Module() throws SQLException {
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
                        pst = db.connection.prepareStatement("DELETE FROM module WHERE id_module =?");
                        pst.setString(1,id);
                        pst.executeUpdate();

                        showTableData();
                        //tblModel.removeRow(level_table.getSelectedRow());
                        JOptionPane.showMessageDialog(null,"Deleted successfully !");

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
