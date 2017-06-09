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
            var prod = productosServices.BuscarProductos(buscar, lat, lng);

            return Content(getResponse(prod), "application/json");
        }

        private string getResponse(object result)
        {
            return Newtonsoft.Json.JsonConvert.SerializeObject(result);
        }

    }
}