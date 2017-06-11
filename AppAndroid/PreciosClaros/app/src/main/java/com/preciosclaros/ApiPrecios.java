package com.preciosclaros;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lucas on 11/6/2017.
 */

public interface ApiPrecios {
    @GET("Productos/ObtenerProductoPorId/{codigo}{lat}{lng}")
    Call<Producto> getProducto(@Path("codigo") String codigo ,@Path("lat") double lat, @Path("lng") double lng);
}
