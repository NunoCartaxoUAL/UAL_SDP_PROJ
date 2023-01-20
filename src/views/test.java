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

public class test extends JFrame{
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

    public test(nameService ns) {

        this.ns = ns;
        setLayout(new FlowLayout());
        setSize(650,500);
        this.setTitle("Name Service");
        nameServiceTable();
        validate();
        this.setVisible(true);
        this.setTitle("Image");
        registerUserTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] data2 = {"John Smith", 40};
                addTableEntry(data2);
            }
        });
    }

    private void nameServiceTable() {
        String[] columnNames = {"Name", "pin"};
        Object[][] data = {
                {"John Doe", 30},
                {"Jane Doe", 25}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        nSTable = new JTable(model);
        nSTable.setEnabled(false);
        //Object[] data2 = {"John Smith", 40};
        //addTableEntry(data2);
        JScrollPane scrollPane = new JScrollPane(nSTable);
        this.add(scrollPane, BorderLayout.EAST);
    }
    public void addTableEntry(Object[] data) {
        DefaultTableModel model = (DefaultTableModel) nSTable.getModel();
        model.addRow(data);

    }
}
