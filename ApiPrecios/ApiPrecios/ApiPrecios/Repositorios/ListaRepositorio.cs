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
        private DBPrecios db = new DBPrecios();
        public Lista CrearLista(String idGoogle)
        {
            var lista = new Lista();
            Usuario user = new Usuario();
            lista.fechaCreacion = DateTime.Now;
            user.idGogle = idGoogle;
            user.Listas.Add(lista);
            db.Listas.Add(lista);
            db.Usuarios.Add(user);
            db.SaveChanges();
            return lista;

        }
        public Boolean AgregarUsuarioLista(String idGoogle, int idLista)
        {
            Usuario user = (from p in db.Usuarios where p.idGogle.Contains(idGoogle) select p).First();
            Lista lis = (from p in db.Listas where p.id.Equals(idLista) select p).First();
            lis.Usuarios.Add(user);
            user.Listas.Add(lis);
            db.SaveChanges();
            return true;
        }
    }
}