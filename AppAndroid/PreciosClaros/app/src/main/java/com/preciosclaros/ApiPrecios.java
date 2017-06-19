package com.preciosclaros;

import com.preciosclaros.modelo.Lista;
import com.preciosclaros.modelo.Usuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lucas on 11/6/2017.
 */

public interface ApiPrecios {
    @Headers("Content-Type: application/json")
    @GET("Productos/ObtenerProductoPorId")
    Call<Response> getProducto(@Query ("codigo") String id ,@Query("lat") double latitud,@Query("lng") double longitud);
    @GET("Listas/ObtenerListas")
    Call<ArrayList<Lista>> getListas(@Query ("idUsuario") int id );
    @POST("Listas/CrearLista")
    Call<Lista> putLista(@Query ("idUsuario") int id, @Query("nombre") String nombre, @Query("descripcion") String descripcion);
    @POST("Usuarios/ObtenerUsuarioPorIdGoogle")
    Call<Usuario> getUsuario(@Query("idGoogle") String id);
    @POST("Usuarios/Login")
    Call<Usuario> loginUsuario(@Body Usuario user);
    @GET("items/{itemId}")
    Call<Article> getArticle(@Path("itemId") String id );
}
