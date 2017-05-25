using ApiPrecios.PreciosClaros;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ApiPrecios.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";
            var pre = new PreciosClarosAPIProd();
            var cadena = pre.ObtenerSucursalesPorZona(-34.666227,-58.589724,10);
            return View("Index", cadena);
        }
    }
}
