package edu.umkc.cs449.knight.cameron.jaw.model;

import java.util.UUID;

/**
 * Created by camjknight on 2/20/16.
 */
public class Peer {
    private String mName;
    private UUID mUUID;

    public Peer(String name) {
        mName = name;
        mUUID = UUID.randomUUID();
    }

    public String getName() {
        return mName;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
