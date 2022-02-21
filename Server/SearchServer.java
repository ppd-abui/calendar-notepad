package NodeBook.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SearchServer extends Thread{
    DateBaseController dateBaseController = new DateBaseController();
    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10800);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("客户端成功连接！传送数据至客户端！");
            } catch (IOException e) {
                e.printStackTrace();
            }

            SearchServerThread searchServerThread = new SearchServerThread(socket);
            searchServerThread.start();
        }
    }
}
