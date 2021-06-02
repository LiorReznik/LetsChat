package chat.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OutputHandler extends IOHandler {
    private DataOutputStream stream;

    /**
     * @param socket the socket that we want to handle its IO
     * @throws IOException          if couldn't open stream
     * @throws NullPointerException if the socket ref is null
     */
    public OutputHandler(Socket socket) throws IOException, NullPointerException {
        super(socket);
    }

    @Override
    protected void createStream() throws IOException {
        this.stream = new DataOutputStream(this.socket.getOutputStream());
    }

    /**
     * write message to the socket
     */
    @Deprecated
    protected void handleStream() {
        try {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLine()) {
                this.stream.writeUTF(scan.nextLine());
            }
        } catch (IOException ignored) {
        }
    }

    public void write(String msg) {
        try {
            if (msg != null) {
                this.stream.writeUTF(msg);
            }
        } catch (IOException e) {
        }
    }


}