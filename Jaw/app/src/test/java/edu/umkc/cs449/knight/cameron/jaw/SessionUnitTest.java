package edu.umkc.cs449.knight.cameron.jaw;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.umkc.cs449.knight.cameron.jaw.model.Peer;
import edu.umkc.cs449.knight.cameron.jaw.model.Session;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SessionUnitTest {
    enum Events {
        CONNECT, DISCONNECT;
    }

    private class TestProvider extends ChatProvider {

        List<Events> events = new ArrayList<>();
        Peer peer = null;

        protected TestProvider(ChatListener chatListener) {
            super(chatListener);
        }

        @Override
        public void connect(Peer peer) {
            this.peer = peer;
            events.add(Events.CONNECT);
        }

        @Override
        public void disconnect() {
            events.add(Events.DISCONNECT);
        }

        @Override
        public void sendText(String text) throws Exception {

        }
    }

    @Test
    public void settingPeerClosesConnection() throws Exception {
        TestProvider provider = new TestProvider(null);
        Peer oldPeer = new Peer("old");
        Session session = new Session(oldPeer, null, provider);
        Peer newPeer = new Peer("new");
        session.setCurrentPeer(newPeer);
        assertTrue(provider.events.contains(Events.DISCONNECT));
    }

    @Test
    public void settingPeerChangesPeer() throws Exception {
        TestProvider provider = new TestProvider(null);
        Peer oldPeer = new Peer("old");
        Session session = new Session(oldPeer, null, provider);
        Peer newPeer = new Peer("new");
        session.setCurrentPeer(newPeer);
        assertTrue(session.getCurrentPeer() == newPeer);
    }

    @Test
    public void settingPeerReOpensConnection() throws Exception {
        TestProvider provider = new TestProvider(null);
        Peer oldPeer = new Peer("old");
        Session session = new Session(oldPeer, null, provider);
        Peer newPeer = new Peer("new");
        session.setCurrentPeer(newPeer);
        assertTrue(provider.events.contains(Events.CONNECT));
    }

    @Test
    public void settingPeerClosesThenOpensConnection() throws Exception {
        TestProvider provider = new TestProvider(null);
        Peer oldPeer = new Peer("old");
        Session session = new Session(oldPeer, null, provider);
        Peer newPeer = new Peer("new");
        session.setCurrentPeer(newPeer);
        assertTrue(provider.events.size() == 2);
        assertTrue(provider.events.get(0) == Events.DISCONNECT);
        assertTrue(provider.events.get(1) == Events.CONNECT);
    }

    @Test
    public void peerChangesBeforeReOpeningConnection() throws Exception {
        TestProvider provider = new TestProvider(null);
        Peer oldPeer = new Peer("old");
        Session session = new Session(oldPeer, null, provider);
        Peer newPeer = new Peer("new");
        session.setCurrentPeer(newPeer);
        assertTrue(provider.peer == newPeer);
    }

}