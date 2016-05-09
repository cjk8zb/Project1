package edu.umkc.cs449.knight.cameron.jaw.socket;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import edu.umkc.cs449.knight.cameron.jaw.model.Message;
import edu.umkc.cs449.knight.cameron.jaw.model.Peer;

/**
 * Created by camjknight on 4/10/16.
 */
public class Client implements InputThread.Listener {
    private static final String TAG = "Client";
    private static final int PORT = 8080;
    private static final String ADDRESS = "10.0.1.13";
    private final Peer mPeer;
    private final ClientListener mListener;
    private final Gson mGson = new Gson();
    private final JsonParser mJsonParser = new JsonParser();

    private DataIOStream mDataIOStream;
    private OutputThread mOutputThread;

    public Client(Peer peer, ClientListener listener) {
        mPeer = peer;
        mListener = listener;
    }

    public void start() {
        new DataIOStreamTask().execute();
    }

    private void startThreads(DataIOStream dataIOStream) {
        if (dataIOStream == null) {
            mListener.onFailedConnection();
            return;
        }
        mDataIOStream = dataIOStream;
        InputThread inputThread = new InputThread(mDataIOStream.in(), this);
        mOutputThread = new OutputThread(mDataIOStream.out());
        inputThread.start();
        mOutputThread.start();
        String handshake = JSONWrapper.wrap("connect", mPeer);
        mOutputThread.sendText(handshake);
    }

    public void sendMessage(Message message) {
        String json = JSONWrapper.wrap("message", message);
        mOutputThread.sendText(json);
    }

    public void disconnect() {
        if (mDataIOStream != null) {
            mDataIOStream.close();
        }
        mDataIOStream = null;
    }

    @Override
    public void receivedText(String text) {
        JsonElement jsonElement = mJsonParser.parse(text);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("connect")) {
            Peer peer = mGson.fromJson(jsonObject.get("connect"), Peer.class);
            mListener.onPeerConnected(peer);
        } else if (jsonObject.has("disconnect")) {
            Peer peer = mGson.fromJson(jsonObject.get("disconnect"), Peer.class);
            mListener.onPeerDisconnected(peer);
        } else if (jsonObject.has("message")) {
            Message message = mGson.fromJson(jsonObject.get("message"), Message.class);
            mListener.onMessageReceived(message);
        } else {
            Log.d(TAG, "Unrecognized payload: " + text);
        }
    }

    public interface ClientListener {
        void onFailedConnection();
        void onPeerConnected(Peer peer);
        void onPeerDisconnected(Peer peer);
        void onMessageReceived(Message message);
    }

    private class DataIOStreamTask extends AsyncTask<Void, Void, DataIOStream> {

        @Override
        protected DataIOStream doInBackground(Void... params) {
            try {
                InetSocketAddress address = new InetSocketAddress(ADDRESS, PORT);
                Socket socket = new Socket();
                socket.connect(address, 3000);
                return new DataIOStream(socket);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(DataIOStream dataIOStream) {
            startThreads(dataIOStream);
        }
    }
}
