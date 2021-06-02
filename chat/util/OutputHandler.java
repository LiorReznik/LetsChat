package chat.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class OutputHandler extends IOHandler {
    private DataOutputStream stream;

    public OutputHandler(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void createStream() throws IOException {
        this.stream = new DataOutputStream(this.socket.getOutputStream());
    }

    @Override
    protected void handleStream() {
        Scanner scan = new Scanner(System.in);
        try {
            if(scan.hasNextLine()) {
                this.stream.writeUTF(scan.nextLine());
            }
        } catch (IOException ignored) { }
    }



}
