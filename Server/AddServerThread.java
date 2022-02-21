package NodeBook.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AddServerThread extends Thread{
    Socket socket;
    DateBaseController dateBaseController = new DateBaseController();

    public AddServerThread(Socket socket){
        this.socket= socket;
    }

    public void run() {
        BufferedReader bufferedReader=null;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bufferedReader.readLine();
            System.out.println("来自客户端的数据："+line);
            String[] timeAndEvent = line.split(" ");
            String time = timeAndEvent[0];
            String event = timeAndEvent[1];
            dateBaseController.addNewEvent(time,event);
            bufferedReader.close();
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
