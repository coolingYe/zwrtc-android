package com.zwrtc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.zwrtc.R;
import com.zwrtc.model.Users;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Users> usersList;

    public RoomAdapter(List<Users> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomViewHolder(parent.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.tvUserName.setText(usersList.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private org.webrtc.SurfaceViewRenderer playerView;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.player);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }
    }
}
