package edu.umkc.cs449.knight.cameron.jaw;

import edu.umkc.cs449.knight.cameron.jaw.model.Peer;

/**
 * Created by camjknight on 3/20/16.
 */
public abstract class ChatProvider {

    protected ChatProvider(ChatListener chatListener) {
        mChatListener = chatListener;
    }

    public interface ChatListener {
        void onConnect(boolean success);
        void onDisconnect();
        void onReceivedText(String text, Peer fromPeer);
        void onPeerAdded(Peer peer);
        void onPeerRemoved(Peer peer);
    }

    protected final ChatListener mChatListener;

    public abstract void connect(Peer peer);
    public abstract void disconnect();
    // throws an Exception when there is not an established connection
    public abstract void sendText(String text) throws Exception;

}
