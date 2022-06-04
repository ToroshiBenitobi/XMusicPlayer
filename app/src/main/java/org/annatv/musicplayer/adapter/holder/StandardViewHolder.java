package org.annatv.musicplayer.adapter.holder;

import android.os.Build;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import org.annatv.musicplayer.databinding.ItemStandardBinding;
import org.jetbrains.annotations.NotNull;

public abstract class StandardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public PopupMenu popupMenu = null;

    public StandardViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public abstract int getMenuRes();

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }


}
