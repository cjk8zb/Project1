package edu.umkc.cs449.knight.cameron.jaw.socket;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by camjknight on 4/24/16.
 */
class OutputThread extends Thread {
    private final DataOutputStream mDataOutputStream;
    private final Object mLock = new Object();
    private String mText = "";

    OutputThread(DataOutputStream dataOutputStream) {
        mDataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        try {

            //noinspection InfiniteLoopStatement
            while (true) {
                String text;
                synchronized(mLock) {
                    try {
                        mLock.wait();
                    } catch(InterruptedException ignored) {}
                    text = mText;
                }

                mDataOutputStream.writeUTF(text);
                mDataOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendText(String text) {
        synchronized(mLock) {
            mText = text;
            mLock.notify();
        }

    }
}
