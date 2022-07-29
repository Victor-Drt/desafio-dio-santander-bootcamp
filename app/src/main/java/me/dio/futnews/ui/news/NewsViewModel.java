package me.dio.futnews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.dio.futnews.domain.News;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        List<News> news = new ArrayList<>();
        news.add(new News("Manaus Empata!", "Jogo foi realizado nesta segunda feira."));
        news.add(new News("Amazonas Busca Vitoria!", "Time aurinegro joga hoje pela serie C."));
        news.add(new News("Dia de Decisão!", "São Paulo vai até Minas em busca da classificação."));

        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return this.news;
    }
}