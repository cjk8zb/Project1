package edu.umkc.cs449.knight.cameron.jaw.socket;

import android.os.Handler;

import edu.umkc.cs449.knight.cameron.jaw.ChatProvider;
import edu.umkc.cs449.knight.cameron.jaw.model.Message;
import edu.umkc.cs449.knight.cameron.jaw.model.Peer;

/**
 * Created by camjknight on 4/10/16.
 */
public class Connection extends ChatProvider implements Client.ClientListener {
    private Handler mHandler = null;
    private Peer mPeer;
    private Client mClient;

    public Connection(ChatListener chatListener) {
        super(chatListener);
    }

    @Override
    public void connect(final Peer peer) {
        mHandler = new Handler();
        mPeer = peer;
        mClient = new Client(peer, this);
        mClient.start();
    }

    @Override
    public void disconnect() {
        if (mClient != null) {
            mClient.disconnect();
            mClient = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
        mChatListener.onDisconnect();
    }

    @Override
    public void sendText(String text) throws Exception {
        if (mClient == null) {
            throw new Exception("Attepting to sendText before connected.");
        }
        Message message = new Message.Builder(Message.Type.RECEIVED).text(text).peer(mPeer).build();
        mClient.sendMessage(message);
    }

    @Override
    public void onFailedConnection() {
        mClient = null;
        mChatListener.onConnect(false);
    }

    @Override
    public void onPeerConnected(final Peer peer) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPeer.equals(peer)) {
                    mChatListener.onConnect(true);
                } else {
                    mChatListener.onPeerAdded(peer);
                }
            }
        });
    }

    @Override
    public void onPeerDisconnected(final Peer peer) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (peer == mPeer) {
                    disconnect();
                }
                else {
                    mChatListener.onPeerRemoved(peer);
                }
            }
        });
    }

    @Override
    public void onMessageReceived(final Message message) {
        if (message.getPeer().equals(mPeer)) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mChatListener.onReceivedText(message.getText(), message.getPeer());
            }
        });
    }
}
