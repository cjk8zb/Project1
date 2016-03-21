package edu.umkc.cs449.knight.cameron.jaw;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.umkc.cs449.knight.cameron.jaw.model.NameGenerator;
import edu.umkc.cs449.knight.cameron.jaw.model.NonsenseGenerator;
import edu.umkc.cs449.knight.cameron.jaw.model.Peer;

/**
 * Created by camjknight on 3/20/16.
 */
public class RandomChat extends ChatProvider {
    private Handler mHandler = null;
    private Random mRandom = new Random();
    private boolean mConnected;
    private List<Peer> mFakePeers = new ArrayList<>();

    public RandomChat(ChatListener chatListener) {
        super(chatListener);
    }

    @Override
    public void connect(Peer peer) {
        mConnected = true;
        if (mChatListener != null) {
            mChatListener.onConnect(true);
        }
        queueEvent();
    }

    @Override
    public void disconnect() {
        mConnected = false;
        if (mChatListener != null) {
            mChatListener.onDisconnect();
        }
    }

    @Override
    public void sendText(String text) {
        // no-op, fake peers don't care what you have to say.
    }

    // 1 to 5 seconds
    private int getSleep() {
        return (1 + mRandom.nextInt(4)) * 1000;
    }

    private void queueEvent() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }

        int delay = getSleep();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                generateEvent();
            }
        }, delay);
    }

    private void addPeer() {
        String name = NameGenerator.generateName();
        Peer peer = new Peer(name);
        mFakePeers.add(peer);
        if (mChatListener != null) {
            mChatListener.onPeerAdded(peer);
        }
    }

    private Peer getRandomPeer() {
        return mFakePeers.get(mRandom.nextInt(mFakePeers.size()));
    }

    private void dropRandomPeer() {
        Peer peer = getRandomPeer();
        mFakePeers.remove(peer);
        if (mChatListener != null) {
            mChatListener.onPeerRemoved(peer);
        }
    }

    private void generateRandomMessage() {
        if (mChatListener != null) {
            Peer peer = getRandomPeer();
            String text = NonsenseGenerator.getInstance().makeSentence(false);
            mChatListener.onReceivedText(text, peer);
        }
    }

    private void generateEvent() {
        if (mFakePeers.size() == 0) {
            addPeer();
        }
        else {
            if (mRandom.nextInt(mFakePeers.size()) == 0) {
                if (mRandom.nextInt(4) == 0) {
                    dropRandomPeer();
                }
                else {
                    addPeer();
                }
            }
            else {
                generateRandomMessage();
            }
        }

        if (mConnected) {
            queueEvent();
        }
    }
}
