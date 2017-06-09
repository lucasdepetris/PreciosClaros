using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web.Http.Description;
using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System.Web.Mvc;
using ApiPrecios.Services.Abstracciones;

namespace ApiPrecios.Controllers
{
    public class UsuariosController : Controller
    {

        //Esto es un controller de ejemplo, la idea en si es que veas como usar un repositorio
        // Lo ideal, seria que no llame al repo de una el controller, si no que pase por un service, ej UsuariosService
        private readonly IUsuariosServices UsuariosServices;
        private readonly IUsuariosRepositorio usuariosRepo;
        public UsuariosController(IUsuariosServices service, IUsuariosRepositorio repositorio)
        {
            UsuariosServices = service;
            usuariosRepo = repositorio;
        }

        [HttpPost]
        public ContentResult ObtenerUsuarioPorIdGoogle(string idGoogle)
        {
            var user = UsuariosServices.ObtenerUsuarioPorIdGoogle(idGoogle);

            return Content(getResponse(user), "application/json");

        }
        [HttpPost]
        public Usuario CrearUsuarioSiNoExiste(Usuario user)
        {
           
           if(! usuariosRepo.ExisteUsuario(user.idGogle))
            {
               return  usuariosRepo.CrearUsuario(user);
            }
           return usuariosRepo.ObtenerUsuarioPorIdGoogle(user.idGogle);
        }
        private string getResponse(object result)
        {
            return Newtonsoft.Json.JsonConvert.SerializeObject(result);
        }

        //// GET: api/Usuarios
        //public IQueryable<Usuario> GetUsuarios()
        //{
        //    return db.Usuarios;
        //}

        //// GET: api/Usuarios/5
        //[ResponseType(typeof(Usuario))]
        //public IHttpActionResult GetUsuario(int id)
        //{
        //    Usuario usuario = db.Usuarios.Find(id);
        //    if (usuario == null)
        //    {
        //        return NotFound();
        //    }

        //    return Ok(usuario);
        //}

        //// PUT: api/Usuarios/5
        //[ResponseType(typeof(void))]
        //public IHttpActionResult PutUsuario(int id, Usuario usuario)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    if (id != usuario.id)
        //    {
        //        return BadRequest();
        //    }

        //    db.Entry(usuario).State = EntityState.Modified;

        //    try
        //    {
        //        db.SaveChanges();
        //    }
        //    catch (DbUpdateConcurrencyException)
        //    {
        //        if (!UsuarioExists(id))
        //        {
        //            return NotFound();
        //        }
        //        else
        //        {
        //            throw;
        //        }
        //    }

        //    return StatusCode(HttpStatusCode.NoContent);
        //}
        //[HttpPost]
        //public Usuario ObtenerUsuarios()
        //{
        //    return db.Usuarios.First();
        //}
        //// POST: api/Usuarios
        //[ResponseType(typeof(Usuario))]
        //public IHttpActionResult PostUsuario(Usuario usuario)
        //{
        //    if (!ModelState.IsValid)
        //    {
        //        return BadRequest(ModelState);
        //    }

        //    db.Usuarios.Add(usuario);
        //    db.SaveChanges();

        //    return CreatedAtRoute("DefaultApi", new { id = usuario.id }, usuario);
        //}

        //// DELETE: api/Usuarios/5
        //[ResponseType(typeof(Usuario))]
        //public IHttpActionResult DeleteUsuario(int id)
        //{
        //    Usuario usuario = db.Usuarios.Find(id);
        //    if (usuario == null)
        //    {
        //        return NotFound();
        //    }

        //    db.Usuarios.Remove(usuario);
        //    db.SaveChanges();

        //    return Ok(usuario);
        //}

        //protected override void Dispose(bool disposing)
        //{
        //    if (disposing)
        //    {
        //        db.Dispose();
        //    }
        //    base.Dispose(disposing);
        //}

        //private bool UsuarioExists(int id)
        //{
        //    return db.Usuarios.Count(e => e.id == id) > 0;
        //}
    }
}