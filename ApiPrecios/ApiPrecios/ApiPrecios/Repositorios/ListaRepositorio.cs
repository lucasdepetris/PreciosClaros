using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Models.Entidades;

namespace ApiPrecios.Repositorios
{
    public class ListaRepositorio : IListasRepositorio
    {

        public Lista CrearLista(int idUsuario, string nombre, string descripcion)
        {
            DBPrecios db = new DBPrecios();
            var lista = new Lista();
            lista.fechaCreacion = DateTime.Now;
            lista.Nombre = nombre;
            lista.Descripcion = descripcion;
            db.Usuarios.Find(idUsuario).Listas.Add(lista);
            db.SaveChanges();
            return lista;

        }
        public Boolean AgregarUsuarioLista(string idGoogle, int idLista)
        {
            DBPrecios db = new DBPrecios();
            Usuario user = (from p in db.Usuarios where p.idGogle.Contains(idGoogle) select p).First();
            Lista lis = (from p in db.Listas where p.id.Equals(idLista) select p).First();
            lis.Usuarios.Add(user);
            user.Listas.Add(lis);
            db.SaveChanges();
            return true;
        }
        public bool AgregarProducto(int idLista, string idArticulo, int cantidad, int precioOptimo, string idComercio)
        {
            DBPrecios db = new DBPrecios();
            var lista = db.Listas.Where(l => l.id == idLista).FirstOrDefault();
            var lart = new ListaArticulo
            {
                idLista = idLista,
                idArticulo = idArticulo,
                Cantidad = cantidad,
                precioOptimo = precioOptimo,
                idComercio = idComercio
            };
            lista.ListaArticuloes.Add(lart);
            db.SaveChanges();
            return true;
            
        }

        public IEnumerable<Lista> ObtenerListas(int idUsuario)
        {
            DBPrecios db = new DBPrecios();
            return db.Listas.Where(l => l.Usuarios.Any(u => u.id == idUsuario));
        }
        public Lista ObtenerLista(int id)
        {
            DBPrecios db = new DBPrecios();
            return db.Listas.Where(l => l.id == id).FirstOrDefault();
        }
        public bool ModificarLista(int idLista,String nombre, String descripcion)
        {
            DBPrecios db = new DBPrecios();
            var lista = db.Listas.Where(l => l.id == idLista).FirstOrDefault();
            lista.Nombre = nombre;
            lista.Descripcion = descripcion;
            db.SaveChanges();
            return true;
        }
        public bool ModificarCantidadDeUnProducto(int idLista, String idArticulo, int cantidad)
        {
            DBPrecios db = new DBPrecios();
            var listaArticulo = db.ListaArticuloes.Where(l => l.idArticulo == idArticulo && l.idLista == idLista).First();
            listaArticulo.Cantidad = cantidad;
            db.SaveChanges();
            return true;
        }
        public bool EliminarLista(int idLista)
        {
            DBPrecios db = new DBPrecios();
            var lista = db.Listas.Where(l => l.id == idLista).First();
            db.Listas.Remove(lista);
            db.SaveChanges();
            return true;
        }
        public bool EliminarProducto(String idArticulo, int idLista)
        {
            DBPrecios db = new DBPrecios();
            var producto = db.ListaArticuloes.Where(l => l.idArticulo == idArticulo && l.idLista == idLista ).First();
            db.ListaArticuloes.Remove(producto);
            return true;
        }
    }
}