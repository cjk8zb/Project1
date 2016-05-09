package edu.umkc.cs449.knight.cameron.jaw.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.umkc.cs449.knight.cameron.jaw.ChatProvider;
import static edu.umkc.cs449.knight.cameron.jaw.AppSettings.*;
/**
 * Created by camjknight on 2/20/16.
 */
public class Session implements ChatProvider.ChatListener {
    private static final String TAG = "Session";
    private final ChatProvider mProvider;

    private final List<Message> mMessages;
    private final Message.Builder mSendMessageBuilder = new Message.Builder(Message.Type.SENT);
    private final Message.Builder mSystemMessageBuilder = new Message.Builder(Message.Type.SYSTEM);
    private final Message.Builder mReceiveMessageBuilder = new Message.Builder(Message.Type.RECEIVED);
    private Peer mCurrentPeer;

    public interface SessionListener {
        void onMessagesUpdated();
    }
    private final SessionListener mSessionListener;

    public Session(Peer currentPeer, SessionListener listener, ChatProvider provider) {
        mCurrentPeer = currentPeer;
        mMessages = new ArrayList<>();
        mSessionListener = listener;
        if (provider == null) {
            mProvider = ChatProviders.SOCKET.getInstance(this);
        }
        else {
            mProvider = provider;
        }
    }

    public Session(Peer currentPeer, SessionListener listener) {
        this(currentPeer, listener, null);
    }

    public void join() {
        mProvider.connect(mCurrentPeer);
    }

    public void disconnect() {
        mProvider.disconnect();
    }

    public void sendText(String text) throws Exception {
        mProvider.sendText(text);
        Message message = mSendMessageBuilder.text(text).peer(mCurrentPeer).build();
        addMessage(message);
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setCurrentPeer(Peer peer) {
        disconnect();
        mCurrentPeer = peer;
        join();
    }

    public Peer getCurrentPeer() {
        return mCurrentPeer;
    }

    private void addMessage(Message message) {
        mMessages.add(message);
        if (mSessionListener != null) {
            mSessionListener.onMessagesUpdated();
        }
    }

    @Override
    public void onConnect(boolean success) {
        Log.d(TAG, "Connected: " + success);
        String text = success ? "You have joined the conversation." : "Unable to join the conversation.";
        Message message = mSystemMessageBuilder.text(text).build();
        addMessage(message);
    }

    @Override
    public void onDisconnect() {
        Message message = mSystemMessageBuilder.text("You have left the conversation.").build();
        addMessage(message);
    }

    @Override
    public void onReceivedText(String text, Peer fromPeer) {
        Message message = mReceiveMessageBuilder.text(text).peer(fromPeer).build();
        addMessage(message);
    }

    @Override
    public void onPeerAdded(Peer peer) {
        String text = peer.getName() + " has joined the conversation.";
        Message message = mSystemMessageBuilder.text(text).build();
        addMessage(message);
    }

    @Override
    public void onPeerRemoved(Peer peer) {
        String text = peer.getName() + " has left the conversation.";
        Message message = mSystemMessageBuilder.text(text).build();
        addMessage(message);
    }
}
