using ApiPrecios.PreciosClaros.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Models.Entidades.Lites
{
    public class ItemSucursales
    {
        public SucursalLite Comercio { get; set; }
        public List<ItemLista> Productos { get ; set; }
    }
}