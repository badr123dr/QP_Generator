package Manage_Questions;

import Database.*;
import LoggedProf.*;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View_Question {
    private JPanel viewQuestionPanel;
    private JLabel lb_title;
    private JTable table_questions;
    private JButton bt_modifier;
    private JButton bt_supprimer;
    private JScrollPane tableScrollPanel;

    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("View List Of Questions");
        frame.setContentPane(new View_Question().viewQuestionPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1200,300);

    }

    PreparedStatement pst;
    DatabaseConnection db ;

    public void showTableData(){
        try
        {
            pst = db.connection.prepareStatement("select id_question ID ,  m.name_module Module, q.question_text Question, q.difficulty Difficulty , q.date_creation as 'Date creation' " +
                    "from questions q join module m " +
                    "on q.id_module = m.id_module join module_professor mp " +
                    "on m.id_module = mp.id_module " +
                    "where mp.id_user=?");
            pst.setString(1,UserLogged.Id);
            ResultSet rs =  pst.executeQuery();
            table_questions.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public View_Question() throws SQLException {

        db=DatabaseConnection.getInstance();
        showTableData();
        bt_modifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        bt_supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tblModel = (DefaultTableModel) table_questions.getModel();
                if(table_questions.getSelectedRowCount()==1){
                    //if a single row is selected
                    int i = table_questions.getSelectedRow();
                    String id = tblModel.getValueAt(i,0).toString();
                    try {
                        int result = JOptionPane.showConfirmDialog(null,"Are you sure to delete this question?","Alert",JOptionPane.OK_CANCEL_OPTION);

                        if(result==JOptionPane.OK_OPTION){
                            pst = db.connection.prepareStatement("DELETE FROM questions WHERE id_question =?");
                            pst.setString(1,id);
                            pst.executeUpdate();

                            showTableData();
                            //tblModel.removeRow(level_table.getSelectedRow());
                            JOptionPane.showMessageDialog(null,"Deleted successfully !");
                        }

                    } catch (SQLException | HeadlessException ex) {
                        JOptionPane.showMessageDialog(null,ex);
                    }

                }else{
                    if(table_questions.getRowCount()==0){
                        //if table is empty
                        JOptionPane.showMessageDialog(null,"Table is empty !!");
                    }else {
                        //if table is not empty but any row is selected or multiple rows are selected
                        JOptionPane.showMessageDialog(null,"Please select a single question to delete !");
                    }
                }
            }
        });
    }
}
