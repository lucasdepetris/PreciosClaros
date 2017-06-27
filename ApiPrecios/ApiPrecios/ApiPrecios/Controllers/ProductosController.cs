using ApiPrecios.Services.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ApiPrecios.Controllers
{
    public class ProductosController : Controller
    {
        private readonly IProductosServices productosServices;
        public ProductosController(IProductosServices productos)
        {
            productosServices = productos;
        }

        [HttpGet]
        public ContentResult ObtenerProductoPorId(string codigo, double lat, double lng)
        {
            var prod = productosServices.ObtenerProductoPorCodigo(codigo, lat, lng);

            return Content(getResponse(prod), "application/json");

        }
        [HttpGet]
        public ContentResult BuscarProductos(string buscar, double lat, double lng)
        {
            try
            {
                var prod = productosServices.BuscarProductos(buscar, lat, lng);

                return Content(getResponse(prod), "application/json");
            }
            catch (Exception ex)
            {
                //La ide aca seria manejar algun mensaje de error si algo fallo,
                //Tecnicamente esta mal poner el try Catch en el controller porque es como muy arriba
                //Pero bueno, no tenemos tiempo para hacerlo de otra forma, la idea es que si falla algo
                //Lo detecte y se logee en la base de datos, en una tabla excepciones
                //Entonces podemos ver que errores suceden y que no reviente todo por los aires
                return Content("{error: " + ex.Message + "}", "application/json");
            }

        }

        private string getResponse(object result)
        {
            return Newtonsoft.Json.JsonConvert.SerializeObject(result);
        }

    }
}