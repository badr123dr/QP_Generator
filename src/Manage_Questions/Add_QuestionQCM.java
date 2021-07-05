package Manage_Questions;

import LoggedProf.UserLogged;
import Database.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Add_QuestionQCM {
    private JPanel QCM_questionPanel;
    private JLabel lb_title;
    private JLabel lb_option1;
    private JTextField txt_option1;
    private JLabel lb_option4;
    private JLabel lb_option2;
    private JTextField txt_option2;
    private JLabel lb_option5;
    private JLabel lb_option3;
    private JTextField txt_option3;
    private JLabel lb_option6;
    private JComboBox cb_option_number;
    private JComboBox cb_modules;
    private JComboBox cb_difficulty;
    private JLabel lb_module;
    private JTextArea textArea_question;
    private JLabel lb_nbr_options;
    private JLabel lb_difficulty;
    private JButton bt_ajouter;
    private JTextField txt_option4;
    private JTextField txt_option5;
    private JTextField txt_option6;
    private JLabel lb_question;
    private JTextField txt_option7;
    private JTextField txt_option8;
    private JLabel lb_option7;
    private JLabel lb_option8;
    ArrayList<String> options;

    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("Add a QCM Question");
        frame.setContentPane(new Add_QuestionQCM().QCM_questionPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600,400);

    }

    PreparedStatement pst;
    DatabaseConnection db ;

    public void remplirCombo(){

        cb_difficulty.addItem("Easy");
        cb_difficulty.addItem("Medium");
        cb_difficulty.addItem("Advanced");

        for (int i=3;i<=8;i++){
            cb_option_number.addItem(i);
        }

        cb_option_number.setSelectedItem(8);

        try {
            pst = db.connection.prepareStatement("select m.name_module " +
                    "from professeur p join module_professor mp " +
                    "on p.id_user = mp.id_user join module m " +
                    "on m.id_module = mp.id_module " +
                    "where p.id_user = ?");
            pst.setString(1,UserLogged.Id);
            ResultSet rs =  pst.executeQuery();

            while (rs.next()){
                cb_modules.addItem(rs.getString(1));
            }
        }catch (SQLException ex){
            System.out.println(ex.getStackTrace());
        }
    }

    public void setOptionsTrue(){
        lb_option1.setVisible(true);
        txt_option1.setVisible(true);
        lb_option2.setVisible(true);
        txt_option2.setVisible(true);
        lb_option3.setVisible(true);
        txt_option3.setVisible(true);
        lb_option4.setVisible(true);
        txt_option4.setVisible(true);
        lb_option5.setVisible(true);
        txt_option5.setVisible(true);
        lb_option6.setVisible(true);
        txt_option6.setVisible(true);
        lb_option7.setVisible(true);
        txt_option7.setVisible(true);
        lb_option8.setVisible(true);
        txt_option8.setVisible(true);
    }

    public boolean getOptions(){
        if(txt_option1.isVisible()){
            if(txt_option1.getText().trim().length()!=0){
                options.add(txt_option1.getText());
            }else {
                return false;
            }
        }
        if(txt_option2.isVisible()){
            if(txt_option2.getText().trim().length()!=0){
                options.add(txt_option2.getText());
            }else {
                return false;
            }
        }
        if(txt_option3.isVisible()){
            if(txt_option3.getText().trim().length()!=0){
                options.add(txt_option3.getText());
            }else {
                return false;
            }
        }
        if(txt_option4.isVisible()){
            if(txt_option4.getText().trim().length()!=0){
                options.add(txt_option4.getText());
            }else {
                return false;
            }
        }
        if(txt_option5.isVisible()){
            if(txt_option5.getText().trim().length()!=0){
                options.add(txt_option5.getText());
            }else {
                return false;
            }
        }
        if(txt_option6.isVisible()){
            if(txt_option6.getText().trim().length()!=0){
                options.add(txt_option6.getText());
            }else {
                return false;
            }
        }
        if(txt_option7.isVisible()){
            if(txt_option7.getText().trim().length()!=0){
                options.add(txt_option7.getText());
            }else {
                return false;
            }
        }
        if(txt_option8.isVisible()){
            if(txt_option8.getText().trim().length()!=0){
                options.add(txt_option8.getText());
            }else {
                return false;
            }
        }
        return true;
    }

    public Add_QuestionQCM() throws SQLException {
        db=DatabaseConnection.getInstance();
        remplirCombo();

        bt_ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea_question.getText().trim().length() ==0){
                    JOptionPane.showMessageDialog(null,"Les champs sont obligatoires");
                }else {
                    String module = cb_modules.getSelectedItem().toString();
                    String difficulty = cb_difficulty.getSelectedItem().toString();
                    String question = textArea_question.getText().toString();

                    options = new ArrayList<>();
                    if(getOptions()==true){

                        try {
                            // GET MODULE ID  ----------------------------------------------------------------------------------------------
                            pst = db.connection.prepareStatement("SELECT * FROM module WHERE name_module = ?");
                            pst.setString(1, module);
                            ResultSet rs =  pst.executeQuery();

                            ArrayList<String> myArrayList = new ArrayList<String>();
                            while (rs.next()){
                                myArrayList.add((rs.getString(1)));
                            }
                            String module_ID = myArrayList.get(0);
                            //INSERT NEW QUESTION INTO BD  ---------------------------------------------------------------------------------
                            pst = db.connection.prepareStatement("INSERT INTO " +
                                    "questions(id_user,id_module,question_text,difficulty)" +
                                    "VALUES(?,?,?,?)");
                            String profID = UserLogged.Id;
                            pst.setString(1, profID);
                            pst.setString(2, module_ID);
                            pst.setString(3, question);
                            pst.setString(4, difficulty);

                            pst.executeUpdate();

                            JOptionPane.showMessageDialog(null,"Question added successfully");

                            //GET QUESTION ID :________________________________________________________________
                            pst = db.connection.prepareStatement("SELECT * FROM questions WHERE id_user = ? ORDER BY date_creation DESC ");
                            pst.setString(1, UserLogged.Id);
                            ResultSet res =  pst.executeQuery();

                            ArrayList<String> myArray = new ArrayList<String>();
                            while (res.next()){
                                myArray.add((res.getString("id_question")));
                            }
                            String Quest_ID = myArray.get(0);
                            JOptionPane.showMessageDialog(null,"Le ID de la question ajoutée est: "+Quest_ID);
                            // INSERT QUESTION OPTIONS INTO BD :________________________________________________
                            for(int j=0;j<options.size();j++){
                                pst = db.connection.prepareStatement("INSERT INTO " +
                                        "options(description,id_question)" +
                                        "VALUES(?,?)");
                                pst.setString(1, options.get(j).toString());
                                pst.setString(2, Quest_ID);

                                pst.executeUpdate();
                            }
                            JOptionPane.showMessageDialog(null,"Les options sont ajoutées avec succés !");

                        }catch (SQLException ex){
                            ex.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Le remplissage des options est obligatoire");
                    }
                }
            }
        });

        cb_option_number.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setOptionsTrue();
                String x = cb_option_number.getSelectedItem().toString();
                switch (x){
                    case "3":
                        lb_option4.setVisible(false);
                        txt_option4.setVisible(false);
                        lb_option5.setVisible(false);
                        txt_option5.setVisible(false);
                        lb_option6.setVisible(false);
                        txt_option6.setVisible(false);
                        lb_option7.setVisible(false);
                        txt_option7.setVisible(false);
                        lb_option8.setVisible(false);
                        txt_option8.setVisible(false);
                        break;
                    case "4":
                        lb_option5.setVisible(false);
                        txt_option5.setVisible(false);
                        lb_option6.setVisible(false);
                        txt_option6.setVisible(false);
                        lb_option7.setVisible(false);
                        txt_option7.setVisible(false);
                        lb_option8.setVisible(false);
                        txt_option8.setVisible(false);
                        break;
                    case "5":
                        lb_option6.setVisible(false);
                        txt_option6.setVisible(false);
                        lb_option7.setVisible(false);
                        txt_option7.setVisible(false);
                        lb_option8.setVisible(false);
                        txt_option8.setVisible(false);
                        break;
                    case "6":
                        lb_option7.setVisible(false);
                        txt_option7.setVisible(false);
                        lb_option8.setVisible(false);
                        txt_option8.setVisible(false);
                        break;
                    case  "7":
                        lb_option8.setVisible(false);
                        txt_option8.setVisible(false);
                        break;
                }
            }
        });
    }
}
