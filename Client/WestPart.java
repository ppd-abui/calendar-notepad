package NodeBook.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WestPart {
    int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31};
    public JPanel westPanel = new JPanel(new BorderLayout());

    //时间选择区域
    private FlowLayout flowLayout = new FlowLayout();
    private JPanel selectTimeSection = new JPanel(flowLayout);
    //年
    private JButton leftYearBtn = new JButton("<");
    private JComboBox jComboBoxYears = new JComboBox();
    private JLabel jLabelYear = new JLabel("年");
    private JButton rightYearBtn = new JButton(">");
    //月
    private JButton leftMonthBtn = new JButton("<");
    private JComboBox jComboBoxMonths = new JComboBox();
    private JLabel jLabelMonth = new JLabel("月");
    private JButton rightMonthBtn = new JButton(">");
    //时
    private JComboBox jComboBoxHours = new JComboBox();
    private JLabel jLabelHour = new JLabel("时");
    //分
    private JComboBox jComboBoxMinutes = new JComboBox();
    private JLabel jLabelMinute = new JLabel("分");

    {
        flowLayout.setHgap(5);

        //初始化选择框：年 月 时 分
        for(int i=0;i<3;i++)
            jComboBoxYears.insertItemAt(i+2021,i);
        for(int i=0;i<12;i++)
            jComboBoxMonths.insertItemAt(i+1,i);
        for(int i=0;i<24;i++)
            jComboBoxHours.insertItemAt(i,i);
        for(int i=0;i<60;i++)
            jComboBoxMinutes.insertItemAt(i,i);

        //设置默认值为系统时间
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentYear = df.format(new Date(timeMillis)).toString().substring(0,4);
        String currentMonth = df.format(new Date(timeMillis)).toString().substring(5,7);
        String currentHour = df.format(new Date(timeMillis)).toString().substring(11,13);
        String currentMinute = df.format(new Date(timeMillis)).toString().substring(14,16);
        jComboBoxYears.setSelectedIndex(Integer.parseInt(currentYear)-2021);
        jComboBoxMonths.setSelectedIndex(Integer.parseInt(currentMonth)-1);
        jComboBoxHours.setSelectedIndex(Integer.parseInt(currentHour));
        jComboBoxMinutes.setSelectedIndex(Integer.parseInt(currentMinute));

        //添加至selectTimeSection中
        selectTimeSection.add(leftYearBtn);
        selectTimeSection.add(jComboBoxYears);
        selectTimeSection.add(jLabelYear);
        selectTimeSection.add(rightYearBtn);

        selectTimeSection.add(leftMonthBtn);
        selectTimeSection.add(jComboBoxMonths);
        selectTimeSection.add(jLabelMonth);
        selectTimeSection.add(rightMonthBtn);

        selectTimeSection.add(jComboBoxHours);
        selectTimeSection.add(jLabelHour);
        selectTimeSection.add(jComboBoxMinutes);
        selectTimeSection.add(jLabelMinute);
    }

    //星期显示区域
    JPanel weekPanel = new JPanel(new GridLayout(1,7));
    JLabel weeknum[] = new JLabel[7];

    {
        //星期区域初始化
        weeknum[0] = new JLabel("星期一",JLabel.CENTER);
        weeknum[1] = new JLabel("星期二",JLabel.CENTER);
        weeknum[2] = new JLabel("星期三",JLabel.CENTER);
        weeknum[3] = new JLabel("星期四",JLabel.CENTER);
        weeknum[4] = new JLabel("星期五",JLabel.CENTER);
        weeknum[5] = new JLabel("星期六",JLabel.CENTER);
        weeknum[6] = new JLabel("星期日",JLabel.CENTER);

        for(int i=0;i<7;i++)
        {
            weeknum[i].setPreferredSize(new Dimension(30,10));      //设置单元格大小
            weeknum[i].setBorder(BorderFactory.createLineBorder(Color.gray));     //设置显示边框并设计为灰色
            weekPanel.add(weeknum[i]);  //添加进weekPanel
        }
    }


    //日期区域
    JPanel datePanel = new JPanel(new GridLayout(6,7));
    JButton[] dateBtn = new JButton[42];
    int flagFormer = 15;
    int flagLatter = 15;

    {
        //获取当前日期
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int currentDate = Integer.parseInt(df.format(new Date(timeMillis)).toString().substring(8,10));
        //获取当前星期
        Calendar cal = Calendar.getInstance();
        int weekNum = cal.get(Calendar.DAY_OF_WEEK) - 1;

        //计算当月第一天是星期几
        int firstDateIndex = 0;
        if(weekNum<currentDate%7)
            weekNum+=7;
        firstDateIndex = weekNum - currentDate % 7;

        //初始化按钮
        for(int i=0;i<firstDateIndex;i++){
            dateBtn[i] = new JButton("");
            dateBtn[i].setFont(new Font("黑体",Font.PLAIN,20));
            datePanel.add(dateBtn[i]);
            dateBtn[i].setPreferredSize(new Dimension(30,75));
            dateBtn[i].setEnabled(false);   //设置按钮无效
        }
        //当前月份
        int currentMonth = Integer.parseInt(df.format(new Date(timeMillis)).toString().substring(5,7));
        int days = monthDays[currentMonth-1];
        //有效日期按钮
        for(int i = firstDateIndex;i<firstDateIndex+days;i++){
            dateBtn[i] = new JButton("" + (i-firstDateIndex+1));
            dateBtn[i].setFont(new Font("黑体",Font.PLAIN,20));
            datePanel.add(dateBtn[i]);
            dateBtn[i].setPreferredSize(new Dimension(30,75));
            int finalI = i;
            dateBtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flagFormer = flagLatter;
                    dateBtn[flagFormer].setForeground(Color.BLACK);

                    flagLatter = finalI;
                    dateBtn[flagLatter].setForeground(Color.RED);
                }
            });

            //选中初始日期
            if(dateBtn[finalI].getText().equals(String.valueOf(currentDate))){
                flagLatter=flagLatter=finalI;
                dateBtn[flagLatter].doClick();
            }
        }
        //无效日期
        for(int i=firstDateIndex+days;i<42;i++){
            dateBtn[i] = new JButton("");
            dateBtn[i].setFont(new Font("黑体",Font.PLAIN,20));
            datePanel.add(dateBtn[i]);
            dateBtn[i].setPreferredSize(new Dimension(30,75));
            dateBtn[i].setEnabled(false);   //设置按钮无效
        }
    }

    public WestPart(){
        westPanel.add(selectTimeSection,BorderLayout.NORTH);
        westPanel.add(weekPanel,BorderLayout.CENTER);
        westPanel.add(datePanel,BorderLayout.SOUTH);
    }

    int currentMonth = jComboBoxMonths.getSelectedIndex()+1;
    public void addMonth(){
        int nextMonth = currentMonth+1;
        if(nextMonth>12)
            nextMonth=1;
        int currentMonthDays = monthDays[currentMonth-1];
        int nextMonthDays = monthDays[nextMonth-1];

        //查询下一个月份第一天是星期几
        int flag=26;
        for(;flag<42;flag++)
            if(dateBtn[flag].getText().equals(String.valueOf(currentMonthDays)))
                break;
        int beginIndex = flag%7+1;
        if(beginIndex>6)
            beginIndex=0;

        //按钮设置
        for(int i=0;i<beginIndex;i++){
            dateBtn[i].setText("");
            dateBtn[i].setEnabled(false);
        }
        //正常日期
        for(int i=beginIndex;i<beginIndex+nextMonthDays;i++){
            dateBtn[i].setText(""+(i-beginIndex+1));
            dateBtn[i].setEnabled(true);
        }
        //不可选日期
        for(int i=beginIndex+nextMonthDays;i<42;i++){
            dateBtn[i].setText("");
            dateBtn[i].setEnabled(false);
        }

        //当前月加+1;
        currentMonth++;
        if(currentMonth>12)
            currentMonth=1;
    }

    public void subMonth(){
        int lastMonth = currentMonth-1;
        if(lastMonth<1)
            lastMonth=12;
        int lastMonthDays = monthDays[lastMonth-1];

        int flag=0;
        for(;flag<10;flag++)
            if(dateBtn[flag].getText().equals("1"))
                break;

        //计算当月第一天是星期几
        int beginIndex = 0;
        if(flag<lastMonthDays%7)
            flag+=7;

        beginIndex = flag - lastMonthDays % 7;

        //按钮设置
        for(int i=0;i<beginIndex;i++){
            dateBtn[i].setText("");
            dateBtn[i].setEnabled(false);
        }
        //正常日期
        for(int i=beginIndex;i<beginIndex+lastMonthDays;i++){
            dateBtn[i].setText(""+(i-beginIndex+1));
            dateBtn[i].setEnabled(true);
        }
        //不可选日期
        for(int i=beginIndex+lastMonthDays;i<42;i++){
            dateBtn[i].setText("");
            dateBtn[i].setEnabled(false);
        }

        //当前月减1
        currentMonth--;
        if(currentMonth<1)
            currentMonth=12;
    }

    int btnFlag = 0;    //哪一个月份按钮按下
    {
        //月份相关按钮的事件监听
        leftMonthBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFlag = -1;
                int currentMonthIndex = jComboBoxMonths.getSelectedIndex();
                if(currentMonthIndex!=0)
                    jComboBoxMonths.setSelectedIndex(currentMonthIndex-1);
                else if(jComboBoxYears.getSelectedIndex()!=0){
                    jComboBoxMonths.setSelectedIndex(11);
                    jComboBoxYears.setSelectedIndex(jComboBoxYears.getSelectedIndex()-1);
                }
            }
        });

        rightMonthBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnFlag = 1;
                int currentMonthIndex = jComboBoxMonths.getSelectedIndex();
                if(currentMonthIndex!=11)
                    jComboBoxMonths.setSelectedIndex(currentMonthIndex+1);
                else if(jComboBoxYears.getSelectedIndex()!=2){
                    jComboBoxMonths.setSelectedIndex(0);
                    jComboBoxYears.setSelectedIndex(jComboBoxYears.getSelectedIndex()+1);
                }
            }
        });

        jComboBoxMonths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnFlag==-1)
                    subMonth();
                else if(btnFlag==1)
                    addMonth();
                else {
                    int targetMonth = jComboBoxMonths.getSelectedIndex()+1;
                    if(targetMonth<currentMonth)
                        while (targetMonth<currentMonth)
                            subMonth();
                    else
                        while (targetMonth>currentMonth)
                            addMonth();
                }
                btnFlag=0;
            }
        });
    }

    {
        //年份按钮事件
        leftYearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxYears.getSelectedIndex()!=0){
                    for(int i=0;i<12;i++)
                        subMonth();
                    jComboBoxYears.setSelectedIndex(jComboBoxYears.getSelectedIndex()-1);
                }

            }
        });

        rightYearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBoxYears.getSelectedIndex()!=2){
                    for(int i=0;i<12;i++)
                        addMonth();
                    jComboBoxYears.setSelectedIndex(jComboBoxYears.getSelectedIndex()+1);
                }
            }
        });
    }

    public String getSelectTime(){
        String year = jComboBoxYears.getSelectedItem()+"年";
        String month = jComboBoxMonths.getSelectedItem().toString().length()==1?
                "0"+jComboBoxMonths.getSelectedItem()+"月":jComboBoxMonths.getSelectedItem()+"月";
        String day = dateBtn[flagLatter].getText().length()==1?
                "0"+dateBtn[flagLatter].getText()+"日":dateBtn[flagLatter].getText()+"日";
        String hour = jComboBoxHours.getSelectedItem().toString().length()==1?
                "0"+jComboBoxHours.getSelectedItem()+"时":jComboBoxHours.getSelectedItem()+"时";
        String minute = jComboBoxMinutes.getSelectedItem().toString().length()==1?
                "0"+jComboBoxMinutes.getSelectedItem()+"分":jComboBoxMinutes.getSelectedItem()+"分";

        return "选中时间：" + year + month + day + hour + minute;
    }
}
