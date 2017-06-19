using ApiPrecios.Mappers.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ApiPrecios.Models.Entidades;
using ApiPrecios.Models.Entidades.Lites;
using ApiPrecios.PreciosClaros.Entidades;

namespace ApiPrecios.Mappers
{
    public class MapperListaLite : IMapperListaLite
    {
        public ListaLite MapearListaEntityAListLite(Lista lista)
        {
            return new ListaLite
            {
                id = lista.id,
                Nombre = lista.Nombre,
                Descripcion = lista.Descripcion,
                Items = obtenerItems(lista)
            };
        }
        private List<ItemLista> obtenerItemsLista (List<ListaArticulo> lista)
        {

            var listas = new List<ItemLista>();
            foreach(var a in lista)
            {
                var item = new ItemLista();
                item.Cantidad = a.Cantidad;
                item.precioReal = a.precioReal;
                item.precioOptimo = a.precioOptimo;
                item.producto = new Producto
                                { id = a.Articulo.id,
                                  nombre = a.Articulo.nombre,
                                  marca = a.Articulo.marca,
                                  presentacion = a.Articulo.presentacion                    
                                };
                listas.Add(item);
            }
            
            return listas;

        }
        private List<ItemSucursales> obtenerItems(Lista lista)
        {
            var sucursales = new List<ItemSucursales>();
            var comercios = lista.ListaArticuloes.Select(p => p.Comercio).Distinct();
            foreach(var c in comercios)
            {
                var productos = lista.ListaArticuloes.Where(s => s.Comercio.id == c.id).ToList();
                var sucursal = new ItemSucursales
                {
                    Comercio = mapearComercioASucursalLite(c),
                    Productos = obtenerItemsLista(productos)
                };
                sucursales.Add(sucursal);
            }
            return sucursales;
        }
        private SucursalLite mapearComercioASucursalLite(Comercio comercio)
        {
            return new SucursalLite
            {
                sucursalNombre = comercio.sucursal_nombre,
                comercioId = comercio.comercio_id,
                banderaDescripcion = comercio.bandera_descripcion,
                banderaId = comercio.bandera_id,
                direccion = comercio.direccion,
                localidad = comercio.localidad,
                provincia = comercio.provincia
            };
        }
        
    }
}