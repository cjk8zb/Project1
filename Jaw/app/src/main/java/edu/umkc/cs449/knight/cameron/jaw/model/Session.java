package edu.umkc.cs449.knight.cameron.jaw.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by camjknight on 2/20/16.
 */
public class Session {
    private static final String TAG = "Session";

    private List<Message> mMessages;
    private List<Peer> mConnectedPeers;
    private Peer mCurrentPeer;

    public Session(Peer currentPeer) {
        mCurrentPeer = currentPeer;
        mMessages = new ArrayList<Message>();
        mConnectedPeers = new ArrayList<Peer>();
    }

    public void join() {
        // FIXME: Add network handshake etc

        mMessages.add(new Message("You have joined the conversation."));
    }

    public void sendText(String text) {
        Message message = new Message(Message.Type.SENT, text, mCurrentPeer);
        mMessages.add(message);

        // Broadcast to all peers
        for (Peer peer : mConnectedPeers) {
            Log.d(TAG, "Sending message: " + text + "to peer: " + peer.getName());
        }
    }

    // FIXME: Remove after networking is implemented
    public void seedRandomData() {
        Random rand = new Random();

        mConnectedPeers.clear();
        for (int i = 0; i < 10; i++) {
            mConnectedPeers.add(new Peer());
        }

        mMessages.clear();
        for (int i = 0; i < 100; i++) {
            Message.Type type = Message.Type.values()[rand.nextInt(Message.Type.values().length)];

            Message message;
            switch (type) {
                case SENT:
                    message = new Message(type, "Sent Message #" + i, mCurrentPeer);
                    break;
                case RECEIVED:
                    message = new Message(type, "Received Message #" + i, mConnectedPeers.get(rand.nextInt(mConnectedPeers.size())));
                    break;
                default:
                    message = new Message("System Message #" + i);
            }

            mMessages.add(message);
        }
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public List<Peer> getConnectedPeers() {
        return mConnectedPeers;
    }

    public Peer getCurrentPeer() {
        return mCurrentPeer;
    }
}
