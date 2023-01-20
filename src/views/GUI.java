package views;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import controllers.nameService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

public class GUI extends JFrame{
    private JPanel nameServicePanel;
    private JTable table1;
    private JTextField userNameTxt;
    private JTextField pinTxt;
    private JButton registerUserTxt;
    private JButton newChat;
    private JPanel panel2;
    private JLabel msgTxt;
    private JTable nSTable;
    private nameService ns;

    public GUI(nameService ns) {
        this.ns = ns;
        setSize(900,500);
        this.setTitle("Name Service");
        this.setResizable(false);
        nameServiceTable();
        add(nameServicePanel);
        validate();
        userNameTxt.setToolTipText("Username");
        pinTxt.setToolTipText("Username");
        this.setVisible(true);
        registerUserTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userNameTxt.getText();
                String pintxt= pinTxt.getText();
                int pin=0;
                try {
                    pin = Integer.parseInt(pintxt);
                    if (name !="" && pinTxt.getText() != ""){
                        String response = ns.createUser(name,pin);
                        msgTxt.setText(response);
                        loadTable();
                        if(response.contains("Created")){
                            userNameTxt.setText("");
                            pinTxt.setText("");
                        }
                    }else if (name !=""){
                        msgTxt.setText("please fill in the username");
                    }else{
                        msgTxt.setText("please fill in the pin");
                    }
                } catch (Exception exception) {
                    msgTxt.setText("the pin must consist of 4 digits");
                }

            }
        });
        newChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("vguhbioj");
                new chatGUI();
            }
        });
    }

    private void nameServiceTable() {
        String[] columnNames = {"Name", "pin"};
        Object[][] data = {
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        nSTable = new JTable(model);
        nSTable.setEnabled(false);
        nSTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        nSTable.getTableHeader().setResizingAllowed(false);
        nSTable.setAutoCreateRowSorter(true);
        loadTable();
        JScrollPane scrollPane = new JScrollPane(nSTable);
        this.add(scrollPane, BorderLayout.EAST);
    }
    public void addTableEntry(Object[] data) {
        DefaultTableModel model = (DefaultTableModel) nSTable.getModel();
        model.addRow(data);

    }
    private void loadTable(){
        Hashtable<String, Integer> entrys = ns.getNameTable();
        DefaultTableModel model = (DefaultTableModel) nSTable.getModel();
        model.setRowCount(0); //clear the name
        Object[] columnNames = {"Name", "Pin"};
        model.setColumnIdentifiers(columnNames);
        for (Map.Entry<String, Integer> entry : entrys.entrySet()) {
            Object[] row = {entry.getKey(), entry.getValue()};
            model.addRow(row);
        }
    }
}
