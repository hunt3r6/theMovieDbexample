package co.id.bismalabs.themoviedb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.id.bismalabs.themoviedb.api.ApiClient;
import co.id.bismalabs.themoviedb.api.ApiInterface;
import co.id.bismalabs.themoviedb.model.Movie;
import co.id.bismalabs.themoviedb.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MutableLiveData<ArrayList<Movie>> mutableLiveData = new MutableLiveData<>();

    public void setMoview(String apiKey, String language) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MoviesResponse> moviesResponseCall = apiInterface.getMovie(apiKey, language);
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieArrayList = (ArrayList<Movie>) response.body().getMovies();
                    mutableLiveData.setValue(movieArrayList);

                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovie() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }
}
