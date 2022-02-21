package NodeBook.Server;

import java.io.*;
import java.net.Socket;

public class SearchServerThread extends Thread{
    Socket socket;
    DateBaseController dateBaseController = new DateBaseController();

    public SearchServerThread(Socket socket){
        this.socket= socket;
    }

    public void run() {
        String  timeAndEvents = dateBaseController.searchAllEvents();
        try{
            PrintStream printStream = null;
            System.out.println("发送至客户端的数据：" + timeAndEvents);
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(timeAndEvents);
            printStream.close();
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
