﻿using ApiPrecios.PreciosClaros.Entidades;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using Newtonsoft.Json;

namespace ApiPrecios.PreciosClaros
{
    public class PreciosClarosAPIProd : IPreciosClarosApi
    {
        internal string APIURL = "https://d735s5r2zljbo.cloudfront.net/prod/";
        //OBTENER SUCURSALES POR ZONA
        public List<Sucursal> ObtenerSucursalesPorZona(double lat, double lng, int limite)
        {
          var json = getHttpRequest("sucursales?lat=" + lat + "&lng=" + lng + "&limit=" + limite);         
          return JsonConvert.DeserializeObject<List<Sucursal>>(json.SelectToken("sucursales").ToString());
        }
        //OBTENER TODAS LAS SUCURSALES
        public List<Sucursal> ObtenerSucursales(int limite)
        {
            var json = getHttpRequest("sucursales?limit="+limite);
            return JsonConvert.DeserializeObject<List<Sucursal>>(json.SelectToken("sucursales").ToString());
        }
        //OBTENER PRODUCTOS POR NOMBRE Y ZONA
        public List<Producto> ObtenerProductosPorNombreyZona(String nombre ,double lat, double lng, int limite)
        {
            var json = getHttpRequest("productos?string="+nombre+"&lat=" + lat + "&lng=" + lng + "&limit=" + limite);
            return JsonConvert.DeserializeObject<List<Producto>>(json.SelectToken("productos").ToString());
        }
        //OBTENER PRODUCTOS POR ID CONSULTA SI DEVUELVE UN OBJETO PRODUCTO POR QUE TE DEVUELVE EL OBJETO SUCURSALES TAMBIEN ?
        public Producto ObtenerProductosPorId(String id, double lat, double lng, int limite)
        {
            var json = getHttpRequest("producto?id_producto=" + id + "&lat=" + lat + "&lng=" + lng + "&limit=" + limite);
            return JsonConvert.DeserializeObject<Producto>(json.SelectToken("producto").ToString());
        }
        public ProductoModel ObtenerProductosModelPorId(String id, double lat, double lng, int limite)
        {

            var json = getHttpRequest("producto?id_producto=" + id + "&lat=" + lat + "&lng=" + lng + "&limit=" + limite);
            return JsonConvert.DeserializeObject<ProductoModel>(json.ToString());
        }
        //OBTENER PRODUCTOS POR ZONA
        public List<Producto> ProductosPorZona(double lat, double lng, int limite)
        {
            var json = getHttpRequest("productos?lat="+lat+"&lng=" +lng+ "&limit=" + limite);
            return JsonConvert.DeserializeObject<List<Producto>>(json.SelectToken("productos").ToString());
        }
        //OBTENER PRODUCTOS POR CATEGORIA
        public List<Producto> ObtenerProductosPorCategoria(String id,double lat, double lng, int limite)
        {
            var json = getHttpRequest("productos?id_categoria="+id+"&lat=" + lat + "&lng=" + lng + "&limit=" + limite);
            try
            {
                return JsonConvert.DeserializeObject<List<Producto>>(json.SelectToken("productos").ToString());
            } catch(Exception ex)
            {
                return new List<Producto>();
            }

        }
        //OBTENER TODAS LAS CATEGORIAS
        public List<Categoria> ObtenerCategorias()
        {
            var json = getHttpRequest("categorias");
            return JsonConvert.DeserializeObject<List<Categoria>>(json.SelectToken("categorias").ToString());
        }
        private JToken getHttpRequest(string url)
        {
            var json = new WebClient().DownloadString(APIURL + url);
            return JObject.Parse(json);
        }
    }
}