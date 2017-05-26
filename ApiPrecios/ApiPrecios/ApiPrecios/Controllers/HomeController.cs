using ApiPrecios.PreciosClaros;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Ninject;
using System.Web.Mvc;

namespace ApiPrecios.Controllers
{
    public class HomeController : Controller
    {
        private readonly IPreciosClarosApi preciosClaros;
        public HomeController(IPreciosClarosApi precios)
        {
            preciosClaros = precios;
        }
        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";
            //var cadena = preciosClaros.ObtenerSucursalesPorZona(-34.666227,-58.589724,10);
            //var cadena = preciosClaros.ObtenerSucursales(20);
            //var cadena = preciosClaros.ObtenerProductosPorNombreyZona("coca", -34.666227, -58.589724, 10);
            //var cadena = preciosClaros.ObtenerProductosPorId(7790040100336, -34.666227, -58.589724, 10);
            //var cadena = preciosClaros.ProductosPorZona(-34.666227, -58.589724, 10);
            var cadena = preciosClaros.ObtenerCategorias();
            return View("Index", cadena);
        }
    }
}
