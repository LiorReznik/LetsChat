package chat.server;

import chat.client.Client;
import chat.util.InputHandler;
import chat.util.OutputHandler;

import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread{
    private final Socket socket;
    
    ClientHandler(Socket socket) {
        this.socket = socket;
    }
    
    private void openIO() {
        try {
            new InputHandler(this.socket).start();
            new OutputHandler(this.socket).start();
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not open IO Streams, closing connection");
            this.closeConnection();
        }
    }
    
    private void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        this.openIO();
    }
    
    }
    


