package chat.util;

import java.io.IOException;
import java.net.Socket;

/**
 * abstract class to handle IO of a socket
 */
public abstract class IOHandler extends Thread {
    protected final Socket socket;
    protected boolean isOn;

    /**
     * @param socket the socket that we want to handle its IO
     * @throws IOException          if couldn't open stream
     * @throws NullPointerException if the socket ref is null
     */
    IOHandler(Socket socket) throws IOException, NullPointerException {
        this.socket = socket;
        this.isOn = this.socket != null;
        if (this.isOn) {
            this.createStream();
        } else {
            throw new NullPointerException("Socket ref is null");
        }
    }

    @Override
    public void run() {
        while (this.isOn && !this.socket.isClosed()) {
            this.handleStream();
        }
    }

    protected abstract void handleStream();

    protected abstract void createStream() throws IOException;

}
