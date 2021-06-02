package chat.server;

import chat.util.InputHandler;
import chat.util.OutputHandler;

import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {
    private static int numOfUsers = 0;
    private final Socket socket;
    private final int CLIENT_ID;
    private final InputHandler inputChannel;
    private final OutputHandler outputChannel;

    ClientHandler(Socket socket) throws NullPointerException {
        if (socket == null) {
            throw new NullPointerException("socket is null");
        }
        InputHandler in = null;
        OutputHandler out = null;
        this.socket = socket;
        this.CLIENT_ID = ++numOfUsers;
        try {
            in = new InputHandler(this.socket);
            out = new OutputHandler(this.socket);
        } catch (IOException e) {
            System.err.println("Could not open IO streams, closing the connection");
            this.closeConnection();
        }
        this.inputChannel = in;
        this.outputChannel = out;
        this.showConnectionInfo("Client %d connected!%n");
    }

    private static int wordsCounter(String msg) {
        return msg == null ? 0 : msg.split(" ").length;
    }

    @Deprecated
    private void openIO() {
        try {
            new InputHandler(this.socket);
            new OutputHandler(this.socket);
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not open IO Streams, closing connection");
            this.closeConnection();
        }
    }

    private void closeConnection() {
        try {
            this.socket.close();
            this.showConnectionInfo("Client %d disconnected!%n");
        } catch (IOException ignored) {
        }
    }

    private String formatMsgForClient(String msg) {
        int wordCount = wordsCounter(msg);
        return String.format("Count is %d%n", wordCount);
    }

    private void showConnectionInfo(String msg) {
        System.out.printf(msg, this.CLIENT_ID);
    }

    private void showMsgFromAndToClient(String msgFromClient, String msgToClient) {
        System.out.printf("Client %d sent: %s%n", this.CLIENT_ID, msgFromClient);
        System.out.printf("Sent to client %d: %s", this.CLIENT_ID, msgToClient);
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()) {
            String msg = this.inputChannel.read();
            if (msg != null) {
                if ("/exit".equals(msg)) {
                    this.closeConnection();
                    break;
                }
                String toClient = formatMsgForClient(msg);
                this.outputChannel.write(toClient);
                this.showMsgFromAndToClient(msg, toClient);
            }
        }
    }


}