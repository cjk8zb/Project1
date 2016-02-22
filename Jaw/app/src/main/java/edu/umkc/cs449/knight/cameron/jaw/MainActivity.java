package edu.umkc.cs449.knight.cameron.jaw;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.umkc.cs449.knight.cameron.jaw.model.Message;
import edu.umkc.cs449.knight.cameron.jaw.model.Peer;
import edu.umkc.cs449.knight.cameron.jaw.model.Session;

public class MainActivity extends AppCompatActivity implements ProfileFragment.ProfileFragmentListener {
    private static final String TAG = "MainActivity";
    private static final String PROFILE = "Profile";
    private static final String PEER_NAME = "PeerName";

    private RecyclerView mMessageRecyclerView;
    private MessageAdapter mMessageAdapter;
    private Button mSendButton;
    private EditText mMessageEditText;
    private Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = (EditText) findViewById(R.id.activity_main_message_edit_text);
        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mMessageEditText.getText().toString();
                if (text.length() > 0) {
                    mSession.sendText(text);
                    mMessageEditText.setText(null);
                    mMessageAdapter.notifyDataSetChanged();
                    scrollToHead();
                }
            }
        });
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.message_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String peerName = sharedPref.getString(PEER_NAME, null);
        if (peerName != null) {
            createSession(peerName);
        } else {
            showProfile();
        }
    }

    private void scrollToHead() {
        mMessageRecyclerView.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);
    }

    private void createSession(String peerName) {
        mSession = new Session(new Peer(peerName));
        mMessageAdapter = new MessageAdapter();
        mMessageRecyclerView.setAdapter(mMessageAdapter);
        mSession.join();
    }

    private void showProfile() {
        FragmentManager manager = getSupportFragmentManager();
        ProfileFragment dialog = new ProfileFragment();
        if (mSession != null) {
            Bundle args = new Bundle();
            args.putString(ProfileFragment.ARG_NAME, mSession.getCurrentPeer().getName());
            dialog.setArguments(args);
        }
        dialog.show(manager, PROFILE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            applyMenuColor(actionBar.getThemedContext(), menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                showProfile();
                return true;
            case R.id.action_debug: {
                if (mSession == null) {
                    return false;
                }
                mSession.seedRandomData();
                mMessageAdapter.notifyDataSetChanged();
                scrollToHead();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyMenuColor(Context context, Menu menu) {
        if (context == null || menu == null) {
            return;
        }

        final TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)) {
            return;
        }
        Integer textColorId = typedValue.resourceId;

        for (int i = 0; i < menu.size(); ++i) {
            MenuItem menuItem = menu.getItem(i);
            Drawable drawable = menuItem.getIcon();
            if (drawable == null) {
                continue;
            }

            drawable = DrawableCompat.wrap(drawable);
            drawable.mutate();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                DrawableCompat.setTint(drawable, getResources().getColor(textColorId, null));
            } else {
                //noinspection deprecation
                DrawableCompat.setTint(drawable, getResources().getColor(textColorId));
            }
            menuItem.setIcon(drawable);
        }
    }

    @Override
    public void onFinishEditProfile(String name) {
        if (name != null) {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            sharedPref.edit().putString(PEER_NAME, name).apply();
            createSession(name);
        }
    }

    private abstract class MessageHolder extends RecyclerView.ViewHolder {
        public MessageHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindMessage(Message message);
    }

    private class SentMessageHolder extends MessageHolder {
        private TextView mMessageTextView;

        public SentMessageHolder(View itemView) {
            super(itemView);
            mMessageTextView = (TextView) itemView.findViewById(R.id.sent_message_list_item_message_text_view);
        }

        @Override
        public void bindMessage(Message message) {
            mMessageTextView.setText(message.getText());
        }
    }

    private class ReceivedMessageHolder extends MessageHolder {
        private TextView mMessageTextView;
        private TextView mPeerTextView;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            mMessageTextView = (TextView) itemView.findViewById(R.id.received_message_list_item_message_text_view);
            mPeerTextView = (TextView) itemView.findViewById(R.id.received_message_list_item_peer_text_view);
        }

        @Override
        public void bindMessage(Message message) {
            mMessageTextView.setText(message.getText());
            mPeerTextView.setText(message.getPeer().getName());
        }
    }

    private class SystemMessageHolder extends MessageHolder {
        private TextView mMessageTextView;

        public SystemMessageHolder(View itemView) {
            super(itemView);
            mMessageTextView = (TextView) itemView.findViewById(R.id.system_message_list_item_message_text_view);
        }

        @Override
        public void bindMessage(Message message) {
            mMessageTextView.setText(message.getText());
        }


    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            switch (Message.Type.values()[viewType]) {
                case SENT: {
                    View view = layoutInflater.inflate(R.layout.sent_message_list_item, parent, false);
                    return new SentMessageHolder(view);
                }
                case RECEIVED: {
                    View view = layoutInflater.inflate(R.layout.received_message_list_item, parent, false);
                    return new ReceivedMessageHolder(view);
                }
                case SYSTEM: {
                    View view = layoutInflater.inflate(R.layout.system_message_list_item, parent, false);
                    return new SystemMessageHolder(view);
                }
                default:
            }

            return null;
        }

        @Override
        public void onBindViewHolder(MessageHolder holder, int position) {
            Message message = mSession.getMessages().get(position);
            holder.bindMessage(message);
        }

        @Override
        public int getItemViewType(int position) {
            return mSession.getMessages().get(position).getType().ordinal();
        }

        @Override
        public int getItemCount() {
            return mSession.getMessages().size();
        }
    }
}
