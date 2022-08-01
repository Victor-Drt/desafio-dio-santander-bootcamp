package me.dio.futnews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.futnews.R;
import me.dio.futnews.databinding.NewsItemBinding;
import me.dio.futnews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final NewsListener favoriteListener;
    private List<News> news;

    public NewsAdapter(List<News> news, NewsListener favoriteListener) {

        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext(); //contexto

        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.getTitle());
        holder.binding.tvDescription.setText(news.getDescription());
        Picasso.get().load(news.getImage())
                .fit()
                .into(holder.binding.ivThumbnail);

//        Funcionalidade de "Compartilhar"
        holder.binding.ivShare.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, news.getTitle());
            i.putExtra(Intent.EXTRA_TEXT, news.getLink());
            context.startActivity(Intent.createChooser(i, "Compartilhar via:"));
        });


//        Funcionalidade de "Abrir Link"
        holder.binding.btOpenLink.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(news.getLink()));
                    context.startActivity(i);
                }
        );

//        Funcionalidade de "Favoritar" (o evento sera instanciado pelo Fragment)
        holder.binding.ivFavorite.setOnClickListener(view -> {
            news.setFavorite(!news.isFavorite());
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });

        int favoriteColor = news.isFavorite() ? R.color.favorite_active : R.color.favorite_inactive;
        holder.binding.ivFavorite.setColorFilter(context.getResources().getColor(favoriteColor));
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final NewsItemBinding binding;

        public ViewHolder(@NonNull NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface NewsListener {
        void onFavorite(News news);
    }

}
