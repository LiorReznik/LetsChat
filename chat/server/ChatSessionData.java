package chat.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * a class to store all the data regarding to an active chat session
 * it includes all the clients that take part in the chat and  the last messages
 * also it includes functionality such as show last messages
 * check if name is allraedy exists
 */
class ChatSessionData {
    //private final List<ClientHandler> clients;
    private final Map<String, ClientHandler> clients = new Hashtable<>();
    private final List<String> messages = new ArrayList<>();//(10);

    private boolean doesUsernameExists(String name) {
        return this.clients.containsKey(name);
    }

    boolean addUser(String name, ClientHandler client) {
        synchronized (this.clients) {
            if (!this.doesUsernameExists(name)) {
                this.clients.put(name, client);
                return true;
            }
            return false;
        }
    }

    void removeUser(String name) {
        this.clients.remove(name);
    }

    private void brodecastMessage(String msg) {
        this.clients.forEach((n, u) -> u.sendMsg(msg));
    }

    private void addMessage(String msg) {
        //  synchronized(messages) {
        this.messages.add(msg);
        //  }
    }

    synchronized void addAndSendmSG(String msg) {
        if (msg != null) {
            //TODO: rethink move brodcast to the clienHandler
            this.addMessage(msg);
            this.brodecastMessage(msg);
        }
    }

    /**
     * a method to return the last n messages
     *
     * @param numOfMessages the amount of messges to be returned
     * @return last n messages
     */
    synchronized String getLastMessages(int numOfMessages) {
//        int size = this.messages.size();
//        if (size >= numOfMessages) {
//            return this.messages.subList(numOfMessages, size);
//        } return new ArrayList<>(this.messages);
        int size = this.messages.size();
        int i = size >= numOfMessages ? size - numOfMessages : 0;
        StringBuilder temp = new StringBuilder();
        for (; i < size; i++) {
            temp.append(this.messages.get(i)).append("\n");
        }
        return temp.toString();
    }


}
