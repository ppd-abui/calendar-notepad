package NodeBook.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EastPart {
    //日志文件
    File file = new File("./allEvents.txt");

    {
        try {
            file.createNewFile(); // 创建文件
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public JPanel eastPanel = new JPanel(new BorderLayout());

    //选择时间显示和系统时间显示区域
    FlowLayout flowLayout = new FlowLayout();
    JPanel timeDisplayPanel = new JPanel(flowLayout);
    JLabel selectTimeLabel = new JLabel("选中时间");
    JLabel currentTimeLabel = new JLabel("当前时间");

    //文本编辑区
    JTextArea writingArea = new JTextArea(10,50);

    //按钮区域
    JPanel btnJPanel = new JPanel(new FlowLayout());
    JButton saveButton = new JButton("添加");
    JButton deleteButton = new JButton("删除");

    {
        //初始化
        flowLayout.setHgap(30);
        selectTimeLabel.setFont(new Font("黑体",Font.PLAIN,20));
        timeDisplayPanel.add(selectTimeLabel);
        currentTimeLabel.setFont(new Font("黑体",Font.PLAIN,20));
        timeDisplayPanel.add(currentTimeLabel);
        writingArea.setFont(new Font("黑体",Font.PLAIN,25));
        writingArea.setText("无");
        btnJPanel.add(saveButton);
        btnJPanel.add(deleteButton);
    }

    public EastPart(){
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_ = df.format(new Date(timeMillis)).toString();
        currentTimeLabel.setForeground(Color.RED);
        currentTimeLabel.setText("当前时间：" + time_);
        eastPanel.add(timeDisplayPanel,BorderLayout.NORTH);
        eastPanel.add(writingArea,BorderLayout.CENTER);
        eastPanel.add(btnJPanel,BorderLayout.SOUTH);
    }
}
