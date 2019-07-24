package com.slavetny.github.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slavetny.github.R;
import com.slavetny.github.api.Repo;

import java.util.ArrayList;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    List<Repo> repoList = new ArrayList<>();

    public ReposAdapter(List<Repo> repoList) {
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        holder.bind(repoList.get(position));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {

        TextView repoName, repoDescription, repoLanguage;

        public ReposViewHolder(@NonNull View itemView) {
            super(itemView);

            repoName = itemView.findViewById(R.id.repoName);
            repoDescription = itemView.findViewById(R.id.repoDescription);
            repoLanguage = itemView.findViewById(R.id.repoLanguage);
        }
        void bind(Repo repoItem) {
            if (repoItem.getDescription() == null) {
                repoDescription.setVisibility(View.GONE);
            }
            repoName.setText(repoItem.getName());
            repoDescription.setText(repoItem.getDescription());
            repoLanguage.setText(repoItem.getLanguage());
        }
    }
}
