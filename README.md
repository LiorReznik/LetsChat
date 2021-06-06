# LetsChat
Just Another chat app!
this is a chat app (both server side and client-side logic).
this chat is implemented with the help of sockets and have the following capabilities:
1. group chat between a client and all the other clients that are connected to the server at the moment.

server side:
1. the server opens a server socket and actively listens to incoming connection requests (till interrupted).
2. the server opens a new data session that holds within the message history of the session and all the active users. 
3. for each connection request the server opens a new client handler (socket on a new thread).
4. the user being ask for its name, if the name does not exist in the current data session the server
adds the client handler alongside with its name to the data session, if the name already exists the server asks for another name.
5. after that the user gets the 10 last messages that was sent.
6. the user can send messages to the whole group of connected users.
7. to exit, the user should enter "/exit", afterwards the socket on the client will disconnect. and at the server-side 
the socket that associated with the client will disconnect as well and the client handler thread will be removed from the active users.

in this project I have used the following programming principles:
1. Socket Networking.
2. IO streams.
3. OOP and Inheritance.
4. Multithreading.


future possible addons:
1. private session between two users.
2. store of information on DB (username and password for each user, history and so on).

