package edu.umkc.cs449.knight.cameron.jaw.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by camjknight on 2/20/16.
 */
public class Peer {
    @SerializedName("name")
    private final String mName;

    @SerializedName("uuid")
    private final UUID mUUID;

    public Peer(String name) {
        mName = name;
        mUUID = UUID.randomUUID();
    }

    public String getName() {
        return mName;
    }

    private UUID getUUID() {
        return mUUID;
    }

    @Override
    public boolean equals(Object o) {
        Peer other;

        if (o instanceof Peer) {
            other = (Peer) o;
        }
        else {
            return false;
        }

        return this.getUUID().equals(other.getUUID())
                && this.getName().equals(other.getName());
    }
}
