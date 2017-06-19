using ApiPrecios.PreciosClaros.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Models.Entidades.Lites
{
    public class ItemLista
    {
        public Producto producto { get; set; }
        public int? Cantidad { get; set; }
        public decimal? precioOptimo { get; set; }
        public decimal? precioReal { get; set; }
    }
}