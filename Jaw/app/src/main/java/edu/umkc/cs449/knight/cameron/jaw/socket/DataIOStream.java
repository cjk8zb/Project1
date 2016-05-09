package edu.umkc.cs449.knight.cameron.jaw.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by camjknight on 4/24/16.
 */
public class DataIOStream  {
    final private Socket mSocket;
    final private DataInputStream mInputStream;
    final private DataOutputStream mOutputStream;

    public DataIOStream(Socket socket) throws IOException {
        mSocket = socket;
        mInputStream = new DataInputStream(mSocket.getInputStream());
        mOutputStream = new DataOutputStream(mSocket.getOutputStream());
    }

    public DataInputStream in() {
        return mInputStream;
    }

    public DataOutputStream out() {
        return mOutputStream;
    }

    public void close() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}