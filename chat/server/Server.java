package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT;
    private ServerSocket serverSocket;
    private Socket clientSocket;

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
        new Server(65535).start();

    }

    void start() {
        if (this.openListener()) {
            this.createClientHandler();
            this.takeBreak(400);
            this.closeSocket();
        }
    }

    private boolean openListener() {
        //todo: rethink maybe run in a loop till successes
        try {
            this.serverSocket = new ServerSocket(this.PORT);
            return true;
        } catch (IOException e) {
            System.err.println("Could not open server, exiting");
            return false;
        }
    }

    private void takeBreak(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception ignored) {
        }
    }

    private void closeSocket() {
        try {
            this.serverSocket.close();
        } catch (Exception ignored) {
        }
    }

    private void createClientHandler() {
        this.clientSocket = acceptConnection();
        if (this.clientSocket != null) {
            new ClientHandler(this.clientSocket).start();
        }
    }

    private Socket acceptConnection() {
        try {
            return this.serverSocket.accept();
        } catch (Exception ignored) {
        }
        return null;
    }
}
