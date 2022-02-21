package NodeBook.Client;

import com.sun.javaws.IconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NodeBookFrame {
    JFrame jFrame = new JFrame("日历记事本");
    WestPart westPart = new WestPart();
    EastPart eastPart = new EastPart();
    JMenuBar jMenuBar = new JMenuBar();
    JMenu jMenu = new JMenu("选项");
    JMenuItem showEvents = new JMenuItem("事件列表");
    String timesAndEvents ="";
    Reminder reminder;
    JFrame checkFrame = new JFrame();   //添加成功提示窗口

    {
        //添加成功提示窗口初始化
        checkFrame.setBounds(600,200,300,100);
        JLabel addSuccessful = new JLabel("添加成功！",JLabel.CENTER);
        addSuccessful.setFont(new Font("黑体",Font.PLAIN,20));
        checkFrame.add(addSuccessful);
        checkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flagIsConnected = true;
                BufferedReader bufferedReader=null;
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1",10800);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    timesAndEvents = bufferedReader.readLine();
                    reminder = new Reminder(timesAndEvents);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    flagIsConnected=false;
                }
                if(flagIsConnected)
                    reminder.start();
            }
        }).start();
    }


    {
        showEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader bufferedReader=null;
                Socket socket = null;
                StringBuilder timeAndEvents = new StringBuilder();
                try {
                    socket = new Socket("127.0.0.1",10800);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    timeAndEvents = new StringBuilder(bufferedReader.readLine());
                    AllEventsFrame allEventsFrame = new AllEventsFrame(timeAndEvents.toString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("allEvents.txt")));
                        String data = null;
                        while((data = bufferedReader.readLine())!=null)
                            timeAndEvents.append(data).append(" ");
                        AllEventsFrame allEventsFrame = new AllEventsFrame(timeAndEvents.toString());

                    } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });

        jMenu.add(showEvents);
        jMenuBar.add(jMenu);
        jFrame.setJMenuBar(jMenuBar);
    }

    {
        eastPart.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(eastPart.writingArea.getText().equals("无"))
                    return;
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream("./allEvents.txt",true)));
                    try {
                        bufferedWriter.write(westPart.getSelectTime().substring(5)+" "+eastPart.writingArea.getText()+"\n");
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                //通信
                Socket clientSocket = null;
                PrintStream printStream = null;
                String event = eastPart.writingArea.getText();
                try {
                    clientSocket = new Socket("127.0.0.1",9060);
                    printStream = new PrintStream(clientSocket.getOutputStream());
                    String timeAndEvent = westPart.getSelectTime().substring(5) + " "
                            + eastPart.writingArea.getText();
                    printStream.println(timeAndEvent);
                    printStream.close();
                    clientSocket.close();
                } catch (IOException ioException) {
                    System.out.println("无网络！");
                    ioException.printStackTrace();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                //reminder更新添加新事件
                Socket socket;
                BufferedReader bufferedReader = null;
                try {
                    socket = new Socket("127.0.0.1",10800);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    timesAndEvents = bufferedReader.readLine();
                    reminder.setTimeAndEvent(timesAndEvents);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                //弹出提示窗口
                checkFrame.setVisible(true);

                eastPart.writingArea.setText("无");
            }
        });
    }

    public NodeBookFrame() {
        jFrame.setSize(1400,650);
        jFrame.setLayout(new GridLayout(1,2));
        jFrame.add(westPart.westPanel);
        jFrame.add(eastPart.eastPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        eastPart.selectTimeLabel.setText(westPart.getSelectTime());

        //系统日期时间线程
        Timer timerCurrent = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long timeMillis = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time_ = df.format(new Date(timeMillis));
                eastPart.currentTimeLabel.setForeground(Color.RED);
                eastPart.currentTimeLabel.setText("当前时间：" + time_);
            }
        });

        //选中时间线程
        Timer timerSelect = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eastPart.selectTimeLabel.setText(westPart.getSelectTime());
            }
        });

        //启动线程
        timerCurrent.start();
        timerSelect.start();

        jFrame.setVisible(true);
    }

    class Reminder extends Thread{
        String[] timeAndEvent;
        public boolean flagChange = false;
        public Reminder(String timeAndEvent){
            setTimeAndEvent(timeAndEvent);
        }

        public void setTimeAndEvent(String timeAndEvent){
            this.timeAndEvent=timeAndEvent.split(" ");
            flagChange = true;
        }

        public void run(){
            String[] times = new String[timeAndEvent.length/2];
            String[] events = new String[timeAndEvent.length/2];

            while (true){
                if(flagChange){
                    times = new String[timeAndEvent.length/2];
                    events = new String[timeAndEvent.length/2];

                    int j=0;
                    for(int i=0;i<timeAndEvent.length/2;i++){
                        times[i] = timeAndEvent[j];
                        j++;
                        events[i] = timeAndEvent[j];
                        j++;
                    }
                    flagChange=false;
                }

                long timeMillis = System.currentTimeMillis();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = df.format(new Date(timeMillis));
                String currentTime = time.substring(0,4) + "年"
                        + time.substring(5,7) + "月"
                        + time.substring(8,10) + "日"
                        + time.substring(11,13) + "时"
                        + time.substring(14,16) + "分";
                String second = time.substring(17,19);

                for(int i=0;i<times.length;i++){
                    if(second.equals("00") && times[i].equals(currentTime)){
                        JFrame reminderFrame = new JFrame("提醒");
                        reminderFrame.setLayout(new GridLayout(2,1));
                        reminderFrame.setBounds(600,300,500,150);
                        reminderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        JLabel timeLabel = new JLabel("当前时间：" + times[i],JLabel.CENTER);
                        JLabel eventLabel = new JLabel("待做事件：" + events[i],JLabel.CENTER);
                        timeLabel.setFont(new Font("黑体",Font.PLAIN,20));
                        eventLabel.setFont(new Font("黑体",Font.PLAIN,20));
                        reminderFrame.add(timeLabel);
                        reminderFrame.add(eventLabel);
                        reminderFrame.setVisible(true);
                    }
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
