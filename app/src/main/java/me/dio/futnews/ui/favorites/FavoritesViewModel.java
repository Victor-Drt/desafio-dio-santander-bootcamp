package me.dio.futnews.ui.favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.dio.futnews.data.FutNewsRepository;
import me.dio.futnews.domain.News;

public class FavoritesViewModel extends ViewModel {


    public FavoritesViewModel() {

    }

    public LiveData<List<News>> loadFavoriteNews() {
        final LiveData<List<News>> news;
        news = FutNewsRepository.getInstance().getLocalDb().newsDao().loadFavoriteNews();
        return news;
    }

    public void saveNews(News news) {
        AsyncTask.execute(() -> {
            FutNewsRepository.getInstance().getLocalDb().newsDao().save(news);
        });
    }
}