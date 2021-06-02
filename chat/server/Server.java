package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT;
    private ServerSocket serverSocket; //TODO: make final?

    {
        System.out.println("Server started!");
    }

    private Server() {
        this(65535);
    }

    private Server(int port) {
        this.PORT = port >= 0 || port <= 65535 ? port : 65535;
    }

    public static void main(String[] args) {
        //TODO: Add args from command line and renter option if one of them is not working good
        new Server(65535).start(5000);

    }

    void start(int time) {
        if (this.openListener(time)) {
            this.createClientHandler();
        }
    }

    private boolean openListener(int time) {
        //todo: rethink maybe run in a loop till successes
        try {
            this.serverSocket = new ServerSocket(this.PORT);
            this.serverSocket.setSoTimeout(time);
            return true;
        } catch (IOException e) {
            System.err.println("Could not open server, exiting");
            return false;
        }
    }

    @Deprecated
    private void takeBreak(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
        }
    }

    public void closeSocket() {
        try {
            this.serverSocket.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * the method is activity listening to the network and waiting for connection requests.
     * when it such request it accepts the connection if it possible.
     */
    private void createClientHandler() {
        while (!this.serverSocket.isClosed()) {
            Socket clientSocket = acceptConnection();
            if (clientSocket != null) {
                new ClientHandler(clientSocket).start();
            }
        }
    }

    private Socket acceptConnection() {
        try {
            return this.serverSocket.accept();
        } catch (Exception ק) {
            this.closeSocket();
        }
        return null;
    }
}
