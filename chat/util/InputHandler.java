package chat.util;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputHandler extends IOHandler {
    private DataInputStream stream;

    public InputHandler(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void createStream() throws IOException {
        this.stream = new DataInputStream(this.socket.getInputStream());
    }
    @Override
    protected void handleStream() {
        System.out.println(this.read());

    }

    private String read() {
        String msg = null;
        try {
            msg = this.stream.readUTF();
        } catch (IOException ignored) { }
        return msg;
    }
}