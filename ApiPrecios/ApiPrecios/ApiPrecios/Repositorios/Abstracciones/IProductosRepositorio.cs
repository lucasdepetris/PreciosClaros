using ApiPrecios.Models.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Repositorios.Abstracciones
{
    public interface IProductosRepositorio
    {
        IList<Articulo> buscarProductos(string buscar);
    }
}
