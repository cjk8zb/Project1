# <del>Peer to Peer to Peer Proximity</del> *Client/Server* Based Group Messaging (Jaw app)
## Cameron Knight
### UMKC CS449

***

## Revision History

|Author|Date|Revision|
|---|---|---|
|Knight|2016-02-05|Initial release|

***

## Table of Contents

- [Vision Statement](#vision-statement)
- [Requirements](#requirements)
    - [Step #1: Identify Categories of Users](#step-1-identify-categories-of-users)
    - [Step #2: Create Actor-Goal List](#step-2-create-actor-goal-list)
    - [Step #3: Identify User Stories](#step-3-identify-user-stories)
    - [Step #4: Write Use Cases](#vision-statement)
- [Iteration #1](#iteration-1)
- [Iteration #1](#iteration-2)
- [Design](#design)
- [Coding Standards](#coding-standards)
- [Project Summary and Retrospective](#project-summary-and-retrospective)

***

## Vision Statement

For the tech-savvy introvert who would rather have a text conversation than interact with a human in the real word **Jaw** bridges the gap. **Jaw** allows you to talk to anyone at a party, without all the social awkwardness (like eye contact). <del>Unlike conventional messaging apps, **Jaw** only works with other **Jaw** users in your immediate proximity.</del> **Jaw** works just like conventional messaging apps.

## Requirements

### Step #1: Identify Categories of Users

**Participant** - A user who actively sends and receives messages<del>, or passively relays messages to other participants</del>.

### Step #2: Create Actor-Goal List

|Actor|Goal|
|---|---|
|Participant|Send messages to all other engaged participants|
||*Potentially* see a list of all participants|
||Receive messages from all participant|
||*Potentially* send/receive direct messages from particular participants|
||<del>Passively extend the reach of communication between participants</del>|

### Step #3: Identify User Stories

|Story ID|Story                                                                                |Story Points|Priority|Status|
|:------:|-------------------------------------------------------------------------------------|:----------:|:------:|-----:|
|      S1|Participants will choose usernames to identify themselves.                           |           5|       1|      |
|      S2|Participants can also be identified by an avatar image.                              |           4|       2|      |
|      S3|Participant can enter a message.                                                     |           3|       3|      |
|      S4|Message should be sent to all other participants.                                    |           6|       4|      |
|      S5|Participant can see a history of past messages.                                      |           3|       5|      |
|      S6|Participant can see system messages.                                                 |           2|       6|      |
|      S7|System messages will show when participants join the conversation.                   |           2|       7|      |
|      S8|System messages should show when participants leave the conversation.                |           2|       8|      |
|      S9|Participants will receive messages from all other participants.                      |           4|       9|      |
|     <del>S10|<del>App will use Central to listen for advertisement from Peripherals.                   |           8|      10|      |
|     <del>S11|<del>App running on compatible devices will use Peripheral to advertise to other devices. |           7|      11|      |
|     <del>S12|<del>Central devices will connect to a Peripheral device to send and receive messages.    |          13|      12|      |
|     <del>S13|<del>A Peripheral device will connect to multiple Central devices.                        |           3|      13|      |
|     <del>S14|<del>A Peripheral device will send participant list to Central devices.                   |          15|      14|      |
|     <del>S15|<del>A Peripheral device will relay messages to Central devices.                          |          16|      15|      |
|     S16|As a participant, I would like to send direct messages to specific participants.     |           3|      16|      |
|     <del>S17|<del>The proximity space should increase as more participants join.                       |          18|      17|      |

### Step #4: Write Use Cases

---

**Title:** Join the conversation  
**Use case ID:** UC001  
**Actor:** Participant  
**Description:** This is the initial action all participants must take to join a conversation

**Basic Flow**

1. Upon app launch, other participants are automatically found and connected.
2. Participant can begin send messages to all other participants.
3. Other participant messages will appear to the participant.

**Alternative Flow**

1. No other participants are found.
    - The app show a message indicating no other participants.
    - The app waits for other participants to join.

**Exceptions**

1. Connection cannot be made with other participants.
    - Connection should automatically attempt to reconnect.

**Open issues**

1. How are participants identified?
    - Automatically generated ID?
    - User entered handle/username?
    - Avatar picture?

---

## Iteration #1

|Story ID|Task|Estimated Hours|Actual Hours|
|:-:|---|---|---|
|S1|Design UI|5|7|
||Develop model|8|5|
||Implement network layer|16||
|S2|Design message listener|8||
|S4|Implement send/receive/repeat message model|16||
||Use network topology to find fastest path between participants|16||

<img src="Screenshots/Iteration1-Empty.png" width="360" alt="Empty">
<img src="Screenshots/Iteration1-Profile.png" width="360" alt="Profile">
<img src="Screenshots/Iteration1-Seeded.png" width="360" alt="Seeded">

### Review Meeting

- UI was demoed to the client.
- Client didn't like the color scheme (it should be brighter).
- Generally the UI was well received.

### Retrospective

Time was spent on implementing the UI layer. Instead of designing UI mockups ahead of time, design happened at the same time as implementation. This approach added some extra time because of the waisted time implementing UI elements that were later removed, but with my unfamiliarity with front-end Android code it helped guard against designing unimplementable elements.

I might need to retcon the story, since the Story Plan doesn't accurately represent the time spent during this iteration.

Project Velocity: 0

## Iteration #2

<img src="Screenshots/Iteration2-Empty.png" width="360" alt="Empty">
<img src="Screenshots/Iteration2-Profile.png" width="360" alt="Profile">
<img src="Screenshots/Iteration2-Profile-filled.png" width="360" alt="Profile Filled">
<img src="Screenshots/Iteration2-Seeded.png" width="360" alt="Seeded">

Changed color scheme to a lighter blue (at clients request).

Added automatically generated profile images (Identicons).

## Iteration #3

<img src="Screenshots/Iteration3-UML.png" width="661" alt="Empty">

Due to current time requirements, the original design goal of the app to support a BLE Mesh network does not seem to be feasible. Fortunately, the app has been design to be network agnostic (the communication implementation does not affect any other aspects of the app). Therefore, pivoting to a traditional Client/Server implementation will require minimal effort, and will leave the option of implementing a BLE Mesh connection as a potential future task.

Also data seeding has been refactored into a ChatProvider with random data to better mimic a real connection.

## Iteration #4

<img src="Screenshots/Iteration4-Random.png" width="360" alt="Random">
<img src="Screenshots/Iteration4-FailedSend.png" width="360" alt="Failed Send">

Seed debug data button has been removed since that feature was removed in the previous iteration.

Attempting to send a message before a connection is established now throws an exception.

Assertions were added to the Message class to ensure that the peer is not set or retrieved for system messages (doing so is considered a programming error, the behavior is undefined).

## Iteration #5

We are adding a feature to allow the user to change his/her profile name `Session.setCurrentPeer`:

<img src="Screenshots/Iteration5-Code0.png" width="310" alt="Code">

-

<img src="Screenshots/Iteration5-Test1.png" width="478" alt="Test">
<img src="Screenshots/Iteration5-Test1-Fail.png" width="943" alt="Fail">
<img src="Screenshots/Iteration5-Code1.png" width="318" alt="Code">
<img src="Screenshots/Iteration5-Test1-Pass.png" width="944" alt="Pass">

-

<img src="Screenshots/Iteration5-Test2.png" width="478" alt="Test">
<img src="Screenshots/Iteration5-Test2-Fail.png" width="956" alt="Fail">
<img src="Screenshots/Iteration5-Code2.png" width="313" alt="Code">
<img src="Screenshots/Iteration5-Test2-Pass.png" width="919" alt="Pass">

-

<img src="Screenshots/Iteration5-Test3.png" width="498" alt="Test">
<img src="Screenshots/Iteration5-Test3-Fail.png" width="950" alt="Fail">
<img src="Screenshots/Iteration5-Code3.png" width="343" alt="Code">
<img src="Screenshots/Iteration5-Test3-Pass.png" width="941" alt="Pass">

-

<img src="Screenshots/Iteration5-Test4.png" width="539" alt="Test">
<img src="Screenshots/Iteration5-Test4-Fail.png" width="938" alt="Fail">
<img src="Screenshots/Iteration5-Code4.png" width="339" alt="Code">
<img src="Screenshots/Iteration5-Test4-Pass.png" width="938" alt="Pass">

-

<img src="Screenshots/Iteration5-Test5.png" width="533" alt="Test">
<img src="Screenshots/Iteration5-Test5-Fail.png" width="939" alt="Fail">
<img src="Screenshots/Iteration5-Code5.png" width="344" alt="Code">
<img src="Screenshots/Iteration5-Test5-Pass.png" width="935" alt="Pass">

## Iteration #6

The Socket Connection Client class had numerous areas in need of refactoring. Primarily, the class had too many responsibilities (socket creation, input, output, response handling, etc.).

The refactoring separated many of the responsibilities into separate classes:

- DataIOStreamTask: AsyncTask used to create the socket
- DataIOStream: Tuple class use to pass input streams from task to client.
- InputThread: Thread dedicated to receiving data.
- OutputThread: Thread dedicated to sending data.

```java
public class Client extends Thread {
    private static final String TAG = "Client";
    private static final int PORT = 8080;
    private static final String ADDRESS = "10.0.1.13";
    private final Peer mPeer;
    private final ClientListener mListener;
    private String messageToSend = "";
    private boolean connected = false;
    private final Gson mGson = new Gson();
    private final JsonParser mJsonParser = new JsonParser();
    
    public Client(Peer peer, ClientListener listener) {
        mPeer = peer;
        mListener = listener;
    }

    @Override
    public void run() {
        Socket socket = null;
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;
        try {
            InetSocketAddress address = new InetSocketAddress(ADDRESS, PORT);
            socket = new Socket();
            socket.connect(address, 3000);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            String handshake = JSONWrapper.wrap("connect", mPeer);
            outputStream.writeUTF(handshake);
            outputStream.flush();
            
            connected = true;
            while(connected) {
                if (inputStream.available() > 0) {
                    text = inputStream.readUTF();
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
                
                if (messageToSend.length() > 0) {
                    outputStream.writeUTF(handshake);
                    outputStream.flush();
                    messageToSend = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    private void sendText(String text) {
        messageToSend = text;
    }

    public void sendMessage(Message message) {
        String json = JSONWrapper.wrap("message", message);
        sendText(json);
    }

    public void disconnect() {
        connected = false;
    }

    public interface ClientListener {
        void onFailedConnection();
        void onPeerConnected(Peer peer);
        void onPeerDisconnected(Peer peer);
        void onMessageReceived(Message message);
    }
}
```

```java
public class Client implements InputThread.Listener {
    private static final String TAG = "Client";
    private static final int PORT = 8080;
    private static final String ADDRESS = "10.0.1.13";
    private final Peer mPeer;
    private final ClientListener mListener;
    private DataIOStream mDataIOStream;
    private OutputThread mOutputThread;
    private final Gson mGson = new Gson();
    private final JsonParser mJsonParser = new JsonParser();

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
        try {
            mDataIOStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void close() throws IOException {
        mSocket.close();
        mOutputStream.close();
        mInputStream.close();
    }
}

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
```

## Design

<del>The networking design will use an iterative design to incrementally increase the range of communication. Initial design should focus on peer to peer communication (1 to 1). The network model should then focus on hub and spoke, allowing one node to act as the host and all others as clients. Finally, the model will act as a mesh network, with each node acting simultaneously as a client, host, and repeater.</del>

## Coding Standards

Follow standard Java/Android coding standards. See <https://source.android.com/source/code-style.html>

## Project Summary and Retrospective

