package NodeBook.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AddServer extends Thread{
    public void run(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9060);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("服务器正在等待客户端的连接请求----");
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("客户端成功连接！接收客户端数据！");
            } catch (IOException e) {
                e.printStackTrace();
            }

            AddServerThread addServerThread = new AddServerThread(socket);     //创建一个线程，用线程创建一个套接字
            addServerThread.start();
        }
    }
}
