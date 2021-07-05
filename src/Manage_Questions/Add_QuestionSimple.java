package Manage_Questions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import Database.*;
//import LoggedProf.UserLogged;
import  LoggedProf.*;

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
    private JPanel simpleQuestionPanel;

    PreparedStatement pst;
    DatabaseConnection db ;
/*
    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("Add Simple Question");
        frame.setContentPane(new Add_QuestionSimple().simpleQuestionPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        JOptionPane.showMessageDialog(null,UserLogged.Id);
    }
*/
    public void remplirCombo(){

        cb_difficulty.addItem("Easy");
        cb_difficulty.addItem("Medium");
        cb_difficulty.addItem("Advanced");
        try {
            //pst = db.connection.prepareStatement("SELECT * FROM module");
            pst = db.connection.prepareStatement("select m.name_module " +
                    "from professeur p join module_professor mp " +
                    "on p.id_user = mp.id_user join module m " +
                    "on m.id_module = mp.id_module " +
                    "where p.id_user = ?");
            pst.setString(1,UserLogged.Id);
            ResultSet rs =  pst.executeQuery();

            while (rs.next()){
                cb_moduleNames.addItem(rs.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getStackTrace());
        }
    }
    public Add_QuestionSimple() throws SQLException {

        db=DatabaseConnection.getInstance();
        remplirCombo();

        bt_ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(textArea_question.getText().trim().length() ==0){
                    JOptionPane.showMessageDialog(null,"Les champs sont obligatoires");
                }else{
                    String module = cb_moduleNames.getSelectedItem().toString();
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
