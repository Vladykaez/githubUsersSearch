package com.slavetny.github.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavetny.github.R;
import com.slavetny.github.UserActivity;
import com.slavetny.github.api.UsersItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    List<UsersItem> usersList = new ArrayList<>();

    public UsersAdapter(List<UsersItem> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(usersList.get(position));
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView userName;
        String user_login, user_avatar;

        public UsersViewHolder(@NonNull final View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.userPhoto);
            userName = itemView.findViewById(R.id.userName);
            final Bundle bundle = new Bundle();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), UserActivity.class);
                    bundle.putString("user_login", user_login);
                    bundle.putString("user_avatar", user_avatar);
                    bundle.putBoolean("onBookmark", true);
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
        void bind(UsersItem usersItem) {
            user_login = usersItem.getLogin();
            user_avatar = usersItem.getAvatarUrl();

            Picasso.get().load(usersItem.getAvatarUrl()).into(circleImageView);
            userName.setText(usersItem.getLogin());
        }
    }
}
