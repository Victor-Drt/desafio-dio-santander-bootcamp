package me.dio.futnews.data.remote;

import java.util.List;

import me.dio.futnews.domain.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FutNewsApi {

    @GET("news.json")
    Call<List<News>> getNews();

}
