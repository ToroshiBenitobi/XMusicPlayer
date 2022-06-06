package org.annatv.musicplayer.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.annatv.musicplayer.R;
import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 homeViewPager = view.findViewById(R.id.homeViewPager);
        homeViewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                Log.d(TAG, "createFragment: createFragment " + position);
                switch (position) {
                    case 0:
                        return new SongFragment();
                    case 1:
                        return new AlbumFragment();
                    default:
                        return new ArtistFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        TabLayout homeTabLayout= view.findViewById(R.id.homeTabLayout);
        new TabLayoutMediator(homeTabLayout, homeViewPager,
                (tab, position) -> {
                    Log.d("1", "onViewCreated: "+ position);
                    switch (position) {
                        case 0:
                            tab.setText("Song");
                            break;
                        case 1:
                            tab.setText("Album");
                            break;
                        default:
                            tab.setText("Artist");
                            break;
                    }
                }
        ).attach();

    }


}
