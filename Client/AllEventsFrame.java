package NodeBook.Client;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;


public class AllEventsFrame {
    private JFrame jFrame = new JFrame();
    private JTable jTable;

    {
        jFrame.setTitle("事件列表");
        jFrame.setBounds(450,80,500,500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public AllEventsFrame(String data){
        String[] columnNames = {"时间","事件"};
        String[] dataString = data.split(" ");
        String[][] timeAndEvents = new String[dataString.length/2][2];

        int j=0;
        for(int i=0;i<dataString.length/2;i++){
            timeAndEvents[i][0]=dataString[j];
            j++;
            timeAndEvents[i][1]=dataString[j];
            j++;
        }

        jTable = new JTable(timeAndEvents,columnNames);
        jTable.setFont(new Font("黑体",Font.PLAIN,20));
        jTable.setRowHeight(30);

        TableColumn column = null;
        int colunms = jTable.getColumnCount();
        for(int i = 0; i < colunms; i++)
        {
            column = jTable.getColumnModel().getColumn(i);
            /*将每一列的默认宽度设置为250*/
            column.setPreferredWidth(250);
        }


        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setSize(500, 500);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        jFrame.add(jScrollPane);
        jFrame.setVisible(true);
    }
}
