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
    private final ChatSessionData sessionData;
    private String name;

    ClientHandler(Socket socket, ChatSessionData sessionData) throws NullPointerException {
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
        this.sessionData = sessionData;
        // this.onLogin();
    }

    private void onLogin() {
        this.sendMsg(this.formatMsg("write your name", true));
        this.setClientName();
        this.sendMsg(this.sessionData.getLastMessages(10));
    }

    private void setClientName() {
        while (!this.socket.isClosed()) {//while(true) {
            String name = this.inputChannel.read();
            if (name != null && this.sessionData.addUser(name, this)) {
                this.name = name;
                break;
            } else {
                this.sendMsg(this.formatMsg("this name is already taken! Choose another one.", true));
            }
        }

    }

    private String formatMsg(String msg, boolean isServer) {
        return String.format("%s: %s", isServer ? "Server" : this.name, msg);
    }

    void sendMsg(String msg) {
        this.outputChannel.write(msg);
    }

    private void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
    }

    private void chat() {
        while (!this.socket.isClosed()) {
            String msg = this.inputChannel.read();
            if (msg != null) {
                if ("/exit".equals(msg)) {
                    break;
                }
                this.sessionData.addAndSendmSG(this.formatMsg(msg, false));
            }

        }
    }

    private void onExit() {
        this.sessionData.removeUser(this.name);
        this.closeConnection();
    }

    @Override
    public void run() {
        this.onLogin();
        this.chat();
        this.onExit();
    }

}