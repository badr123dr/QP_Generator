package Level_Modules;

import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Affecting_Level_Modules {
    private JPanel Learning;
    private JLabel module_professor;
    private JComboBox moduleComboBox;
    private JComboBox levelCombobox;
    private JButton assignButton;
    private JLabel Full_name_Professor_label;
    private JPanel module_level;
    PreparedStatement pst;
    DatabaseConnection db ;

    public  void load_data_modules() throws SQLException {


        db = DatabaseConnection.getInstance();
        pst = db.connection.prepareStatement("SELECT * FROM module");
        ResultSet res = pst.executeQuery();
        moduleComboBox.removeAllItems();
        while(res.next())
        {
            moduleComboBox.addItem(res.getString(2));
        }
    }
    public void load_data_level() throws SQLException {
        db = DatabaseConnection.getInstance();
        pst = db.connection.prepareStatement("SELECT * from level");
        ResultSet res = pst.executeQuery();
        levelCombobox.removeAllItems();
        while(res.next())
        {
            levelCombobox.addItem(res.getString(2));


        }
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Affecting_Level_Modules");
        frame.setContentPane(new Affecting_Level_Modules().module_level);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Affecting_Level_Modules() throws SQLException {
        db = DatabaseConnection.getInstance();
        load_data_modules();
        load_data_level();
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String module = moduleComboBox.getSelectedItem().toString();
                    String level = levelCombobox.getSelectedItem().toString();



                    pst = db.connection.prepareStatement("SELECT m.id_module from  module m  where m.name_module=?");
                    pst.setString(1, module);
                    ResultSet rs = pst.executeQuery();
                    ArrayList<String> arrayList = new ArrayList<>();
                    while(rs.next())
                    {

                        arrayList.add(rs.getString("id_module"));

                    }

                    String moduleid = arrayList.get(0);

                    pst = db.connection.prepareStatement("SELECT id_level from level where name_level=?");
                    pst.setString(1, level);
                    ResultSet res = pst.executeQuery();
                    ArrayList<String> liste = new ArrayList<>();
                    while(res.next())
                    {

                        liste.add(res.getString("id_level"));

                    }

                    String levelid = liste.get(0);

                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "module_level(id_level,id_module)" +
                            "VALUES(?,?)");
                    pst.setString(1, levelid);
                    pst.setString(2, moduleid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, module+" assigned to " +level+"  with successfully");
                }catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
    }
}
