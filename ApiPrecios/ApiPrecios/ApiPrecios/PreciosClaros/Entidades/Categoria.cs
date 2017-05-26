using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.PreciosClaros.Entidades
{
    public class Categoria
    {
        public int nivel { get; set; }
        public int productos { get; set; }
        public string id { get; set; }
        public string nombre { get; set; }
        public List<object> padres { get; set; }
        public bool? categoriaRequerida { get; set; }
    }
}