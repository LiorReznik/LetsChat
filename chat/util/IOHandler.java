package chat.util;

import java.io.IOException;
import java.net.Socket;

public abstract class IOHandler extends Thread {
    protected boolean isOn;
    protected final Socket socket;
    
    IOHandler(Socket socket) throws IOException{
        this.socket = socket;
        this.isOn = this.socket == null ? false : true;
        this.createStream();
    }
    @Override
    public void run() {
        while(this.isOn && !this.socket.isClosed()) {
            this.handleStream();
        }
    }

    protected abstract void handleStream();

    protected abstract void createStream() throws IOException;

}
