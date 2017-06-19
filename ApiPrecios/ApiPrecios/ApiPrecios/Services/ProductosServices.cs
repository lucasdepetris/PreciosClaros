using ApiPrecios.Models.Entidades;
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
        private readonly IComerciosRepositorio repositorioComercio;
        public ProductosServices(IPreciosClarosApi precios, IProductosRepositorio productos, IComerciosRepositorio comercio)
        {
            preciosClaros = precios;
            repositorioProductos = productos;
            repositorioComercio = comercio;
        }
        public ProductoModel ObtenerProductoPorCodigo(string codigo, double lat, double lng, int limite = 5)
        {
            var producto = preciosClaros.ObtenerProductosModelPorId(codigo, lat, lng, limite);
            if(producto.producto.nombre == null )
            {
                return producto;
            }

            validarProductoExistente(producto.producto);
            if(producto.sucursales.Any())
            {

                validarComerciosExistentes(producto.sucursales);

                producto.sucursales = producto.sucursales.Where(s => s.preciosProducto.precioLista.Trim() != "" && s.distanciaNumero < 6).ToList();
                producto.mejorPrecio = producto.sucursales
                                                .Select(p => decimal.Parse(p.preciosProducto.precioLista))
                                                .Min()
                                                .ToString();
            }

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
        private void validarProductoExistente(Producto producto)
        {
            if(!repositorioProductos.ExisteProducto(producto.id))
            {
                var articulo = new Articulo
                {
                    id = producto.id,
                    marca = producto.marca,
                    nombre = producto.nombre,
                    presentacion = producto.presentacion
                };
                repositorioProductos.AgregarProducto(articulo);
            }
        }
        private void validarComerciosExistentes(List<Sucursal> comercios)
        {
         foreach(var c in comercios)
            {
                if (!repositorioComercio.ExisteComercio(c.comercioId+ "-" + c.banderaId + "-"+c.id))
                {
                    var comercio = new Comercio
                    {
                       id = c.comercioId + "-" + c.banderaId + "-" + c.id,
                       comercio_id = c.comercioId,
                       bandera_descripcion = c.banderaDescripcion,
                       bandera_id = c.banderaId,
                       sucursal_nombre = c.sucursalNombre,
                       sucursal_tipo = c.sucursalTipo,
                       direccion = c.direccion,
                       comercio_razon_social = c.comercioRazonSocial,
                       provincia = c.provincia,
                       localidad = c.localidad,
                       lat = c.lat.ToString(),
                       lng = c.lng.ToString()                 
                    };
                    repositorioComercio.AgregarComercio(comercio);
                }
            }

        }

    }
}