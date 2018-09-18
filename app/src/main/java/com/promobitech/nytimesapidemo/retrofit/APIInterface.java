package com.promobitech.nytimesapidemo.retrofit;

import com.promobitech.nytimesapidemo.server.ResponseResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by khurram .
 */
public interface APIInterface {
    /**
     * This method is used to validate Mobile App login credentials
     */
    @GET("movies/v2/reviews/search.json")
    Observable<ResponseResult> getMovieReviews(@Query("api_key") String api_key,@Query("publication-date") String pDate, @Query("opening-date")String oDate, @Query("reviewer")String reviewer);
}
