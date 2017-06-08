using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using ApiPrecios.Models.Entidades;

namespace ApiPrecios.Controllers
{
    public class ListasController : ApiController
    {
        private DBPrecios db = new DBPrecios();

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
        }
    }
}