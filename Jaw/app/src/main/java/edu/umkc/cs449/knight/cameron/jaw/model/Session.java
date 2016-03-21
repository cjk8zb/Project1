package edu.umkc.cs449.knight.cameron.jaw.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.umkc.cs449.knight.cameron.jaw.ChatProvider;
import edu.umkc.cs449.knight.cameron.jaw.RandomChat;

/**
 * Created by camjknight on 2/20/16.
 */
public class Session implements ChatProvider.ChatListener {
    private static final String TAG = "Session";
    private ChatProvider mProvider;

    private List<Message> mMessages;
    private List<Peer> mConnectedPeers;
    private Peer mCurrentPeer;

    public interface SessionListener {
        public void onMessagesUpdated(Message lastMessage);
    }
    private SessionListener mSessionListener;

    public Session(Peer currentPeer, SessionListener listener) {
        mCurrentPeer = currentPeer;
        mMessages = new ArrayList<Message>();
        mConnectedPeers = new ArrayList<Peer>();
        mProvider = new RandomChat(this);
        mSessionListener = listener;
    }

    public void join() {
        mProvider.connect(mCurrentPeer);
    }

    public void sendText(String text) {
        Message message = new Message(Message.Type.SENT, text, mCurrentPeer);
        addMessage(message);
        mProvider.sendText(text);
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

    private void addMessage(Message message) {
        mMessages.add(message);
        if (mSessionListener != null) {
            mSessionListener.onMessagesUpdated(message);
        }
    }

    @Override
    public void onConnect(boolean success) {
        if (success) {
            addMessage(new Message("You have joined the conversation."));
        } else {
            addMessage(new Message("Unable to join the conversation."));
        }
    }

    @Override
    public void onDisconnect() {
        addMessage(new Message("You have left the conversation."));
    }

    @Override
    public void onReceivedText(String text, Peer fromPeer) {
        addMessage(new Message(Message.Type.RECEIVED, text, fromPeer));
    }

    @Override
    public void onPeerAdded(Peer peer) {
        mConnectedPeers.add(peer);
        addMessage(new Message(peer.getName() + " has joined the conversation."));
    }

    @Override
    public void onPeerRemoved(Peer peer) {
        mConnectedPeers.remove(peer);
        addMessage(new Message(peer.getName() + " has left the conversation."));
    }
}
