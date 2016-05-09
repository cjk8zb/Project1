package edu.umkc.cs449.knight.cameron.jawsimpleserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.umkc.cs449.knight.cameron.jaw.model.Peer;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}

    static final int SocketServerPORT = 8080;

    String msgLog = "";

    List<ChatClient> userList;

    ServerSocket serverSocket;

    Server() {
        userList = new ArrayList<>();
        ChatServerThread chatServerThread = new ChatServerThread();
        chatServerThread.start();

    }

    private class ChatServerThread extends Thread {

        @Override
        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());
                    System.out.println("CTRL + C to quit");

                while (true) {
                    socket = serverSocket.accept();
                    ChatClient client = new ChatClient();
                    userList.add(client);
                    client.socket = socket;
                    
                    OutputThread outputThread = new OutputThread(client);
                    outputThread.start();
                    
                    InputThread inputThread = new InputThread(client);
                    inputThread.start();
                    // ConnectThread connectThread = new ConnectThread(client, socket);
                    // connectThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private class InputThread extends Thread {
        Socket socket;
        ChatClient connectClient;
        
        InputThread(ChatClient client) {
            connectClient = client;
            this.socket = client.socket;
            client.inputThread = this;
        }
        
        @Override
        public void run() {
            DataInputStream dataInputStream = null;
            Gson gson = new Gson();
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());

                String n = dataInputStream.readUTF();

//                connectClient.name = n;
                JsonElement jelement = new JsonParser().parse(n);
                JsonObject  jobject = jelement.getAsJsonObject();
                if (jobject.has("connect")) {
                    Peer peer = gson.fromJson(jobject.get("connect"), Peer.class);
                    connectClient.peer = peer;
                }
//                else if (jobject.has("disconnect")) {
//                    Peer peer = gson.fromJson(jobject.get("disconnect"), Peer.class);
//                    mListener.onPeerDisconnected(peer);
//                }
//                else if (jobject.has("message")) {
//                    Message message = gson.fromJson(jobject.get("message"), Message.class);
//                    mListener.onMessageReceived(message);
//                }
                
                System.out.println(connectClient.peer.getName() + " connected@"
                    + connectClient.socket.getInetAddress()
                    + ":" + connectClient.socket.getPort() + "\n");

                broadcastMsg(n);

                while (true) {
                    String newMsg = dataInputStream.readUTF();
                    System.out.print( n + ": " + newMsg);
                    broadcastMsg(newMsg);
                }

            } catch (EOFException e) {
                // Client left.
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                userList.remove(connectClient);

                System.out.println(connectClient.peer.getName() + " removed.");

                msgLog = "-- " + connectClient.peer.getName() + " leaved\n";
                System.out.println(msgLog);
                JsonElement je = gson.toJsonTree(connectClient.peer);
                JsonObject jo = new JsonObject();
                jo.add("disconnect", je);
                String disc = jo.toString();
                broadcastMsg(disc);
            }
        }
    }

    private class OutputThread extends Thread {
        Object lock = new Object();
        Socket socket;
        String msgToSend = "";

        OutputThread(ChatClient client) {
            this.socket = client.socket;
            client.outputThread = this;
        }

        @Override
        public void run() {
            DataOutputStream dataOutputStream = null;

            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    synchronized(lock) {
                        try {
                            lock.wait();
                        } catch(InterruptedException e) {}
                    }
                    
                    dataOutputStream.writeUTF(msgToSend);
                    dataOutputStream.flush();
                    msgToSend = "";
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        private void sendMsg(String msg) {
            msgToSend = msg;
            synchronized(lock) {
                lock.notify();
            }
            
        }

    }

    private void broadcastMsg(String msg) {
        for (int i = 0; i < userList.size(); i++) {
            userList.get(i).outputThread.sendMsg(msg);
            msgLog = "- send to " + userList.get(i).peer.getName() + "\n";
            System.out.print(msgLog);
        }
        System.out.println();
        
    }

    class ChatClient {
    	Peer peer;
        Socket socket;
        InputThread inputThread;
        OutputThread outputThread;
    }

}
