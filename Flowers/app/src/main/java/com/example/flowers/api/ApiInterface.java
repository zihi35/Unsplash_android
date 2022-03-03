package com.example.flowers.api;

import static com.example.flowers.api.ApiUtilities.API_KEY;

import com.example.flowers.model.SearchModel;
import com.example.flowers.model.imageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("Authorization: Client-ID "+ API_KEY)
    @GET("/photos")
    Call<List<imageModel>> getImages(
            @Query("page") int page,
            @Query("per_page") int perPage
    );

    @Headers("Authorization: Client-ID "+ API_KEY)
    @GET("/search/photos")
    Call<List<SearchModel>> seachImages(
            @Query("query") String query
    );
}
