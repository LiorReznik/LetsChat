package chat.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * a class that handles the input stream of a socket
 * this class gets a message from the socket and prints it to the screen
 */
public class InputHandler extends IOHandler {
    private DataInputStream stream;

    /**
     * @param socket the socket that we want to handle its IO
     * @throws IOException          if couldn't open stream
     * @throws NullPointerException if the socket ref is null
     */
    public InputHandler(Socket socket) throws IOException, NullPointerException {
        super(socket);
    }

    @Override
    protected void createStream() throws IOException {
        this.stream = new DataInputStream(this.socket.getInputStream());
    }

    /**
     * the method prints the message that was obtained from the socket to the standard output
     */
    @Override
    protected void handleStream() {
        String msg = this.read();
        if (msg != null) {
            System.out.println(msg);
        }
    }

    /**
     * the method tries to read a message from the stream,
     * if the message was successfully obtained the method returns it
     * if not the method returns null
     *
     * @return message
     */
    private String read() {
        String msg = null;
        try {
            msg = this.stream.readUTF();
        } catch (IOException ignored) {
        }
        return msg;
    }
}