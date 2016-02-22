package edu.umkc.cs449.knight.cameron.jaw.model;

/**
 * Created by camjknight on 2/20/16.
 */
public class Message {
    private Type mType;
    private String mText;
    private Peer mPeer;

    public Message(Type type, String text, Peer peer) {
        mType = type;
        mText = text;
        mPeer = peer;
    }

    public Message(String text) {
        mType = Type.SYSTEM;
        mText = text;
        mPeer = null;
    }

    public Type getType() {
        return mType;
    }

    public String getText() {
        return mText;
    }

    public Peer getPeer() {
        return mPeer;
    }

    public enum Type {SENT, RECEIVED, SYSTEM}

}
