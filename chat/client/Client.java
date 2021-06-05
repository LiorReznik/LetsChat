package chat.client;

import chat.util.InputHandler;
import chat.util.OutputHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Client {
    private static final Pattern IPv4_PATTERN =
            Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    private final Scanner scan = new Scanner(System.in);
    private Socket socket; //TODO: final?
    private InputHandler inputChannel; //TODO: final?
    private OutputHandler outputChannel; //TODO: final?

    {
        System.out.println("Client started!");
    }

    private Client(String ip, int port) {
        if (this.openConnection(ip, port)) {
            new Thread(this::handleInput).start();
            this.handleOutput();
        }
    }

    public static void main(String[] args) {
        //TODO: Add args from command line and renter option if one of them is not working good
        new Client("127.0.0.1", 65535);
    }

    private String getMsgFromStandardInput() {
        if (this.scan.hasNext()) {
            return this.scan.nextLine();
        }
        return null;
    }

    private void sendMsg(String msg) {
        if (msg != null) {
            this.outputChannel.write(msg);
        }
    }

    private void handleInput() {
        while (!this.socket.isClosed()) {
            String msg = this.inputChannel.read();
            if (msg != null) {
                System.out.println(msg);
            }
        }

    }

    private void handleOutput() {
        while (!this.socket.isClosed()) {
            String msg = getMsgFromStandardInput();
            this.sendMsg(msg);
            if ("/exit".equals(msg)) {
                this.closeConnection();
            }
        }
    }


    /**
     * method to manage the client from the connection establishment and till closing
     *
     * @param ip   -> the ip address of the server that we want to connect with
     * @param port -> the port within the server
     */
    private boolean openConnection(String ip, int port) {
        if (this.validatePort(port) && this.validateIp(ip)) {
            return this.connectToServer(ip, port) && this.openIO();
        }
        return false;
    }

    /**
     * setter for ip , the method sets  the ip only if it is a valid ipv4 address
     *
     * @param ip the address of the server
     * @return true if the operation was successful or false otherwise
     */
    private boolean validateIp(String ip) {
        if (ip != null && IPv4_PATTERN.matcher(ip).matches()) {
            return true;
        }
        System.err.println("Non valid ip address, exiting");
        return false;
    }

    /**
     * setter for port, the method checks if the port within the right range, if so sets it
     *
     * @param port the port of the remoted server that we want to connect to (valid port should be with in [0,65535]
     * @return true if the operation was successful or false otherwise
     */
    private boolean validatePort(int port) {
        if (port >= 0 && port <= 65535) {
            return true;
        }
        System.err.println("Non valid port, exiting");
        return false;
    }

    /**
     * the method tries to Establish connection with the given server
     *
     * @return true if the connection was established or false if the given ip or port isn't right
     */
    private boolean connectToServer(String ip, int port) {
        boolean result;
        while (true) {
            try {
                this.socket = new Socket(ip, port);
                result = true;
                break;
            } catch (UnknownHostException e) {
                System.err.println("the server ip or port is wrong, please check this");
                result = false;
                break;
            } catch (IOException e) {
                System.err.println("Could not connect, retrying in 1 minute");
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return result;
    }

    /**
     * the method tries to open IO streams, if it is not successful the method closing the connection (socket)
     *
     * @return true if the operation was successful or false otherwise
     */
    private boolean openIO() {
        try {
            this.inputChannel = new InputHandler(this.socket);
            this.outputChannel = new OutputHandler(this.socket);
            return true;
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not open IO Streams, closing connection");
            this.closeConnection();
            return false;
        }
    }

    /**
     * method to close the connection with the server
     */
    private void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
    }


}