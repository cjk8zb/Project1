package edu.umkc.cs449.knight.cameron.jaw.socket;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by camjknight on 4/24/16.
 */
class InputThread extends Thread {
    private final DataInputStream mDataInputStream;
    private final Listener mListener;
    public InputThread(DataInputStream dataInputStream, Listener listener) {
        mDataInputStream = dataInputStream;
        mListener = listener;
    }

    public interface Listener {
        void receivedText(String text);
    }
    @Override
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                String input = mDataInputStream.readUTF();
                mListener.receivedText(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}