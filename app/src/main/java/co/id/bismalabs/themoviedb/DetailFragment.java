package co.id.bismalabs.themoviedb;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvTitle, tvDate, tvDescription;
    ImageView imgMovie;
    private static String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tv_title);
        tvDate = view.findViewById(R.id.tv_date);
        tvDescription = view.findViewById(R.id.tv_des);
        imgMovie = view.findViewById(R.id.img_detail);
        String type = DetailFragmentArgs.fromBundle(getArguments()).getType();
        if (type.equals("movie")) {
            String title = DetailFragmentArgs.fromBundle(getArguments()).getTitle();
            String date = DetailFragmentArgs.fromBundle(getArguments()).getDate();
            String desc = DetailFragmentArgs.fromBundle(getArguments()).getDescription();
            String img = URL_IMAGE + DetailFragmentArgs.fromBundle(getArguments()).getImage();

            tvTitle.setText(title);
            tvDate.setText(date);
            tvDescription.setText(desc);

            Glide.with(this)
                    .load(img)
                    .into(imgMovie);

        } else if (type.equals("tv")) {
            String title = DetailFragmentArgs.fromBundle(getArguments()).getTitle();
            String date = DetailFragmentArgs.fromBundle(getArguments()).getDate();
            String desc = DetailFragmentArgs.fromBundle(getArguments()).getDescription();
            String img = URL_IMAGE + DetailFragmentArgs.fromBundle(getArguments()).getImage();

            tvTitle.setText(title);
            tvDate.setText(date);
            tvDescription.setText(desc);

            Glide.with(this)
                    .load(img)
                    .into(imgMovie);
        }

    }
}
