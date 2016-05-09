package edu.umkc.cs449.knight.cameron.jaw;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.umkc.cs449.knight.cameron.jaw.view.IdenticonView;

/**
 * Created by camjknight on 2/21/16.
 */
public class ProfileFragment extends DialogFragment {
    public static final String ARG_NAME = "Name";
    private EditText mNameEditText;
    private IdenticonView mIdenticonView;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dialog_profile, null);
        mIdenticonView = (IdenticonView) view.findViewById(R.id.identiconView);
        mNameEditText = (EditText) view.findViewById(R.id.dialog_profile_name_edit_text);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateText(s);
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString(ARG_NAME);
            mNameEditText.setText(name);
        }
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_AlertDialog)
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

    @Override
    public void onStart() {
        super.onStart();
        updateText(mNameEditText.getText());
    }

    private void updateText(CharSequence text) {
        if (text == null) {
            text = "";
        }
        mIdenticonView.setText(text.toString());
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(text.length() > 0);
        }
    }

    private void finish() {
        ProfileFragmentListener listener = (ProfileFragmentListener) getActivity();
        listener.onFinishEditProfile(mNameEditText.getText().toString());
    }

    public interface ProfileFragmentListener {
        void onFinishEditProfile(String name);
    }
}
