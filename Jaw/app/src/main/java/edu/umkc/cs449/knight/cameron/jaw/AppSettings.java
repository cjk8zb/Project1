package edu.umkc.cs449.knight.cameron.jaw;

/**
 * Created by camjknight on 4/10/16.
 */
public class AppSettings {
    public enum ChatProviders {
        RANDOM, SOCKET;

        public ChatProvider getInstance(ChatProvider.ChatListener listener) {
            switch (this) {
                case RANDOM:
                    return new edu.umkc.cs449.knight.cameron.jaw.random.Connection(listener);
                case SOCKET:
                    return new edu.umkc.cs449.knight.cameron.jaw.socket.Connection(listener);
            }

            return null;
        }
    }
}
