package views;

import controllers.nameService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Map;

public class nameServiceGUI extends JFrame{
    private JPanel nameServicePanel;
    private JButton refreshBTN;
    private nameService nS;
    private JTable nsTable;
    private JScrollPane scrollPane;

    public nameServiceGUI(nameService nS){
        this.nS=nS;
        setSize(200,300);
        this.setTitle("Name Service");
        this.setResizable(false);
        nameServiceTable();
        refreshBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        this.add(nameServicePanel);
        Toolkit toolKit = getToolkit();
        Dimension size = toolKit.getScreenSize();
        setLocation(size.width/2 - getWidth()-435, size.height/2 - getHeight()/2-200);
        validate();
        this.setVisible(true);
    }

    private void nameServiceTable() {
        String[] columnNames = {"Name", "pin"};
        Object[][] data = {
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        nsTable.setModel(model);
        nsTable.setEnabled(false);
        nsTable.setAutoResizeMode(4);
        //nsTable.setPreferredSize(new Dimension(100,40));
        nsTable.setFont(new Font("Verdana", Font.PLAIN, 12));
        nsTable.setRowHeight(20);
        nsTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        nsTable.getTableHeader().setResizingAllowed(false);
        nsTable.setAutoCreateRowSorter(true);
        loadTable();
        //scrollPane = new JScrollPane(nsTable);
        //this.add(scrollPane, BorderLayout.EAST);
    }
    private void loadTable(){
        Hashtable<String, Integer> entrys = nS.getNameTable();
        DefaultTableModel model = (DefaultTableModel) nsTable.getModel();
        model.setRowCount(0); //clear the name
        Object[] columnNames = {"Name", "Pin"};
        model.setColumnIdentifiers(columnNames);
        for (Map.Entry<String, Integer> entry : entrys.entrySet()) {
            Object[] row = {entry.getKey(), entry.getValue()};
            model.addRow(row);
        }
    }
}
