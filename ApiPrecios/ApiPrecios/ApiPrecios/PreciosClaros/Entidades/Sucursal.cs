using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.PreciosClaros.Entidades
{
    public class Sucursal
    {
      public decimal distanciaNumero { get; set; }
      public string distanciaDescripcion { get; set; }
      public int banderaId { get; set; }
      public double lat { get; set; }
      public double lng { get; set; }
      public string sucursalNombre { get; set; }
      public string id { get; set; }
      public string sucursalTipo { get; set; }
      public string provincia { get; set; }
      public string direccion { get; set; }
      public string banderaDescripcion { get; set; }
      public string localidad { get; set; }
      public string comercioRazonSocial { get; set; }
      public int comercioId { get; set; }
      public int sucursalId { get; set; }
    }
}