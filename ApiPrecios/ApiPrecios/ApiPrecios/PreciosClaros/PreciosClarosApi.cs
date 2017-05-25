using ApiPrecios.PreciosClaros.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.PreciosClaros
{
    interface PreciosClarosApi
    {
        List<Sucursal> ObtenerSucursalesPorZona(double lat, double lng, int limite);
    }
}
