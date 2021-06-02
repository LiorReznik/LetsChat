package chat.util;

import java.io.IOException;
import java.net.Socket;

/**
 * abstract class to handle IO of a socket
 */
public abstract class IOHandler {
    protected final Socket socket;

    /**
     * @param socket the socket that we want to handle its IO
     * @throws IOException          if couldn't open stream
     * @throws NullPointerException if the socket ref is null
     */
    IOHandler(Socket socket) throws IOException, NullPointerException {
        if (socket != null) {
            this.socket = socket;
            this.createStream();
        } else {
            throw new NullPointerException("Socket ref is null");
        }
    }

    @Deprecated
    protected abstract void handleStream();

    protected abstract void createStream() throws IOException;

}
