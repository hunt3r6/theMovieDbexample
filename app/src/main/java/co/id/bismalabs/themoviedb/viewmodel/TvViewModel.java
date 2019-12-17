package co.id.bismalabs.themoviedb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.id.bismalabs.themoviedb.api.ApiClient;
import co.id.bismalabs.themoviedb.api.ApiInterface;
import co.id.bismalabs.themoviedb.model.TvShow;
import co.id.bismalabs.themoviedb.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvViewModel extends ViewModel {
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    private MutableLiveData<ArrayList<TvShow>> mutableLiveData = new MutableLiveData<>();

    public void setTvShow(String apiKey, String language) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TvShowResponse> tvShowResponseCall = apiInterface.getTv(apiKey, language);
        tvShowResponseCall.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShowArrayList = (ArrayList<TvShow>) response.body().getTvShows();
                    mutableLiveData.setValue(tvShowArrayList);
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShow() {
        return mutableLiveData;
    }
}
