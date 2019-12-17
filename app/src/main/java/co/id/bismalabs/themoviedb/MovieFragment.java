package co.id.bismalabs.themoviedb;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.bismalabs.themoviedb.adapter.MovieAdapter;
import co.id.bismalabs.themoviedb.model.Movie;
import co.id.bismalabs.themoviedb.viewmodel.MovieViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private String api_key = "d7af969ef1c44cd8d11fb3492a09bde4";
    private String language = Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry(); //ID
    private static String TAG = "MoviewFragment";
    private RecyclerView recyclerViewMovie;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + language);
    }

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewMovie = view.findViewById(R.id.rvMovie);
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMovie.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMovie.setNestedScrollingEnabled(false);
        movieAdapter = new MovieAdapter(getContext(), movieArrayList);
        recyclerViewMovie.setAdapter(movieAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::loadMovie);
        loadMovie();
    }

    private void loadMovie() {
        MovieViewModel movieViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        movieViewModel.setMoview(api_key, language);
        movieViewModel.getMovie().observe(getActivity(), movies -> {
            List<Movie> movieList = movies;
            movieArrayList.clear();
            movieArrayList.addAll(movieList);
            movieAdapter = new MovieAdapter(getContext(), movieArrayList);
            recyclerViewMovie.setAdapter(movieAdapter);
            recyclerViewMovie.setHasFixedSize(true);
            movieAdapter.notifyDataSetChanged();
            movieAdapter.setOnItemClickCallback((this::detail));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void detail(View view, Movie movie) {
        MovieFragmentDirections.ActionNavigationMovieToNavigationDetail toNavigationDetail = MovieFragmentDirections.actionNavigationMovieToNavigationDetail();
        toNavigationDetail.setType("movie");
        toNavigationDetail.setDate(movie.getReleaseDate());
        toNavigationDetail.setDescription(movie.getDescription());
        toNavigationDetail.setTitle(movie.getTitle());
        toNavigationDetail.setImage(movie.getPosterPath());
        Navigation.findNavController(view).navigate(toNavigationDetail);
    }
}
