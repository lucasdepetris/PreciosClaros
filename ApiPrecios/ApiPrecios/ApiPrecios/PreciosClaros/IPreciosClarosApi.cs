using ApiPrecios.PreciosClaros.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.PreciosClaros
{
    public interface IPreciosClarosApi
    {
        List<Sucursal> ObtenerSucursalesPorZona(double lat, double lng, int limite);
        List<Sucursal> ObtenerSucursales(int limite);
        List<Producto> ObtenerProductosPorNombreyZona(String nombre, double lat, double lng, int limite);
        Producto ObtenerProductosPorId(String id, double lat, double lng, int limite);
        List<Producto> ObtenerProductosPorCategoria(String id, double lat, double lng, int limite);
        List<Producto> ProductosPorZona(double lat, double lng, int limite);
        List<Categoria> ObtenerCategorias();
    }
}
