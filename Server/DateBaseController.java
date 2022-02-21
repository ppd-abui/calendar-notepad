package NodeBook.Server;

import java.sql.*;

public class DateBaseController {
    static {
        //注册JDBC驱动程序
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String url="jdbc:mysql://localhost:3306/nodebook";
    String user="root";
    String password="Iamliam.";
    Connection con = null;
    Statement statement = null;
    ResultSet resultSet = null;

    {
        //数据库连接
        try {
            con = (Connection) DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            resultSet = statement.executeQuery("select * from all_event");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addNewEvent(String time_,String event_){
        String sql = "insert into all_event(time, event) values('"
                + time_ + "','" + event_ + "')";

        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String searchAllEvents(){
        String allEvent = "";
        while (true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                String time = resultSet.getString("Time");
                String event = resultSet.getString("event");
                allEvent = allEvent + time + " " + event + " ";

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return allEvent;
    }

    public void deleteEvent(String time_,String event_){
        String sql = "delete from all_event where time='"+time_+"' and "
        + "event='" + event_ + "'";


        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
