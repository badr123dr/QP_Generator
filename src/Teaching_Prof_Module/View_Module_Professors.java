package Teaching_Prof_Module;

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
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
                if(table1.getSelectedRowCount()==1){
                    //if a single row is selected
                    int i = table1.getSelectedRow();
                    String id_module = tblModel.getValueAt(i,0).toString();
                    String id_professor = tblModel.getValueAt(i,2).toString();

                    try {
                        int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                                "alert", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            pst = db.connection.prepareStatement("DELETE FROM module_professor WHERE id_module =? and id_user=?");
                            pst.setString(1, id_module);
                            pst.setString(2, id_professor);
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
