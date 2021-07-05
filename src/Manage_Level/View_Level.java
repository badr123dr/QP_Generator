package Manage_Level;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Database.*;
import net.proteanit.sql.DbUtils;
import Manage_Level.*;

public class View_Level {
    private JLabel lb_title;
    private JButton bt_modifier;
    private JButton bt_supprimer;
    private JTable level_table;
    private JPanel levelList;
    private JScrollPane table_levels;

    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("View_Level");
        frame.setContentPane(new View_Level().levelList);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    PreparedStatement pst;
    DatabaseConnection db ;

    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("select id_level ID, name_level as 'Level name' ,description Description " +
                    "from level");
            ResultSet rs = pst.executeQuery();
            level_table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public View_Level() throws SQLException {

        db=DatabaseConnection.getInstance();
        showTableData();

        bt_modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Update_Level();


            }
        });

        bt_supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                DefaultTableModel tblModel = (DefaultTableModel) level_table.getModel();
                if(level_table.getSelectedRowCount()==1) {
                    //if a single row is selected
                    int i = level_table.getSelectedRow();
                    String id = tblModel.getValueAt(i, 0).toString();

                    try {
                        int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure?",
                                "alert", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            pst = db.connection.prepareStatement("DELETE FROM level WHERE id_level =?");
                            pst.setString(1, id);
                            pst.executeUpdate();

                            showTableData();
                            //tblModel.removeRow(level_table.getSelectedRow());
                            JOptionPane.showMessageDialog(null, "Deleted successfully !");
                        } else{Thread.sleep(1);}
                        } catch(SQLException | HeadlessException | InterruptedException ex){
                            JOptionPane.showMessageDialog(null, ex);
                        }



                    }
                    else{
                        if (level_table.getRowCount() == 0) {
                            //if table is empty
                            JOptionPane.showMessageDialog(null, "Table is empty !!");
                        } else {
                            //if table is not empty but any row is selected or multiple rows are selected
                            JOptionPane.showMessageDialog(null, "Please select a single row to delete !");
                        }
                    }

            }
        });
    }
}
