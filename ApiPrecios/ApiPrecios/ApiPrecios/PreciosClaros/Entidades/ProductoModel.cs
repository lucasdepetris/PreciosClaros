using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.PreciosClaros.Entidades
{
    public class ProductoModel
    {
        public Producto producto { get; set; }
        public string mejorPrecio { get; set; }
        public List<Sucursal> sucursales { get; set; }
    }
}