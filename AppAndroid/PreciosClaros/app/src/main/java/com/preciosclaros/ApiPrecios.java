package com.preciosclaros;

import com.preciosclaros.modelo.Lista;
import com.preciosclaros.modelo.Producto;
import com.preciosclaros.modelo.Response;
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
    Call<Response> getProducto(@Query ("codigo") String id , @Query("lat") double latitud, @Query("lng") double longitud);
    @GET("Productos/BuscarProductos")
    Call<ArrayList<Producto>> BuscarProductos(@Query ("buscar") String buscar , @Query("lat") double latitud, @Query("lng") double longitud);
    @GET("Listas/ObtenerListas")
    Call<ArrayList<Lista>> getListas(@Query ("idUsuario") int id );
    @GET("Listas/ObtenerLista")
    Call<Lista> getLista(@Query ("idLista") int id );
    @POST("Listas/CrearLista")
    Call<Lista> putLista(@Query ("idUsuario") int id, @Query("nombre") String nombre, @Query("descripcion") String descripcion);
    @POST("Usuarios/ObtenerUsuarioPorIdGoogle")
    Call<Usuario> getUsuario(@Query("idGoogle") String id);
    @POST("Usuarios/Login")
    Call<Usuario> loginUsuario(@Body Usuario user);
    @POST("Listas/AgregarProducto")
    Call<Lista> AgregarProducto(@Query ("idLista") int id, @Query("idArticulo") String idArticulo, @Query("cantidad") int cantidad,
                                @Query("precioOptimo") int preciOptimo, @Query("idComercio") String idComercio);
    @GET("items/{itemId}")
    Call<Article> getArticle(@Path("itemId") String id );
}
