package Level_Modules;

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

public class View_Module_Level {
    private JPanel list_module_level;
    private JLabel list_label;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel module_level;
    static PreparedStatement pst;
    static DatabaseConnection db ;

    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("SELECT l.id_level,l.name_level,m.id_module,m.name_module" +
                    "                    FROM level l JOIN module_level ml ON ml.id_level=l.id_level" +
                    "                    JOIN module m ON m.id_module=ml.id_module");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("View_Module_Level");
        frame.setContentPane(new View_Module_Level().module_level);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public View_Module_Level() throws SQLException {
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
                    String id_module = tblModel.getValueAt(i,2).toString();
                    String levelid = tblModel.getValueAt(i,0).toString();

                    try {
                        int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                                "alert", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            pst = db.connection.prepareStatement("DELETE FROM module_level WHERE id_module =? and id_level=?");
                            pst.setString(1, id_module);
                            pst.setString(2, levelid);
                            pst.executeUpdate();

                            showTableData();
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
