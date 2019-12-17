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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.id.bismalabs.themoviedb.adapter.TvAdapter;
import co.id.bismalabs.themoviedb.model.TvShow;
import co.id.bismalabs.themoviedb.viewmodel.TvViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private String api_key = "d7af969ef1c44cd8d11fb3492a09bde4";
    private String language = Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    private RecyclerView recyclerViewTvShow;
    private TvAdapter tvAdapter;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewTvShow = view.findViewById(R.id.rvTv);
        recyclerViewTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTvShow.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTvShow.setNestedScrollingEnabled(false);
        tvAdapter = new TvAdapter(getContext(), tvShowArrayList);
        recyclerViewTvShow.setAdapter(tvAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::loadTv);
        loadTv();
    }

    private void loadTv() {
        TvViewModel tvViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(TvViewModel.class);
        tvViewModel.setTvShow(api_key, language);
        tvViewModel.getTvShow().observe(getActivity(), tvShows -> {
            List<TvShow> tvShowList = tvShows;
            tvShowArrayList.clear();
            tvShowArrayList.addAll(tvShowList);
            tvAdapter = new TvAdapter(getContext(), tvShowArrayList);
            recyclerViewTvShow.setAdapter(tvAdapter);
            recyclerViewTvShow.setHasFixedSize(true);
            tvAdapter.notifyDataSetChanged();
            tvAdapter.setOnItemClickCallback((this::detail));
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void detail(View view, TvShow tvShow) {
        TvFragmentDirections.ActionNavigationTvToNavigationDetail toNavigationDetail = TvFragmentDirections.actionNavigationTvToNavigationDetail();
        toNavigationDetail.setType("tv");
        toNavigationDetail.setDate(tvShow.getFirstAirDate());
        toNavigationDetail.setDescription(tvShow.getOverview());
        toNavigationDetail.setTitle(tvShow.getTitle());
        toNavigationDetail.setImage(tvShow.getPosterPath());
        Navigation.findNavController(view).navigate(toNavigationDetail);
    }
}
