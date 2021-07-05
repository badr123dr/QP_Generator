package Manage_Questions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import Database.*;
import LoggedProf.UserLogged;

public class Add_QuestionSimple {
    private JLabel lb_title;
    private JComboBox cb_moduleNames;
    private JComboBox cb_levelNames;
    private JComboBox cb_difficulty;
    private JTextArea textArea_question;
    private JButton bt_ajouter;
    private JLabel lb_module;
    private JLabel lb_question;
    private JLabel lb_difficulty;

    PreparedStatement pst;
    DatabaseConnection db ;

    public Add_QuestionSimple() throws SQLException {
        cb_difficulty.addItem("Easy");
        cb_difficulty.addItem("Medium");
        cb_difficulty.addItem("Advanced");

        db=DatabaseConnection.getInstance();

        bt_ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textArea_question.getText()==""){
                    JOptionPane.showMessageDialog(null,"Les champs sont obligatoires");
                }else{
                    String module = cb_moduleNames.getSelectedItem().toString();
                    String level = cb_levelNames.getSelectedItem().toString();
                    String difficulty = cb_difficulty.getSelectedItem().toString();
                    String question = textArea_question.getText().toString();
                    try {
                        // GET MODULE ID------------------------------------------------------------------
                        pst = db.connection.prepareStatement("SELECT * FROM module WHERE name_module = ?");
                        pst.setString(1, module);
                        ResultSet rs =  pst.executeQuery();

                        ArrayList<String> myArrayList = new ArrayList<String>();
                        while (rs.next()){
                            myArrayList.add((rs.getString(1)));
                        }
                        String module_ID = myArrayList.get(0);
                        //---------------------------------------------------------------------------------
                        pst = db.connection.prepareStatement("INSERT INTO " +
                                "questions(id_user,id_module,question_text,difficulty)" +
                                "VALUES(?,?,?,?)");
                        String profID = UserLogged.Id;
                        pst.setString(1, profID);
                        pst.setString(2, module_ID);
                        pst.setString(3, question);
                        pst.setString(4, difficulty);

                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null,"Question Added successfully");

                    }catch (SQLException ex){
                        System.out.println(ex.getStackTrace());
                    }

                }
            }
        });
    }
}
