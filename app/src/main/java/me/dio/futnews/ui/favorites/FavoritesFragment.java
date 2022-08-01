package me.dio.futnews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import me.dio.futnews.MainActivity;
import me.dio.futnews.databinding.FragmentFavoritesBinding;
import me.dio.futnews.domain.News;
import me.dio.futnews.ui.adapter.NewsAdapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoriteViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        loadFavoriteNews(favoriteViewModel);


        View root = binding.getRoot();


        return root;
    }

    private void loadFavoriteNews(FavoritesViewModel favoriteViewModel) {
        favoriteViewModel.loadFavoriteNews().observe(getViewLifecycleOwner(), localNews -> {
            binding.rvFavoriteNews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavoriteNews.setAdapter((new NewsAdapter(localNews, updatedNews -> {
                favoriteViewModel.saveNews(updatedNews);
                loadFavoriteNews(favoriteViewModel);
            })));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}