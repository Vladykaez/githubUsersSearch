package com.slavetny.github.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slavetny.github.db.App;
import com.slavetny.github.db.AppDatabase;
import com.slavetny.github.R;
import com.slavetny.github.db.User;
import com.slavetny.github.db.UsersDao;
import com.slavetny.github.adapters.UserAdapter;

import java.util.List;

public class BookmarkFragment extends Fragment {

    private List<User> userList;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        AppDatabase db = App.getInstance().getDatabase();
        UsersDao usersDao = db.usersDao();

        userList = usersDao.getAll();

        UserAdapter userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        return view;
    }
}
