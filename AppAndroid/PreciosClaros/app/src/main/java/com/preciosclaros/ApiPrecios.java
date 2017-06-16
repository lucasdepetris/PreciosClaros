package com.preciosclaros;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lucas on 11/6/2017.
 */

public interface ApiPrecios {
    @Headers("Content-Type: application/json")
    @GET("Productos/ObtenerProductoPorId")
    Call<Response> getProducto(@Query ("codigo") String id ,@Query("lat") double latitud,@Query("lng") double longitud);
    @GET("items/{itemId}")
    Call<Article> getArticle(@Path("itemId") String id );
}
