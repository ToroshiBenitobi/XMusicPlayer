package org.annatv.musicplayer.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import org.annatv.musicplayer.R;

public class LocalFragment extends Fragment {

    private LocalViewModel localViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        localViewModel =
                new ViewModelProvider(this).get(LocalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_local, container, false);
        final TextView textView = root.findViewById(R.id.text_local);
        localViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}