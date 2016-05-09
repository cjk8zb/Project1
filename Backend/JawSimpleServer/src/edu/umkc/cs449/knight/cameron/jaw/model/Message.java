package edu.umkc.cs449.knight.cameron.jaw.model;

import com.google.gson.annotations.SerializedName;

import static junit.framework.Assert.*;

import edu.umkc.cs449.knight.cameron.jaw.BuildConfig;

/**
 * Created by camjknight on 2/20/16.
 *
 * Class Invariant: System messages will not have a peer
 */
public class Message {
    @SerializedName("type")
    private final Type mType;
    @SerializedName("text")
    private final String mText;
    @SerializedName("peer")
    private final Peer mPeer;

    public Type getType() {
        return mType;
    }

    public String getText() {
        return mText;
    }

    public Peer getPeer() {
        if (BuildConfig.DEBUG) {
            assertFalse("System messages will not have a peer.", mType == Type.SYSTEM);
        }
        return mPeer;
    }

    public enum Type {SENT, RECEIVED, SYSTEM}

    public static class Builder {
        private final Type mType;
        private String mText;
        private Peer mPeer;

        public Builder(Type type) {
            mType = type;
        }

        public Builder text(String text) {
            mText = text;
            return this;
        }

        public Builder peer(Peer peer) {
            if (BuildConfig.DEBUG) {
                assertFalse("Peer should not be set for system messages.", mType == Type.SYSTEM);
            }
            mPeer = peer;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    private Message(Builder builder) {
        mType = builder.mType;
        mText = builder.mText;
        mPeer = builder.mPeer;
    }
}
