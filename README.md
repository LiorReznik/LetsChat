# LetsChat
Just Another chat app!
this is a chat app (both server side and client-side logic).
this chat is implemented with the help of sockets and have the following capabilities:
1. group chat between a client and all the other clients that are connected to the server at the moment.
server side:
-the server opens a server socket and actively listens to incoming connection requests (till interrupted).
-the server opens a new data session that holds within the message history of the session and all the active users. 
-for each connection request the server opens a new client handler (socket on a new thread).
-the user being ask for its name, if the name does not exist in the current data session the server
adds the client handler alongside with its name to the data session, if the name already exists the server asks for another name.
-after that the user gets the 10 last messages that was sent.
-the user can send messages to the whole group of connected users.
-if the user enters "/exit" the socket on the client disconnected. at the server-side 
the socket disconnected and the client handler thread is removed from the active users.

in this project I have used the following programming principles:
-Socket Networking.
-IO streams.
-OOP and Inheritance.
-Multithreading.
-Generics (Collections).

future possible addons:
-private session between two users.
-store of information on DB (username and password for each user, history and so on).

