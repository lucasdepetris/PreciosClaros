using ApiPrecios.PreciosClaros;
using ApiPrecios.PreciosClaros.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Services.Abstracciones;
using System.Collections.Generic;
using System.Linq;

namespace ApiPrecios.Services
{

    public class ProductosServices : IProductosServices
    {
        private readonly IPreciosClarosApi preciosClaros;
        private readonly IProductosRepositorio repositorioProductos;
        public ProductosServices(IPreciosClarosApi precios, IProductosRepositorio productos)
        {
            preciosClaros = precios;
            repositorioProductos = productos;
        }
        public ProductoModel ObtenerProductoPorCodigo(string codigo, double lat, double lng, int limite = 5)
        {
            var producto = preciosClaros.ObtenerProductosModelPorId(codigo, lat, lng, limite);
            //hola aca voy a validar si existe ya en la bdd
            if(producto.producto.nombre == null )
            {
                return producto;
            }
            producto.sucursales = producto.sucursales.Where(s => s.preciosProducto.precioLista.Trim() != "" && s.distanciaNumero < 6).ToList();
            producto.mejorPrecio = producto.sucursales
                                            .Select(p => decimal.Parse(p.preciosProducto.precioLista))
                                            .Min()
                                            .ToString();
            return producto;
        }
        public List<Producto> BuscarProductos(string buscar, double lat, double lng)
        {
            var productosLocales = repositorioProductos.buscarProductos(buscar);
            if(productosLocales.Any())
            {
                return productosLocales.Select(p => new Producto
                {
                    id = p.id,
                    marca = p.marca,
                    nombre = p.nombre,
                    presentacion = p.presentacion
                }).ToList();
            }
            return preciosClaros.ObtenerProductosPorNombreyZona(buscar, lat, lng, 20); 

        }

    }
}