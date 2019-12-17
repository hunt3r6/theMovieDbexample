package co.id.bismalabs.themoviedb.api;

import co.id.bismalabs.themoviedb.model.MoviesResponse;
import co.id.bismalabs.themoviedb.model.TvShowResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie")
    Call<MoviesResponse> getMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv")
    Call<TvShowResponse> getTv(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}
