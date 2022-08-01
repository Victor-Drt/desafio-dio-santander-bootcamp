package me.dio.futnews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import me.dio.futnews.databinding.FragmentNewsBinding;
import me.dio.futnews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//        passar as informacoes da lista para o adapter
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        observeNews();
        observeStates();

        binding.srlNews.setOnRefreshListener(newsViewModel::findNews);

        return root;
    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
//                    TODO Inicia Swiperefresh layout
                    binding.srlNews.setRefreshing(true);
                    break;
                case DONE:
//                    TODO Finaliza Swiperefresh layout
                    binding.srlNews.setRefreshing(false);
                    break;
                case ERROR:
//                    TODO Finaliza Swiperefresh layout
                    binding.srlNews.setRefreshing(false);
//                    TODO Mostrar um erro generico
                    Snackbar.make(binding.srlNews, "Network error", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter((new NewsAdapter(news, newsViewModel::saveNews)));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}