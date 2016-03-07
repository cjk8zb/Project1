package edu.umkc.cs449.knight.cameron.jaw;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.umkc.cs449.knight.cameron.jaw.model.Message;
import edu.umkc.cs449.knight.cameron.jaw.view.IdenticonView;

/**
 * Created by camjknight on 3/6/16.
 */


public abstract class MessageHolder extends RecyclerView.ViewHolder {


    public MessageHolder(View itemView) {
        super(itemView);
    }

    public static MessageHolder getInstance(LayoutInflater layoutInflater, ViewGroup parent, Message.Type type) {

        switch (type) {
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

    public abstract void bindMessage(Message message);

    private static class SentMessageHolder extends MessageHolder {
        private TextView mMessageTextView;

        public SentMessageHolder(View itemView) {
            super(itemView);
            RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.sent_message_list_item_relative_layout);
            DrawableUtil.applyTint(itemView.getContext(), layout.getBackground(), R.attr.colorAccent);
            mMessageTextView = (TextView) itemView.findViewById(R.id.sent_message_list_item_message_text_view);
        }

        @Override
        public void bindMessage(Message message) {
            mMessageTextView.setText(message.getText());
        }
    }

    private static class ReceivedMessageHolder extends MessageHolder {
        private TextView mMessageTextView;
        private TextView mPeerTextView;
        private IdenticonView mIdenticonView;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            mMessageTextView = (TextView) itemView.findViewById(R.id.received_message_list_item_message_text_view);
            mPeerTextView = (TextView) itemView.findViewById(R.id.received_message_list_item_peer_text_view);
            mIdenticonView = (IdenticonView) itemView.findViewById(R.id.identiconView);
        }

        @Override
        public void bindMessage(Message message) {
            mMessageTextView.setText(message.getText());
            mPeerTextView.setText(message.getPeer().getName());
            mIdenticonView.setText(message.getPeer().getName());
        }
    }

    private static class SystemMessageHolder extends MessageHolder {
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

}

