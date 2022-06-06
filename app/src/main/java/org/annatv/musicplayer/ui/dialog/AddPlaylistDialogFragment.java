package org.annatv.musicplayer.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import org.annatv.musicplayer.R;
import org.jetbrains.annotations.NotNull;

public class AddPlaylistDialogFragment extends AppCompatDialogFragment {
    AddPlaylistDialogListener listener;
    EditText editText;

    public AddPlaylistDialogFragment(AddPlaylistDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    public EditText getEditText() {
        return editText;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        editText = new EditText(getActivity());
        editText.setHint(R.string.enter_playlist_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_playlist)
                .setView(editText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(AddPlaylistDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(AddPlaylistDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    public interface AddPlaylistDialogListener {
        public void onDialogPositiveClick(AppCompatDialogFragment dialog);

        public void onDialogNegativeClick(AppCompatDialogFragment dialog);
    }
}
