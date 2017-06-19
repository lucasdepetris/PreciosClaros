using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Models.Entidades.Lites
{
    public class SucursalLite
    {
       public string sucursalNombre { get; set; }
       public int? comercioId { get; set; }
       public string banderaDescripcion { get; set; }
       public int? banderaId { get; set; }
       public string direccion { get; set; }
       public string localidad { get; set; }
       public string provincia { get; set; }
    }
}