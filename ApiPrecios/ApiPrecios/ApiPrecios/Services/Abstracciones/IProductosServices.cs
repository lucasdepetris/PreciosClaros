using ApiPrecios.PreciosClaros.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Services.Abstracciones
{
    public interface IProductosServices
    {
        ProductoModel ObtenerProductoPorCodigo(string codigo, double lat, double lng, int limite = 20);
        List<Producto> BuscarProductos(string buscar, double lat, double lng);
    }
}
