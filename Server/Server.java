package NodeBook.Server;

public class Server {
    public static void main(String[] args) {
        AddServer addServer = new AddServer();
        SearchServer server = new SearchServer();
        addServer.start();
        server.start();
    }
}