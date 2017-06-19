using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Services.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ApiPrecios.Controllers
{
    public class ListasController : Controller
    {
        private readonly IListasServices listaServices;
        private readonly IListasRepositorio ListaRepo;
        public ListasController(IListasServices service, IListasRepositorio repositorio)
        {
            listaServices = service;
            ListaRepo = repositorio;
        }
        [HttpPost]
        public ContentResult CrearLista(string idGoogle)
        {
            var lista = listaServices.CrearLista(idGoogle);
            return Content(getResponse(lista), "application/json");
        }

        [HttpGet]
        public ContentResult ObtenerListas(int idUsuario)
        {
            var listas = listaServices.ObtenerListas(idUsuario).ToList();
            return Content(getResponse(listas), "application/json");
        }

        [HttpGet]
        public ContentResult ObtenerLista(int idLista)
        {
            var lista = listaServices.ObtenerLista(idLista);
            return Content(getResponse(lista), "application/json");
        }
        [HttpPost]
        public ContentResult AgregarUsuarioLista(string idGoogle, int idLista)
        {
           var lista = listaServices.AgregarUsuarioLista(idGoogle, idLista);
           return Content(getResponse(lista), "application/json");
        }
        [HttpPost]
        public ContentResult AgregarProducto(int idLista, string idArticulo, int cantidad, int precioOptimo, string idComercio)
        {
            var lista = listaServices.AgregarProducto(idLista,idArticulo,cantidad,precioOptimo,idComercio);
            return Content(getResponse(lista), "application/json");
        }

        private string getResponse(object result)
        {
            return Newtonsoft.Json.JsonConvert.SerializeObject(result);
        }
        /*
        // GET: api/Listas
        public IQueryable<Lista> GetListas()
        {
            return db.Listas;
        }

        // GET: api/Listas/5
        [ResponseType(typeof(Lista))]
        public IHttpActionResult GetLista(int id)
        {
            Lista lista = db.Listas.Find(id);
            if (lista == null)
            {
                return NotFound();
            }

            return Ok(lista);
        }

        // PUT: api/Listas/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutLista(int id, Lista lista)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != lista.id)
            {
                return BadRequest();
            }

            db.Entry(lista).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ListaExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Listas
        [ResponseType(typeof(Lista))]
        public IHttpActionResult PostLista(Lista lista)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Listas.Add(lista);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = lista.id }, lista);
        }

        // DELETE: api/Listas/5
        [ResponseType(typeof(Lista))]
        public IHttpActionResult DeleteLista(int id)
        {
            Lista lista = db.Listas.Find(id);
            if (lista == null)
            {
                return NotFound();
            }

            db.Listas.Remove(lista);
            db.SaveChanges();

            return Ok(lista);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ListaExists(int id)
        {
            return db.Listas.Count(e => e.id == id) > 0;
        }*/
    }
}