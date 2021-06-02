package chat.server;

import chat.util.InputHandler;
import chat.util.OutputHandler;

import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket socket;

    ClientHandler(Socket socket) throws NullPointerException {
        if (socket != null) {
            this.socket = socket;
        } else {
            throw new NullPointerException("socket is null");
        }

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
        } catch (IOException ignored) {
        }
    }

    @Override
    public void run() {
        this.openIO();
    }


}