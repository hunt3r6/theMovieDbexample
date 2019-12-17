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
import co.id.bismalabs.themoviedb.model.TvShow;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private Context mContext;
    private ArrayList<TvShow> listTvshow;
    private OnItemClickCallback onItemClickCallback;

    public TvAdapter(Context mContext, ArrayList<TvShow> listTvshow) {
        this.mContext = mContext;
        this.listTvshow = listTvshow;
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        TvShow tvShow = listTvshow.get(position);
        String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
        String poster = IMAGE_URL + tvShow.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(holder.poster);
        holder.judul.setText(tvShow.getTitle());
        holder.descripsi.setText(tvShow.getOverview());
        holder.releaseDate.setText(tvShow.getFirstAirDate());
        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClickDetail(view, listTvshow.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return listTvshow.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        TextView judul, descripsi, releaseDate;
        ImageView poster;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_tv);
            poster = itemView.findViewById(R.id.img_tv);
            descripsi = itemView.findViewById(R.id.tv_desTv);
            releaseDate = itemView.findViewById(R.id.tv_date_tv);
        }
    }

    public interface OnItemClickCallback {
        void onItemClickDetail(View  view, TvShow tvShow);
    }
}
