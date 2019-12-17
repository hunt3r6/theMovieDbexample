package co.id.bismalabs.themoviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import co.id.bismalabs.themoviedb.R;
import co.id.bismalabs.themoviedb.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviewViewHolder> {
    private Context mContext;
    private ArrayList<Movie> movieArrayList;
    private OnItemClickCallback onItemClickCallback;

    public MovieAdapter(Context mContext, ArrayList<Movie> movieArrayList) {
        this.mContext = mContext;
        this.movieArrayList = movieArrayList;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public MoviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviewViewHolder holder, int position) {
        String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
        Movie movie = movieArrayList.get(position);
        String poster = IMAGE_URL + movie.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(holder.poster);
        holder.judul.setText(movie.getTitle());
        holder.descripsi.setText(movie.getDescription());
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClickDetail(view, movieArrayList.get(holder.getAdapterPosition())));

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    class MoviewViewHolder extends RecyclerView.ViewHolder {
        TextView judul, descripsi, releaseDate;
        ImageView poster;
        MoviewViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_movie);
            descripsi = itemView.findViewById(R.id.tv_desMovie);
            releaseDate = itemView.findViewById(R.id.tv_date_movie);
            poster = itemView.findViewById(R.id.img_movie);
        }
    }

    public interface OnItemClickCallback {
      void onItemClickDetail(View  view, Movie movie);
    }
}
