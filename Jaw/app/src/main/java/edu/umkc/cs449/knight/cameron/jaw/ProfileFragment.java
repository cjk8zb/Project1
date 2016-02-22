package edu.umkc.cs449.knight.cameron.jaw;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by camjknight on 2/21/16.
 */
public class ProfileFragment extends DialogFragment {
    public static final String ARG_NAME = "Name";
    private static final String EXTRA_NAME = "Name";
    private EditText mNameEditText;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_profile, null);
        mNameEditText = (EditText) view.findViewById(R.id.dialog_profile_name_edit_text);

        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(ARG_NAME);
            mNameEditText.setText(name);
        }
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Profile")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
    }

    private void finish() {
        ProfileFragmentListener listener = (ProfileFragmentListener) getActivity();
        listener.onFinishEditProfile(mNameEditText.getText().toString());
    }

    public interface ProfileFragmentListener {
        void onFinishEditProfile(String name);
    }
}
