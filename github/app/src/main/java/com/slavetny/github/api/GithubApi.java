package com.slavetny.github.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("users")
    Call<Users> getUsers(@Query("q") String userName);

    @GET("{username}/repos")
    Call<List<Repo>> getUserRepos(@Path("username") String username);
}
