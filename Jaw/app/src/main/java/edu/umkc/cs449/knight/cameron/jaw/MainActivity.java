package edu.umkc.cs449.knight.cameron.jaw;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import edu.umkc.cs449.knight.cameron.jaw.model.Message;
import edu.umkc.cs449.knight.cameron.jaw.model.Peer;
import edu.umkc.cs449.knight.cameron.jaw.model.Session;

public class MainActivity extends AppCompatActivity implements ProfileFragment.ProfileFragmentListener {
    private static final String TAG = "MainActivity";
    private static final String PROFILE = "Profile";
    private static final String PEER_NAME = "PeerName";

    private RecyclerView mMessageRecyclerView;
    private MessageAdapter mMessageAdapter;
    private ImageButton mSendButton;
    private EditText mMessageEditText;
    private Session mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = (EditText) findViewById(R.id.activity_main_message_edit_text);
        mSendButton = (ImageButton) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mMessageEditText.getText().toString();
                if (text.length() > 0) {
                    mSession.sendText(text);
                    mMessageEditText.setText(null);
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
        if (false && peerName != null) {
            createSession(peerName);
        } else {
            showProfile();
        }
    }

    private void scrollToHead() {
        mMessageRecyclerView.smoothScrollToPosition(mMessageAdapter.getItemCount() - 1);
    }

    private void createSession(String peerName) {
        mMessageAdapter = new MessageAdapter();
        mSession = new Session(new Peer(peerName), new Session.SessionListener() {
            @Override
            public void onMessagesUpdated(Message lastMessage) {
                mMessageAdapter.notifyDataSetChanged();
                scrollToHead();
            }
        });
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
            for (int i = 0; i < menu.size(); ++i) {
                MenuItem menuItem = menu.getItem(i);
                DrawableUtil.applyTint(actionBar.getThemedContext(), menuItem.getIcon(), android.R.attr.textColorPrimary);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                showProfile();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditProfile(String name) {
        if (name != null) {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            sharedPref.edit().putString(PEER_NAME, name).apply();
            createSession(name);
        }
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            return MessageHolder.getInstance(layoutInflater, parent, Message.Type.values()[viewType]);
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
