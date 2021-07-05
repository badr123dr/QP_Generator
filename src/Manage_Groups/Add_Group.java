package Manage_Groups;

import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Add_Group {
    private JPanel add_groupPanel;
    private JLabel add_group_lavel;
    private JLabel labelName;
    private JTextField textName;
    private JLabel labelDescription;
    private JTextField txtDescription;
    private JLabel labelLevel;
    private JButton addButton;
    private JComboBox list_level;
    private JPanel Addgroup;


    PreparedStatement pst;
    DatabaseConnection db ;

    public  void load_data_level() throws SQLException {


        db = DatabaseConnection.getInstance();
        pst = db.connection.prepareStatement("SELECT * FROM level");
        ResultSet res = pst.executeQuery();
        list_level.removeAllItems();
        while(res.next())
        {
            list_level.addItem(res.getString(2));
        }


    }


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Add_Group");
        frame.setContentPane(new Add_Group().Addgroup);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    public Add_Group() throws SQLException {
        db = DatabaseConnection.getInstance();
        load_data_level();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                try {
                    String group_name, description;
                    group_name = textName.getText();
                    description = txtDescription.getText();
                    String level_name = list_level.getSelectedItem().toString();



                    pst = db.connection.prepareStatement("SELECT * from level where name_level=?");
                    pst.setString(1, level_name);
                    ResultSet rs = pst.executeQuery();
                    ArrayList<String> arrayList = new ArrayList<>();
                    while(rs.next())
                    {

                        arrayList.add(rs.getString("id_level"));

                    }


                    if(txtDescription.getText().trim().length()==0 && textName.getText().trim().length()==0 )
                    {
                        JOptionPane.showMessageDialog(null,"please enter a validate value.");
                    }
                    else{
                    pst = db.connection.prepareStatement("INSERT INTO " +
                            "groups(name,description,id_level)" +
                            "VALUES(?,?,?)");
                    pst.setString(1, group_name);
                    pst.setString(2, description);
                    pst.setString(3, arrayList.get(0));
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "group added successfully");
                    //table_load();
                    textName.setText("");
                    txtDescription.setText("");
                    textName.requestFocus();}
                }catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
    }
}
