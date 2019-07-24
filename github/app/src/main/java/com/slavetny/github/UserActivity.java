package com.slavetny.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.slavetny.github.adapters.ReposAdapter;
import com.slavetny.github.api.GithubApi;
import com.slavetny.github.api.Repo;
import com.slavetny.github.db.App;
import com.slavetny.github.db.AppDatabase;
import com.slavetny.github.db.User;
import com.slavetny.github.db.UsersDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    private TextView name;
    private CircleImageView userAvatar;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private Toolbar toolbar;
    private GithubApi githubApi;
    private boolean onBookmark;
    private List<Repo> repoList = new ArrayList<>();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        name = findViewById(R.id.userName);
        userAvatar = findViewById(R.id.userAvatar);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        githubApi = retrofit.create(GithubApi.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        name.setText(bundle.getString("user_login"));
        Picasso.get().load(bundle.getString("user_avatar")).into(userAvatar);
        onBookmark = bundle.getBoolean("onBookmark");

        final Call<List<Repo>> getRepos = githubApi.getUserRepos(bundle.getString("user_login"));
        getRepos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                repoList.addAll(response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
                recyclerView.setHasFixedSize(true);
                ReposAdapter reposAdapter = new ReposAdapter(repoList);
                recyclerView.setAdapter(reposAdapter);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (onBookmark) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_bookmark, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                AddToDatabase addToDatabase = new AddToDatabase();
                addToDatabase.execute();
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    class AddToDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = App.getInstance().getDatabase();
            UsersDao usersDao = db.usersDao();
            User user = new User();
            user.userName = bundle.getString("user_login");
            user.avatarUrl = bundle.getString("user_avatar");
            usersDao.insert(user);
            return null;
        }
    }
}

