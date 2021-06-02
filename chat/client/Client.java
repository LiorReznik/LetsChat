
package chat.client;

import chat.util.InputHandler;
import chat.util.OutputHandler;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String ip;
    private int port;

    public static void main(final String[] args) {
        //TODO: Add args from command line
        new Client("127.0.0.1",65535).manage();
    }

    private Client(final String ip,final int port) {
        this.ip = ip;
        this.port = port;
        System.out.println("Client started!");

    }
    private boolean connectToServer(){
        try {
            this.socket = new Socket(this.ip,this.port);
            return true;
        } catch (Exception e) {
            // TODO: rethink? add try again functionality
            System.err.println("Failed to connect to the server");
            return false;
        }
    };
    private boolean openIO() {
        try {
            new InputHandler(this.socket).start();
            new OutputHandler(this.socket).start();
            return true;
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not open IO Streams, closing connection");
            this.closeConnection();
            return false;
        }
    }
    private void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException e) { }
    }
    private void takeBreak(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) { }
    }
    private void manage() {
        if (this.connectToServer()) {
            if (this.openIO()) {
                this.takeBreak(400);
                this.closeConnection();
            }
        }
    }
}